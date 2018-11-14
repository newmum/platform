package net.evecom.core.db.database.query;

import net.evecom.tools.constant.consts.SqlConst;
import net.evecom.utils.verify.CheckUtil;
import org.beetl.sql.core.query.OrderBy;

/**
 * @ClassName: QueryCondition
 * @Description: 条件对象
 * @author： zhengc
 * @date： 2018年5月30日
 */
public class QueryCondition {

	/**
	 * 属性名
	 */
	private String attrName;

    /**
     * 属性类型
     */
	private String attrType;

    /**
     * 条件表达式
     */
	private String condition;

    /**
     * 值
     */
	private Object value;

    /**
     * 并列关系
     */
	private String relation;

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrType() {
		return attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}

	public String getCondition() {
        if(CheckUtil.isNull(condition)){
            condition = SqlConst.EQ;
        }
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Object getValue() {
		if(CheckUtil.isNull(value)){
            value = "";
        }
	    return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getRelation() {
        if(CheckUtil.isNull(relation)){
            if(this.condition!=null
                    && this.condition.equals(SqlConst.ORDERBY)){
                relation = SqlConst.ASC;
            }else{
                relation = SqlConst.AND;
            }
        }
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

    @Override
    public String toString() {
        return super.toString();
    }
}
