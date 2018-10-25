package net.evecom.core.db.untis;

import net.evecom.core.db.model.entity.ResProp;
import net.evecom.tools.exception.CommonException;
import net.evecom.utils.verify.CheckUtil;

import java.util.*;

public class JdbcUtil {


    public static Boolean isHasLength(String columnName) {
        boolean bo = false;
        switch (columnName.toLowerCase()) {
            case "text":
                break;
            case "date":
                break;
            default:
                bo = true;
                break;
        }
        return bo;
    }

    public static String getDropSql(String tableName) {
        StringBuffer sb = new StringBuffer("");
        sb.append("drop table " + tableName + " ");
        return sb.toString();
    }

    public static String getCreateSql(String tableName, List<ResProp> columnList) {
        StringBuffer sb = new StringBuffer("");
        sb.append("CREATE TABLE " + tableName + " (");
        for (int i = 0; i < columnList.size(); i++) {
            ResProp col = columnList.get(i);
            sb.append(col.getJdbcField() + " " + col.getJdbcType());
            if (CheckUtil.isNotNull(col.getLength()) && isHasLength(col.getType())) {
                sb.append("(" + col.getLength() + ")");
            }
            sb.append(" ");
            if (col.getIsAuto() == 1) {
                sb.append("NOT NULL AUTO_INCREMENT");
                sb.append(" ");
            }
            if (col.getIsPk() == 1) {
                sb.append("PRIMARY KEY");
            }
            sb.append(" COMMENT \"" + col.getComments() + "\",");
        }
        String str = sb.toString().substring(0, sb.length() - 1) + ")";
        return str;
    }

    public static String delColumn(String tableName, String columnName) {
        StringBuffer sb = new StringBuffer();
        sb.append("alter table " + tableName + " drop column " + columnName);
        return sb.toString();
    }

    public static String getAddColumn(String tableName, ResProp resProp) {
        StringBuffer sb = new StringBuffer();
        sb.append("alter table " + tableName + " ");
        sb.append("add " + resProp.getJdbcField() + " " + resProp.getJdbcType());
        if (CheckUtil.isNotNull(resProp.getLength())&& isHasLength(resProp.getType())) {
            sb.append("(" + resProp.getLength() + ")");
        }
        return sb.toString();
    }

    public static void setAttrType(List<ResProp> list) {
        for (ResProp resProp : list) {
            setAttrType(resProp);
        }
    }

    public static void setAttrType(ResProp resProp) {
        // 设置数据与java类型匹配
        String str = "";
        str = resProp.getJdbcType();
        resProp.setName(GenCodeUtile.setColName(resProp.getJdbcField()));
        resProp.setType(jdbcToJava(str));
    }

    public static void setJdbcType(String dbType, ResProp resProp)  throws Exception{
        switch (dbType.toLowerCase()) {
            case "mysql":
                javaToJdbcByMysql(resProp);
                break;
            case "oracle":
                javaToJdbcByOracle(resProp);
                break;
            default:
                break;
        }
    }

    private static void javaToJdbcByOracle(ResProp resProp) throws Exception {
        if (CheckUtil.isNull(resProp.getType())) {
            throw new CommonException(CommonException.RES_PROP_TYPE_IS_NULL);
        }
        String jdbcType = null;
        switch (resProp.getType().toLowerCase()) {
            case "char":
                jdbcType = "CHAR";
                break;
            case "int":
                jdbcType = "NUMBER";
                break;
            case "string":
                jdbcType = "VARCHAR";
                break;
            case "double":
                jdbcType = "NUMBER";
                break;
            case "date":
                jdbcType = "DATE";
                break;
            case "long":
                jdbcType = "NUMBER";
                break;
            case "text":
                jdbcType = "CLOB";
                break;
            default:
                jdbcType = "请为java类型\"" + resProp.getType() + "\"设置对应oracle-jdbc类型";
                break;
        }
        resProp.setJdbcType(jdbcType);
    }

    private static void javaToJdbcByMysql(ResProp resProp) throws Exception {
        if (CheckUtil.isNull(resProp.getType())) {
            throw new CommonException(CommonException.RES_PROP_TYPE_IS_NULL);
        }
        String jdbcType = null;
        switch (resProp.getType().toLowerCase()) {
            case "long":
                jdbcType = "bigint";
                resProp.setType("Long");
                resProp.setLength("20");
                break;
            case "int":
                if(!CheckUtil.isNum(resProp.getLength())){
                    throw new CommonException(CommonException.INT_INPUT_NOT_NUM);
                }
                Integer intLen = Integer.valueOf(resProp.getLength());
                if(intLen<=0){
                    throw new CommonException(CommonException.INT_INPUT_LESS_ONE);
                }
                if(intLen<=255){
                    jdbcType = "int";//255
                }else{
                    jdbcType = "bigint";//20
                    resProp.setType("Long");
                    resProp.setLength("20");
                }
                break;
            case "string":
                if(!CheckUtil.isNum(resProp.getLength())){
                    throw new CommonException(CommonException.STRING_INPUT_NOT_NUM);
                }
                Integer strLen = Integer.valueOf(resProp.getLength());
                if(strLen<=0){
                    throw new CommonException(CommonException.STRING_INPUT_LESS_ONE);
                }
                if(strLen<=1000){
                    jdbcType = "varchar";
                }else{
                    jdbcType = "text";
                    resProp.setLength(null);
                }
                break;
            case "double":
                String reg = "^([1-5][0-9]|[6][0-5]|[1-9])(,([1-2][0-9]|[3][0]|[0-9]))?$";
                if( CheckUtil.match(resProp.getLength(),reg)){
                    jdbcType = "decimal";//65,30
                }else{
                    throw new CommonException(CommonException.DECIMAL_FORMAT_65_30);
                }
                break;
            case "date":
                jdbcType = "datetime";
                resProp.setLength(null);
                break;
            default:
                jdbcType = "请为java类型\"" + resProp.getType() + "\"设置对应mysql-jdbc类型";
                break;
        }
        resProp.setJdbcType(jdbcType);
    }


    public static String jdbcToJava(String jdbcType) {
        if (CheckUtil.isNull(jdbcType)) {
            return null;
        }
        String javaType = "";
        switch (jdbcType) {
            case "int":
                javaType = "Long";
                break;
            case "varchar":
                javaType = "String";
                break;
            case "double":
                javaType = "Double";
                break;
            case "datetime":
                javaType = "Date";
                break;
            case "char":
                javaType = "String";
                break;
            case "bigint":
                javaType = "Long";
                break;
            case "date":
                javaType = "Date";
                break;
            case "tinyint":
                javaType = "Integer";
                break;
            case "text":
                javaType = "String";
                break;
            default:
                javaType = "请为数据库类型\"" + jdbcType + "\"设置对应java类型";
                break;
        }
        return javaType;
    }

    public static Map<String, Object> getInsertSql(String tableName, Map<String, Object> hashMap) {
        Map<String, Object> map = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        StringBuffer values = new StringBuffer();
        sb.append("INSERT INTO " + tableName + " (");
        Iterator iterator = hashMap.keySet().iterator();
        List<Object> params = new ArrayList<>();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            sb.append(key);
            sb.append(",");
            values.append("?,");
            params.add(hashMap.get(key));
        }
        String insertSql = sb.substring(0, sb.length() - 1) + ") VALUES (" + values.substring(0, values.length() - 1) + ")";
        map.put("insertSql", insertSql);
        map.put("params", params);
        return map;
    }

    public static Map<String, Object> getUpdateSql(String tableName, Map<String, Object> hashMap) {
        Map<String, Object> map = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE " + tableName + " set ");
        Iterator iterator = hashMap.keySet().iterator();
        List<Object> params = new ArrayList<>();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            if (key.equals("id")) {
                continue;
            }
            sb.append(key);
            sb.append("=?,");
            params.add(hashMap.get(key));
        }
        String updateSql = sb.substring(0, sb.length() - 1) + " where id=?";
        params.add(hashMap.get("id"));
        map.put("updateSql", updateSql);
        map.put("params", params);
        return map;
    }

    public static Map<String, Object> getDeleteSql(String tableName, Map<String, Object> hashMap) {
        Map<String, Object> map = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM " + tableName + " WHERE ");
        Iterator iterator = hashMap.keySet().iterator();
        List<Object> params = new ArrayList<>();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            sb.append(" ");
            sb.append(key);
            sb.append("=? and");
            params.add(hashMap.get(key));
        }
        String deleteSql = sb.substring(0, sb.length() - 3);
        map.put("deleteSql", deleteSql);
        map.put("params", params);
        return map;
    }
}
