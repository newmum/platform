package net.evecom.tools.exception;

/**
 * @ClassName: CommonException
 * @Description: 通用异常类
 * @author: zhengc
 * @date: 2018年6月7日
 */
public class CommonException extends RuntimeException {
	public static final String UI_COMPONET_PROP_HAS_EXIST = "ui_componet_prop_has_exist";
	public static final String CREATE_TABLE_RESPROPLIST_NULL = "create_table_resPropList_null";
	public static final String BATCH_ADD_FAILED = "batch_add_failed";
	public static final String SYSTEM_FIELD_NOT_DELETE = "system_field_not_delete";
	public static final String DECIMAL_FORMAT_65_30 = "decimal_format_(65,30)";
	public static final String INT_INPUT_NOT_NUM = "int_input_not_num";
	public static final String INT_INPUT_LESS_ONE = "int_input_less_one";
	public static final String STRING_INPUT_NOT_NUM = "String_input_not_num";
	public static final String STRING_INPUT_LESS_ONE = "String_input_less_one";
	public static final String RES_PROP_TYPE_IS_NULL = "res_prop_type_is_null";
	public static final String UI_COMPONET_PROP_ALL = "ui_componet_prop_all";

	public static final String JSON_FORMAT_ERROR = "json_format_error";
	public static final String TABLE_NAME_NULL = "table_name_null";
	public static final String CATEGORY_MODULE_NAME_NULL = "category_module_name_null";
	public static final String OPERATE_FAILED = "operate_failed";
	public static final String USERIDS_NULL = "userIds_null";
	public static final String GROUPID_NULL = "groupid_null";
	public static final String AGAIN_LOGIN = "again_login";
	public static final String NO_POWER = "no_power";
	public static final String NO_LOGIN = "no_login";
	public static final String NULL_POINTER = "null_pointer";
	public static final String DATA_NULL = "data_null";
	public static final String PARAM_NULL = "param_null";
	public static final String CLASS_NOT_FOUND = "class_not_found";
	public static final String TABLE_HAS_EXIST = "table_has_exist";

	public String errMsg;

	public CommonException(String msg) {
		super(msg);
		this.errMsg = msg;
	}

}
