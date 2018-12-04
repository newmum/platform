package net.evecom.rd.ie.baseline.core.rbac.exception;

import net.evecom.rd.ie.baseline.tools.exception.CommonException;

/**
 * @Description: 用户异常类
 * @author: zhengc
 * @date: 2018年6月7日
 */
public class UserException extends CommonException {

	public static final String PERFECT_EMAIL_VALIDATE_NULL = "perfect_email_validate_null";// 完善资料邮箱验证为空
	public static final String VALIDATE_IMAGE_INPUT_NULL = "validate_image_input_null";// 图形验证码输入为空
	public static final String PERFECT_EMAIL_VALIDATE_ERROR = "perfect_email_validate_error";// 完善资料邮箱验证错误
	public static final String PERFECT_MOBILE_VALIDATE_NULL = "perfect_mobile_validate_null";// 完善资料手机验证为空
	public static final String PERFECT_MOBILE_VALIDATE_ERROR = "perfect_mobile_validate_error";// 完善资料手机验证错误
	public static final String ILLEGAL_USER = "illegal_user";// 非法用户
	public static final String USER_NO_LOGIN = "user_no_login";// 用户未登录
	public static final String TYPE_NO_EXIST = "type_no_exist";// 操作的类型不存在
	public static final String TEMPLATE_NO_EXIST = "template_no_exist";// 模板不存在
	public static final String USER_NO_EXIST = "user_no_exist";// 用户不存在
	public static final String MOBILE_EMAIL_NULL = "mobile_email_null";// 手机,邮箱为空
	public static final String ACCOUNT_HAS_EXIST = "account_has_exist";// 账号已存在
	public static final String MOBILE_EMAIL_ACCOUNT_NULL = "mobile_email_account_null";// 手机,邮箱,账号为空
	public static final String PASSWORD_UPDATE_FAILED = "password_update_failed";// 密码更新失败
	public static final String PASSWORD_INPUT_NULL = "password_input_null";// 密码输入为空
	public static final String PASSWORDOLD_INPUT_NULL = "passwordOld_input_null";// 旧密码输入为空
	public static final String PASSWORDNEW_INPUT_NULL = "passwordNew_input_null";// 新密码输入为空
	public static final String PASSWORDNEW_NO_EQUALS = "passwordnew_no_equals";// 两次新密码不相同
	public static final String PASSWORDNEW_NO_EQUAL_PASSWORDOLD = "passwordNew_no_equal_passwordOld";// 新旧密码不相等
	public static final String PASSWORD_ERROR = "password_error";// 密码错误
	public static final String VALIDATE_INPUT_NULL = "validate_input_null";// 验证码输入为空
	public static final String VALIDATE_NULL = "validate_null";// 缓存中验证码为空
	public static final String VALIDATE_ERROR = "validate_error";// 验证码错误
	public static final String LOGOUT_FAILED = "logout_failed";// 注销失败
	public static final String REGISTER_ERROR = "register_error";// 注册失败
	public static final String MOBILE_NO_EXIST = "mobile_no_exist";// 手机不存在
	public static final String MOBILE_HAS_EXIST = "mobile_has_exist";// 手机已存在
	public static final String MOBILE_FORMAT_ERROR = "mobile_format_error";// 手机格式错误
	public static final String EMAIL_FORMAT_ERROR = "email_format_error";// 邮箱格式错误
	public static final String EMAIL_HAS_EXIST = "email_has_exist";// 邮箱已存在
	public static final String EMAIL_NO_EXIST = "email_no_exist";// 邮箱不存在
	public static final String EMAIL_SEND_ERROR = "email_send_error";// 邮件发送失败
	public static final String SMS_SEND_ERROR = "sms_send_error";// 短信发送失败
	public static final String ACCOUNT_FORMAT_3TO20 = "account_format_3to20";// 账号格式错误

	public UserException(String msg) {
		super(msg);
		this.errMsg = msg;
	}

}
