package net.evecom.core.db.database.query;

import net.evecom.tools.constant.consts.SqlConst;
import net.evecom.utils.verify.CheckUtil;
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
     * 获取全部查询条件
     * @param list
     * @param query
     */
    public static Query<?> getQuery(List<QueryCondition> list, Query<?> query) {
        List<QueryCondition> temp_list = new ArrayList<QueryCondition>();
        List<QueryCondition> select_list = new ArrayList<QueryCondition>();
        List<QueryCondition> order_list = new ArrayList<QueryCondition>();
        List<QueryCondition> group_list = new ArrayList<QueryCondition>();
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
                String relation = sc.getRelation1();
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
         /*String sql = query.getSql().toString();
         System.out.println("sql:" + sql);
         List<Object> params = query.getParams();
         for (Object object : params) {
         System.out.print("," + object.toString());
         }*/
        return query;
    }

    /**
     * 获取子类查询条件
     *
     * @param query
     * @param list
     * @return
     */
    public static org.beetl.sql.core.query.QueryCondition getChildCondition(Query<?> query, List<QueryCondition> list) {
        Query<?> condition = null;
        QueryCondition sc = list.get(0);

        String relation = sc.getRelation() == null ? "" : sc.getRelation();
        switch (sc.getCondition()) {
            case SqlConst.IS_NULL:
                if (relation.equals(SqlConst.OR)) {
                    condition = query.condition().orIsNull(sc.getAttrName());
                } else if (relation.equals(SqlConst.AND)) {
                    condition = query.condition().andIsNull(sc.getAttrName());
                }
                break;
            case SqlConst.IS_NOT_NULL:
                if (relation.equals(SqlConst.OR)) {
                    condition = query.condition().orIsNotNull(sc.getAttrName());
                } else if (relation.equals(SqlConst.AND)) {
                    condition = query.condition().andIsNotNull(sc.getAttrName());
                }
                break;
            case SqlConst.LIKE:
                if (relation.equals(SqlConst.OR)) {
                    condition = query.condition().orLike(sc.getAttrName(), "%" + sc.getValue() + "%");
                } else if (relation.equals(SqlConst.AND)) {
                    condition = query.condition().andLike(sc.getAttrName(), "%" + sc.getValue() + "%");
                }
                break;
            case SqlConst.NOT_LIKE:
                if (relation.equals(SqlConst.OR)) {
                    condition = query.condition().orNotLike(sc.getAttrName(), "%" + sc.getValue() + "%");
                } else if (relation.equals(SqlConst.AND)) {
                    condition = query.condition().andNotLike(sc.getAttrName(), "%" + sc.getValue() + "%");
                }
                break;
            case SqlConst.EQ:
                if (relation.equals(SqlConst.OR)) {
                    condition = query.condition().orEq(sc.getAttrName(), sc.getValue());
                } else if (relation.equals(SqlConst.AND)) {
                    condition = query.condition().andEq(sc.getAttrName(), sc.getValue());
                }
                break;
            case SqlConst.NOT_EQ:
                if (relation.equals(SqlConst.OR)) {
                    condition = query.condition().orNotEq(sc.getAttrName(), sc.getValue());
                } else if (relation.equals(SqlConst.AND)) {
                    condition = query.condition().andNotEq(sc.getAttrName(), sc.getValue());
                }
                break;
            case SqlConst.BETWEEN:
                String[] values = sc.getValue().toString().split(",");
                if (relation.equals(SqlConst.OR)) {
                    condition = query.condition().orBetween(sc.getAttrName(), values[0], values[1]);
                } else if (relation.equals(SqlConst.AND)) {
                    condition = query.condition().andBetween(sc.getAttrName(), values[0], values[1]);
                }
                break;
            case SqlConst.NOT_BETWEEN:
                String[] notvalues = sc.getValue().toString().split(",");
                if (relation.equals(SqlConst.OR)) {
                    condition = query.condition().orNotBetween(sc.getAttrName(), notvalues[0], notvalues[1]);
                } else if (relation.equals(SqlConst.AND)) {
                    condition = query.condition().andNotBetween(sc.getAttrName(), notvalues[0], notvalues[1]);
                }
                break;
            case SqlConst.IN:
                String[] valueIn = sc.getValue().toString().split(",");
                if (relation.equals(SqlConst.OR)) {
                    condition = query.condition().orIn(sc.getAttrName(), Arrays.asList(valueIn));
                } else if (relation.equals(SqlConst.AND)) {
                    condition = query.condition().andIn(sc.getAttrName(), Arrays.asList(valueIn));
                }
                break;
            case SqlConst.NOT_IN:
                String[] valueNotIn = sc.getValue().toString().split(",");
                if (relation.equals(SqlConst.OR)) {
                    condition = query.condition().orNotIn(sc.getAttrName(), Arrays.asList(valueNotIn));
                } else if (relation.equals(SqlConst.AND)) {
                    condition = query.condition().andNotIn(sc.getAttrName(), Arrays.asList(valueNotIn));
                }
                break;
            case SqlConst.LESS:
                if (relation.equals(SqlConst.OR)) {
                    condition = query.condition().orLess(sc.getAttrName(), sc.getValue());
                } else if (relation.equals(SqlConst.AND)) {
                    condition = query.condition().andLess(sc.getAttrName(), sc.getValue());
                }
                break;
            case SqlConst.LESS_EQ:
                if (relation.equals(SqlConst.OR)) {
                    condition = query.condition().orLessEq(sc.getAttrName(), sc.getValue());
                } else if (relation.equals(SqlConst.AND)) {
                    condition = query.condition().andLessEq(sc.getAttrName(), sc.getValue());
                }
                break;
            case SqlConst.GREAT:
                if (relation.equals(SqlConst.OR)) {
                    condition = query.condition().orGreat(sc.getAttrName(), sc.getValue());
                } else if (relation.equals(SqlConst.AND)) {
                    condition = query.condition().andGreat(sc.getAttrName(), sc.getValue());
                }
                break;
            case SqlConst.GREAT_EQ:
                if (relation.equals(SqlConst.OR)) {
                    condition.orGreatEq(sc.getAttrName(), sc.getValue());
                } else if (relation.equals(SqlConst.AND)) {
                    condition = query.condition().andGreatEq(sc.getAttrName(), sc.getValue());
                }
                break;
            default:
                break;
        }

        list.remove(0);
        for (QueryCondition sc_child : list) {
            condition = getCondition(condition, sc_child);
        }
        return condition;
    }

    /**
     * 获取单个查询条件
     *
     * @param query
     * @param sc
     * @return
     */
    public static Query<?> getCondition(Query<?> query, QueryCondition sc) {
        Query<?> condition = query;
        String relation = sc.getRelation() == null ? "" : sc.getRelation();
        switch (sc.getCondition()) {
            case SqlConst.IS_NULL:
                if (relation.equals(SqlConst.OR)) {
                    condition.orIsNull(sc.getAttrName());
                } else if (relation.equals(SqlConst.AND)) {
                    condition.andIsNull(sc.getAttrName());
                }
                break;
            case SqlConst.IS_NOT_NULL:
                if (relation.equals(SqlConst.OR)) {
                    condition.orIsNotNull(sc.getAttrName());
                } else if (relation.equals(SqlConst.AND)) {
                    condition.andIsNotNull(sc.getAttrName());
                }
                break;
            case SqlConst.LIKE:
                if (relation.equals(SqlConst.OR)) {
                    condition.orLike(sc.getAttrName(), "%" + sc.getValue() + "%");
                } else if (relation.equals(SqlConst.AND)) {
                    condition.andLike(sc.getAttrName(), "%" + sc.getValue() + "%");
                }
                break;
            case SqlConst.NOT_LIKE:
                if (relation.equals(SqlConst.OR)) {
                    condition.orNotLike(sc.getAttrName(), "%" + sc.getValue() + "%");
                } else if (relation.equals(SqlConst.AND)) {
                    condition.andNotLike(sc.getAttrName(), "%" + sc.getValue() + "%");
                }
                break;
            case SqlConst.EQ:
                if (relation.equals(SqlConst.OR)) {
                    condition.orEq(sc.getAttrName(), sc.getValue());
                } else if (relation.equals(SqlConst.AND)) {
                    condition.andEq(sc.getAttrName(), sc.getValue());
                }
                break;
            case SqlConst.NOT_EQ:
                if (relation.equals(SqlConst.OR)) {
                    condition.orNotEq(sc.getAttrName(), sc.getValue());
                } else if (relation.equals(SqlConst.AND)) {
                    condition.andNotEq(sc.getAttrName(), sc.getValue());
                }
                break;
            case SqlConst.BETWEEN:
                String[] values = sc.getValue().toString().split(",");
                if (relation.equals(SqlConst.OR)) {
                    condition.orBetween(sc.getAttrName(), values[0], values[1]);
                } else if (relation.equals(SqlConst.AND)) {
                    condition.andBetween(sc.getAttrName(), values[0], values[1]);
                }
                break;
            case SqlConst.NOT_BETWEEN:
                String[] notvalues = sc.getValue().toString().split(",");
                if (relation.equals(SqlConst.OR)) {
                    condition.orNotBetween(sc.getAttrName(), notvalues[0], notvalues[1]);
                } else if (relation.equals(SqlConst.AND)) {
                    condition.andNotBetween(sc.getAttrName(), notvalues[0], notvalues[1]);
                }
                break;
            case SqlConst.IN:
                String[] valueIn = sc.getValue().toString().split(",");
                if (relation.equals(SqlConst.OR)) {
                    condition.orIn(sc.getAttrName(), Arrays.asList(valueIn));
                } else if (relation.equals(SqlConst.AND)) {
                    condition.andIn(sc.getAttrName(), Arrays.asList(valueIn));
                }
                break;
            case SqlConst.NOT_IN:
                String[] valueNotIn = sc.getValue().toString().split(",");
                if (relation.equals(SqlConst.OR)) {
                    condition.orNotIn(sc.getAttrName(), Arrays.asList(valueNotIn));
                } else if (relation.equals(SqlConst.AND)) {
                    condition.andNotIn(sc.getAttrName(), Arrays.asList(valueNotIn));
                }
                break;
            case SqlConst.LESS:
                if (relation.equals(SqlConst.OR)) {
                    condition.orLess(sc.getAttrName(), sc.getValue());
                } else if (relation.equals(SqlConst.AND)) {
                    condition.andLess(sc.getAttrName(), sc.getValue());
                }
                break;
            case SqlConst.LESS_EQ:
                if (relation.equals(SqlConst.OR)) {
                    condition.orLessEq(sc.getAttrName(), sc.getValue());
                } else if (relation.equals(SqlConst.AND)) {
                    condition.andLessEq(sc.getAttrName(), sc.getValue());
                }
                break;
            case SqlConst.GREAT:
                if (relation.equals(SqlConst.OR)) {
                    condition.orGreat(sc.getAttrName(), sc.getValue());
                } else if (relation.equals(SqlConst.AND)) {
                    condition.andGreat(sc.getAttrName(), sc.getValue());
                }
                break;
            case SqlConst.GREAT_EQ:
                if (relation.equals(SqlConst.OR)) {
                    condition.orGreatEq(sc.getAttrName(), sc.getValue());
                } else if (relation.equals(SqlConst.AND)) {
                    condition.andGreatEq(sc.getAttrName(), sc.getValue());
                }
                break;
            default:
                break;
        }
        return condition;
    }
}
