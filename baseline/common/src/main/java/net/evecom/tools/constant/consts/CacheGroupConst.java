package net.evecom.tools.constant.consts;

/**
 * @ClassName: CacheGroupConst
 * @Description: redis缓存分组常量表
 * @author： zhengc 
 * @date： 2018年5月30日
 */
public class CacheGroupConst {

    /**
     * 用户在redis中的id
     */
    public static final String COOKIE_USER_ID = "sid";
    /**
     * 用户在redis中的id
     */
    public static final String TICKET_USER_ID = "authorization";

	/**
	 * limit_size在redis中的id
	 */
	public static final String SYS_CONFIG = "sys_config";
	/**
	 * 用户在redis中的id
	 */
	public static final String CHECK_CODE_NAME = "code";
	/**
	 * 权限集合在redis中的id
	 */
	public static final String POWER_REDIS_ID = "power_list";
	/**
	 * 登录校验码
	 */
	public static final String LOGIN_CHECKCODE = "login_checkcode";

	/**
	 * 注册校验码
	 */
	public static final String REGISTER_CHECKCODE = "register_checkcode";

	/**
	 * 找回密码校验码
	 */
	public static final String FIND_CHECKCODE = "find_checkcode";

	/**
	 * 完善资料校验码
	 */
	public static final String PERFECT_CHECKCODE = "perfect_checkcode";
	/**
	 * 数据redis缓存库号
	 */
	public static final int DATACACHE_REIDS = 13;
	/**
	 * seesion redis缓存库号
	 */
	public static final int SESSION_REDIS = 14;
	/**
	 * 验证码 redis缓存库号
	 */
	public static final int CODE_REDIS = 15;
}
