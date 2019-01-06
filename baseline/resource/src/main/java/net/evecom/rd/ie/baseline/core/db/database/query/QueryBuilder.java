package net.evecom.rd.ie.baseline.core.db.database.query;

import net.evecom.rd.ie.baseline.tools.constant.consts.SqlConst;
import net.evecom.rd.ie.baseline.utils.verify.CheckUtil;
import org.beetl.sql.core.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: QueryBuilder
 * @Description: SQL构造器
 * @author： zhengc
 * @date： 2018年5月30日
 */
public class QueryBuilder {
    /**
     * 条件集合
     */
    protected List<QueryCondition> list;

    /**
     * 获取全部查询条件
     * @param query
     */
    public Query<?> getQuery(Query<?> query) {
        List<QueryCondition> temp_list = new ArrayList<>();
        List<QueryCondition> select_list = new ArrayList<>();
        List<QueryCondition> order_list = new ArrayList<>();
        List<QueryCondition> group_list = new ArrayList<>();
        if (list != null && list.size() != 0) {
            for (QueryCondition qc : list) {
                if (qc.getCondition().equals(SqlConst.ORDERBY)) {
                    order_list.add(qc);
                } else if (qc.getCondition().equals(SqlConst.GROUPBY)) {
                    group_list.add(qc);
                }else {
                    Object value = qc.getValue();
                    if (!CheckUtil.isNull(value)) {
                        select_list.add(qc);
                    }
                }
            }
        }
        for (int i = 0; i < select_list.size(); i++) {
            QueryCondition sc = select_list.get(i);
            // 最后一个条件
            if ((i + 1) == select_list.size()) {
                // 取出并列关系
                String relation = sc.getRelation();
                // or 情况下,加入集合并拼接
                if (SqlConst.OR.equals(relation)) {
                    if (temp_list.size() < 2) {
                        temp_list.add(sc);
                    }
                    query.and(getChildCondition(query, temp_list));
                    temp_list.clear();
                } else {
                    // and 情况下,直接拼接
                    getCondition(query, sc);
                }
                continue;
            }
            // 不是最后一个条件,取出下一个条件
            QueryCondition sc2 = select_list.get(i + 1);
            // 取出并列关系
            String relation = sc2.getRelation();
            // 判断集合长度,查看上一个条件是否已加入集合
            int size = temp_list.size();
            // or 情况下,加入集合
            if (SqlConst.OR.equals(relation)) {
                if (size == 0) {
                    temp_list.add(sc);
                }
                temp_list.add(sc2);
            } else {
                // and 情况下,查看集合长度 大于0,拼接上一组条件,并直接拼接本组
                boolean bo = temp_list.size() > 0;
                if (bo) {
                    query.and(getChildCondition(query, temp_list));
                    temp_list.clear();
                } else {
                    getCondition(query, sc);
                }
            }
        }
        //拼接groupby
        if (group_list.size() > 0) {
            for (int i = 0; i < group_list.size(); i++) {
                query.groupBy(group_list.get(i).getAttrName());
            }
        }
        //拼接orderby
        if (order_list.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < order_list.size(); i++) {
                QueryCondition sc = order_list.get(i);
                String relation = sc.getRelation();
                if (relation.equals(SqlConst.DESC ) || relation.equals(SqlConst.ASC) || relation.equals("")) {
                    sb.append(sc.getAttrName());
                    sb.append(" " + sc.getRelation());
                    if (i != order_list.size() - 1) {
                        sb.append(",");
                    }
                }
                else {
                    System.out.println("order by 条件不能跟随===>>>>" + relation);
                }
            }
            query.orderBy(sb.toString());
        }
        return query;
    }

    /**
     * 获取子类查询条件
     * @param query
     * @param list
     * @return
     */
    public org.beetl.sql.core.query.QueryCondition getChildCondition(Query<?> query, List<QueryCondition> list) {
        Query<?> condition = getCondition(query.condition(), list.get(0));
        list.remove(0);
        for (QueryCondition sc_child : list) {
            condition = getCondition(condition, sc_child);
        }
        return condition;
    }

    /**
     * 获取单个查询条件
     * @param query
     * @param condition
     * @return
     */

    public Query<?> getCondition(Query<?> query, QueryCondition condition) {
        String relation = condition.getRelation() == null ? "" : condition.getRelation();
        switch (condition.getCondition()) {
            case SqlConst.IS_NULL:
                if (relation.equals(SqlConst.OR)) {
                    query.orIsNull(condition.getAttrName());
                } else if (relation.equals(SqlConst.AND)) {
                    query.andIsNull(condition.getAttrName());
                }
                break;
            case SqlConst.IS_NOT_NULL:
                if (relation.equals(SqlConst.OR)) {
                    query.orIsNotNull(condition.getAttrName());
                } else if (relation.equals(SqlConst.AND)) {
                    query.andIsNotNull(condition.getAttrName());
                }
                break;
            case SqlConst.LIKE:
                if (relation.equals(SqlConst.OR)) {
                    query.orLike(condition.getAttrName(), "%" + condition.getValue() + "%");
                } else if (relation.equals(SqlConst.AND)) {
                    query.andLike(condition.getAttrName(), "%" + condition.getValue() + "%");
                }
                break;
            case SqlConst.NOT_LIKE:
                if (relation.equals(SqlConst.OR)) {
                    query.orNotLike(condition.getAttrName(), "%" + condition.getValue() + "%");
                } else if (relation.equals(SqlConst.AND)) {
                    query.andNotLike(condition.getAttrName(), "%" + condition.getValue() + "%");
                }
                break;
            case SqlConst.EQ:
                if (relation.equals(SqlConst.OR)) {
                    query.orEq(condition.getAttrName(), condition.getValue());
                } else if (relation.equals(SqlConst.AND)) {
                    query.andEq(condition.getAttrName(), condition.getValue());
                }
                break;
            case SqlConst.NOT_EQ:
                if (relation.equals(SqlConst.OR)) {
                    query.orNotEq(condition.getAttrName(), condition.getValue());
                } else if (relation.equals(SqlConst.AND)) {
                    query.andNotEq(condition.getAttrName(), condition.getValue());
                }
                break;
            case SqlConst.BETWEEN:
                String[] values = condition.getValue().toString().split(",");
                if (relation.equals(SqlConst.OR)) {
                    query.orBetween(condition.getAttrName(), values[0], values[1]);
                } else if (relation.equals(SqlConst.AND)) {
                    query.andBetween(condition.getAttrName(), values[0], values[1]);
                }
                break;
            case SqlConst.NOT_BETWEEN:
                String[] notvalues = condition.getValue().toString().split(",");
                if (relation.equals(SqlConst.OR)) {
                    query.orNotBetween(condition.getAttrName(), notvalues[0], notvalues[1]);
                } else if (relation.equals(SqlConst.AND)) {
                    query.andNotBetween(condition.getAttrName(), notvalues[0], notvalues[1]);
                }
                break;
            case SqlConst.IN:
                String[] valueIn = condition.getValue().toString().split(",");
                if (relation.equals(SqlConst.OR)) {
                    query.orIn(condition.getAttrName(), Arrays.asList(valueIn));
                } else if (relation.equals(SqlConst.AND)) {
                    query.andIn(condition.getAttrName(), Arrays.asList(valueIn));
                }
                break;
            case SqlConst.NOT_IN:
                String[] valueNotIn = condition.getValue().toString().split(",");
                if (relation.equals(SqlConst.OR)) {
                    query.orNotIn(condition.getAttrName(), Arrays.asList(valueNotIn));
                } else if (relation.equals(SqlConst.AND)) {
                    query.andNotIn(condition.getAttrName(), Arrays.asList(valueNotIn));
                }
                break;
            case SqlConst.LESS:
                if (relation.equals(SqlConst.OR)) {
                    query.orLess(condition.getAttrName(), condition.getValue());
                } else if (relation.equals(SqlConst.AND)) {
                    query.andLess(condition.getAttrName(), condition.getValue());
                }
                break;
            case SqlConst.LESS_EQ:
                if (relation.equals(SqlConst.OR)) {
                    query.orLessEq(condition.getAttrName(), condition.getValue());
                } else if (relation.equals(SqlConst.AND)) {
                    query.andLessEq(condition.getAttrName(), condition.getValue());
                }
                break;
            case SqlConst.GREAT:
                if (relation.equals(SqlConst.OR)) {
                    query.orGreat(condition.getAttrName(), condition.getValue());
                } else if (relation.equals(SqlConst.AND)) {
                    query.andGreat(condition.getAttrName(), condition.getValue());
                }
                break;
            case SqlConst.GREAT_EQ:
                if (relation.equals(SqlConst.OR)) {
                    query.orGreatEq(condition.getAttrName(), condition.getValue());
                } else if (relation.equals(SqlConst.AND)) {
                    query.andGreatEq(condition.getAttrName(), condition.getValue());
                }
                break;
            default:
                break;
        }
        return query;
    }

    public String buildSql(String sql, String conditionSql) {
        StringBuilder sb = new StringBuilder(sql);
        int i = sb.indexOf("1=1");
        if (i > -1) {
            sb.insert(i+3,conditionSql);
        }else{
            sb.append(conditionSql);
        }
        return sb.toString();
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
}
