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
            sb.append(col.getTableField() + " " + col.getPropType());
            if (CheckUtil.isNotNull(col.getFieldLength()) && isHasLength(col.getPropType())) {
                sb.append("(" + col.getFieldLength() + ")");
            }
            sb.append(" ");
            if (col.getIsAuto() == 1) {
                sb.append("NOT NULL AUTO_INCREMENT");
                sb.append(" ");
            }
            if (col.getIsPk() == 1) {
                sb.append("PRIMARY KEY");
            }
            sb.append(" COMMENT \"" + col.getFieldComment() + "\",");
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
        sb.append("add " + resProp.getTableField() + " " + resProp.getPropType());
        if (CheckUtil.isNotNull(resProp.getFieldLength())&& isHasLength(resProp.getPropType())) {
            sb.append("(" + resProp.getFieldLength() + ")");
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
        str = resProp.getPropType();
        resProp.setPropName(GenCodeUtile.setColName(resProp.getTableField()));
        resProp.setPropType(jdbcToJava(str));
    }

    public static void setpropType(String dbType, ResProp resProp)  throws Exception{
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
        if (CheckUtil.isNull(resProp.getPropType())) {
            throw new CommonException(CommonException.RES_PROP_TYPE_IS_NULL);
        }
        String propType = null;
        switch (resProp.getPropType().toLowerCase()) {
            case "char":
                propType = "CHAR";
                break;
            case "int":
                propType = "NUMBER";
                break;
            case "string":
                propType = "VARCHAR";
                break;
            case "double":
                propType = "NUMBER";
                break;
            case "date":
                propType = "DATE";
                break;
            case "long":
                propType = "NUMBER";
                break;
            case "text":
                propType = "CLOB";
                break;
            default:
                propType = "请为java类型\"" + resProp.getPropType() + "\"设置对应oracle-jdbc类型";
                break;
        }
        resProp.setPropType(propType);
    }

    private static void javaToJdbcByMysql(ResProp resProp) throws Exception {
        if (CheckUtil.isNull(resProp.getPropType())) {
            throw new CommonException(CommonException.RES_PROP_TYPE_IS_NULL);
        }
        String propType = null;
        switch (resProp.getPropType().toLowerCase()) {
            case "long":
                propType = "bigint";
                resProp.setPropType("Long");
                resProp.setFieldLength("20");
                break;
            case "int":
                if(!CheckUtil.isNum(resProp.getFieldLength())){
                    throw new CommonException(CommonException.INT_INPUT_NOT_NUM);
                }
                Integer intLen = Integer.valueOf(resProp.getFieldLength());
                if(intLen<=0){
                    throw new CommonException(CommonException.INT_INPUT_LESS_ONE);
                }
                if(intLen<=255){
                    propType = "int";//255
                }else{
                    propType = "bigint";//20
                    resProp.setPropType("Long");
                    resProp.setFieldLength("20");
                }
                break;
            case "string":
                if(!CheckUtil.isNum(resProp.getFieldLength())){
                    throw new CommonException(CommonException.STRING_INPUT_NOT_NUM);
                }
                Integer strLen = Integer.valueOf(resProp.getFieldLength());
                if(strLen<=0){
                    throw new CommonException(CommonException.STRING_INPUT_LESS_ONE);
                }
                if(strLen<=1000){
                    propType = "varchar";
                }else{
                    propType = "text";
                    resProp.setFieldLength(null);
                }
                break;
            case "double":
                String reg = "^([1-5][0-9]|[6][0-5]|[1-9])(,([1-2][0-9]|[3][0]|[0-9]))?$";
                if( CheckUtil.match(resProp.getFieldLength(),reg)){
                    propType = "decimal";//65,30
                }else{
                    throw new CommonException(CommonException.DECIMAL_FORMAT_65_30);
                }
                break;
            case "date":
                propType = "datetime";
                resProp.setFieldLength(null);
                break;
            default:
                propType = "请为java类型\"" + resProp.getPropType() + "\"设置对应mysql-jdbc类型";
                break;
        }
        resProp.setPropType(propType);
    }


    public static String jdbcToJava(String propType) {
        if (CheckUtil.isNull(propType)) {
            return null;
        }
        String javaType = "";
        switch (propType) {
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
                javaType = "请为数据库类型\"" + propType + "\"设置对应java类型";
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
