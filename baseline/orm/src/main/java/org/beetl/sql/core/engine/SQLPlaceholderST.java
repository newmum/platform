package org.beetl.sql.core.engine;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.beetl.core.Context;
import org.beetl.core.InferContext;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.statement.Expression;
import org.beetl.core.statement.FormatExpression;
import org.beetl.core.statement.FunctionExpression;
import org.beetl.core.statement.PlaceholderST;
import org.beetl.core.statement.Statement;
import org.beetl.core.statement.Type;
import org.beetl.sql.core.JavaType;
import org.beetl.sql.core.kit.StringKit;

public class SQLPlaceholderST extends Statement
{
	public Expression expression;
	public Type type = null;
	FormatExpression format;
	/**
	 *  这些函数调用总是返回函数结果而不是一个sql占位符号“?”
	 */
	public static final Set<String> textFunList = new HashSet<String>();
	static{
		textFunList.add("text");
		textFunList.add("use");
		textFunList.add("globalUse");
		textFunList.add("join");
		textFunList.add("page");
		textFunList.add("orm.single");
		textFunList.add("orm.many");
		
		
	}

	public SQLPlaceholderST(PlaceholderST st)
	{
		super(st.token);
		this.type = st.type;
		this.expression = st.expression;
		this.format = st.getFormat();

	}

	@Override
	public final void execute(Context ctx)
	{
		try{
			Object value = expression.evaluate(ctx);
			int jdbcType = 0;
			if (format != null)
			{
				String formatName =  format.token.text;
				if(formatName.startsWith("typeof")){
					//特殊的format，告诉此对象应该作为jdbc类型
					String str = formatName.substring(6).toLowerCase();
					str = StringKit.toLowerCaseFirstOne(str);
					Integer expectJdbcType = JavaType.jdbcTypeNames.get(str);
					if(expectJdbcType==null){
						BeetlException be = new BeetlException(BeetlException.FORMAT_NOT_FOUND,formatName+"是用来指示jdbc类型，并不存在，请检查java.sql.Type");
						be.pushToken(format.token);
						throw be;
					}
					jdbcType = expectJdbcType;
				}else if(formatName.equals("jdbc")) {
					Integer expectJdbcType = (Integer)format.evaluateValue(value, ctx);
					if(expectJdbcType==null){
						BeetlException be = new BeetlException(BeetlException.ERROR,formatName+"是用来指示jdbc类型，并不存在，请检查java.sql.Type");
						be.pushToken( format.token);
						throw be;
					}
					jdbcType = expectJdbcType;
					
				}
				else{
					value = format.evaluateValue(value, ctx);
				}
				
				
			}
			
			if(expression instanceof FunctionExpression){
				//db 开头或者内置的方法直接输出
				FunctionExpression fun = (FunctionExpression)expression;
				String funName = fun.token.text;
				if(funName.startsWith("db")){
					ctx.byteWriter.writeString(value!=null?value.toString():"");
					return ;
				}else if(textFunList.contains(funName)){
					ctx.byteWriter.writeString(value!=null?value.toString():"");
					return ;
				}
			}
			int type  = SQLParameter.NAME_EXPRESSION;
			if(expression instanceof SQLVarRef){
				type = SQLParameter.NAME_GENEARL;
			}
			ctx.byteWriter.writeString("?");
			List list = (List)ctx.getGlobal("_paras");
			SQLParameter sqlPara  = new SQLParameter(expression.token.text,value,type);
			sqlPara.setJdbcType(jdbcType);
			list.add(sqlPara);
			
			
		}
		catch (IOException e)
		{
			BeetlException be = new BeetlException(BeetlException.CLIENT_IO_ERROR_ERROR, e.getMessage(), e);
			be.pushToken(this.token);
			throw be;
		}

	}

	@Override
	public void infer(InferContext inferCtx)
	{
		expression.infer(inferCtx);
		this.type = expression.type;
	}

}