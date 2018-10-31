package net.evecom.core.db.database.query;

import lombok.ToString;
import net.evecom.tools.constant.consts.SqlConst;
import net.evecom.utils.object.ClassUtil;
import net.evecom.utils.verify.CheckUtil;
import org.beetl.sql.core.query.Query;

import javax.persistence.Column;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @ClassName: QueryParam
 * @Description: 数据层入参对象
 * @author： zhengc
 * @date： 2018年5月30日
 * @param <T>
 */
@ToString
public class QueryParam<T> {
    /**
     * 条件集合
     */
    private List<QueryCondition> list;

    /**
     * 当前页数
     */
    private int page;

    /**
     * 每页显示记录数
     */
    private int pageSize;

    /**
     * 是否分页
     */
    private boolean needPage;

    /**
     * 是否有总记录数
     */
    private boolean needTotal;

    public QueryParam() {
        getList();
    }

    public List<QueryCondition> getList() {
        if (list == null) {
            list = new ArrayList<QueryCondition>();
        }
        return list;
    }

    public void setList(List<QueryCondition> list) {
        this.list = list;
    }

    public int getPage() {
        if (page <= 0) {
            return 1;
        }
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        if (pageSize <= 0) {
            pageSize = 20;
        }
        return pageSize;
    }

    public int getStartSize() {
        return (getPage() - 1) * pageSize + 1;
    }

    public int getEndSize() {
        return getStartSize() + pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isNeedPage() {
        return needPage;
    }

    public void setNeedPage(boolean needPage) {
        this.needPage = needPage;
    }

    public boolean isNeedTotal() {
        return needTotal;
    }

    public void setNeedTotal(boolean needTotal) {
        this.needTotal = needTotal;
    }

    public QueryParam<T> append(String column, Object value, String condition, String relation) {
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setAttrName(column);
        queryCondition.setValue(value);
        queryCondition.setCondition(condition);
        queryCondition.setRelation(relation);
        getList().add(queryCondition);
        return this;
    }

    public QueryParam<T> append(QueryProperty<T, ?> upperfier, Object value, String condition, String relation) {
        return append(getFunctionName(upperfier), value, condition, relation);
    }

    private QueryParam<T> append(String column, Object value, String condition) {
        return append(column, value, condition, SqlConst.AND);
    }

    public QueryParam<T> append(QueryProperty<T, ?> upperfier, Object value, String condition) {
        return append(getFunctionName(upperfier), value, condition, SqlConst.AND);
    }

    public QueryParam<T> append(String column, Object value) {
        return append(column, value, SqlConst.EQ);
    }

    public QueryParam<T> append(QueryProperty<T, ?> upperfier, Object value) {
        return append(getFunctionName(upperfier), value, SqlConst.EQ);
    }

    public void clear() {
        list.clear();
        needPage = false;
        needTotal = false;
        page = 1;
        pageSize = 20;
    }

    public void clearCondition() {
        list.clear();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getStr() {
        return "str";
    }

    private String getWhereSql(String sql, List<Object> params) {
        StringBuffer sb = new StringBuffer();
        String[] array = sql.split("\\?");
        for (int i = 0; i < params.size(); i++) {
            sb.append(array[i]);
            sb.append("\"" + params.get(i).toString() + "\"");
        }
        if (CheckUtil.isNotNull(array[array.length - 1])) {
            sb.append(array[array.length - 1]);
        }
        sql = sb.toString();
        return sql;
    }

    public String getGroupKey(Query<?> query) {
        query = QueryBuilder.getQuery(list, query);
        String sql = query.getSql().toString();
        if (CheckUtil.isNull(sql)) {
            sql = "all";
        } else {
            sql = getWhereSql(sql, query.getParams());
        }
        sql = sql + ",page:" + page + ",pageSize:" + pageSize + ",needPage:" + needPage + ",needTotal:" + needTotal;
        return sql;
    }

    private String getFunctionName(QueryProperty<T, ?> property) {
        try {
            Method declaredMethod = property.getClass().getDeclaredMethod("writeReplace");
            declaredMethod.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) declaredMethod.invoke(property);
            String method = serializedLambda.getImplMethodName();
            String attr = null;
            if (method.startsWith("get")) {
                attr = method.substring(3);
            } else {
                attr = method.substring(2);
            }
//            attr = attr.substring(0, 1).toLowerCase() + attr.substring(1);
//			 获取类对应的列 暂不使用
            String columnName = "";
            String implClass = serializedLambda.getImplClass();
            implClass = implClass.replaceAll("/", ".");
            Class<?> obj = Class.forName(implClass);
            List<Field> list = new ArrayList<Field>();
            ClassUtil.getAllfield(list, obj);
            for (Field field : list) {
                if (field.getName().equals(attr)) {
                    if (field.isAnnotationPresent(Column.class)) {
                        Column column = (Column) field.getAnnotation(Column.class);
                        columnName = column.name();
                        break;
                    }
                }
            }
            return columnName;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public interface QueryProperty<T, R> extends Function<T, R>, Serializable {
    }
}
