package net.evecom.rd.ie.baseline.core.db.database.query;

import lombok.ToString;
import net.evecom.rd.ie.baseline.tools.constant.consts.SqlConst;
import net.evecom.rd.ie.baseline.utils.string.StringUtil;
import net.evecom.rd.ie.baseline.utils.verify.CheckUtil;
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

    private Class<T> clazz;

    public QueryParam() {
        getList();
    }

    public QueryParam(Class<T> clazz) {
        this.clazz = clazz;
        getList();
    }

    public List<QueryCondition> getList() {
        if (list == null) {
            list = new ArrayList();
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

    private QueryParam<T> append(String column, Object value, String condition) {
        return append(column, value, condition, SqlConst.AND);
    }

    public QueryParam<T> append(String column, Object value) {
        return append(column, value, SqlConst.EQ);
    }

    public QueryParam<T> append(Property<T, ?> property, Object value, String condition, String relation) {
        return append(getFunctionName(property, clazz), value, condition, relation);
    }

    public QueryParam<T> append(Property<T, ?> property, Object value, String condition) {
        return append(getFunctionName(property, clazz), value, condition, SqlConst.AND);
    }

    public QueryParam<T> append(Property<T, ?> property, Object value) {
        return append(getFunctionName(property, clazz), value, SqlConst.EQ);
    }

    public void clear() {
        list.clear();
        needPage = false;
        needTotal = false;
        page = 1;
        pageSize = 20;
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

    public String getFunctionName(Property<T, ?> property, Class clazz) {
        try {
            Method declaredMethod = property.getClass().getDeclaredMethod("writeReplace");
            declaredMethod.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) declaredMethod.invoke(property);
            String methodName = serializedLambda.getImplMethodName();
            if (methodName.startsWith("get")) {
                methodName = methodName.substring(3);
            } else {
                methodName = methodName.substring(2);
            }
            methodName = StringUtil.toLowerCaseFirstOne(methodName);
            if(clazz == null){
                String implClass = serializedLambda.getImplClass();
                implClass = implClass.replaceAll("/", ".");
                clazz = Class.forName(implClass);
            }
            for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {
                try {
                    Field field = clazz.getDeclaredField(methodName);
                    if (field.isAnnotationPresent(Column.class)) {
                        Column column = field.getAnnotation(Column.class);
                        methodName = column.name();
                        break;
                    }
                } catch (Exception e) {
                }
            }
            return methodName;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public interface Property<T, R> extends Function<T, R>, Serializable {
    }
}
