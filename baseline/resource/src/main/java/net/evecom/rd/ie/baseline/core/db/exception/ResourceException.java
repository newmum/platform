package net.evecom.rd.ie.baseline.core.db.exception;

import net.evecom.rd.ie.baseline.tools.exception.CommonException;

/**
 * @Description: 资源异常类
 * @author: zhengc @date: 2018年6月8日
 */
public class ResourceException extends CommonException {
	public static final String ID_NULL = "id_null";// 更新时id为null
	public static final String RESOURCE_NO_EXIST = "resource_no_exist";// 资源不存在
	public static final String RESOURCE_PROP_NO_EXIST = "resource_prop_no_exist";// 资源属性不存在
	public static final String RESOURCE_PROP_EXL_NO_EXIST = "resource_prop_exl_no_exist";// 资源报表属性不存在
	public static final String JDBC_FIELD_HAS_EXIST = "jdbc_field_has_exist";// 资源不存在
	public static final String ID_NO_EXIST = "id_no_exist";// id不存在

	public static final String RESOURCE_NO_FOUND = "resource_no_found";// 对象路径有误

	public static final String RESOURCE_TABLE_NAME_NULL = "resource_table_name_null";// 表格名称为空
	public static final String RESOURCE_NAME_NULL = "resource_name_null";// 名称为空

	public static final String BASE_CLASS_NULL = "base_class_null";// 基本类型,路径不能为空
	public static final String SQL_CLASS_NULL = "base_sql_null";// sql类型,SQL语句不能为空
	public static final String NAME_HAS_EXIST = "name_has_exist";// 名称已存在
	public static final String TABLE_NAME_HAS_EXIST = "table_name_has_exist";// 表格名称已存在
	public static final String NOT_FOLDER = "not_folder";// 表格名称已存在

	public static final String CHECK_NOT_PASS = "check_not_pass";// 验证不通过

	public ResourceException(String msg) {
		super(msg);
		this.errMsg = msg;
	}

}
