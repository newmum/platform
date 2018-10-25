package net.evecom.core.rbac.model.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.evecom.core.rbac.model.entity.*;
import net.evecom.core.rbac.base.BaseService;
import net.evecom.core.rbac.model.dao.ICrmUserDao;
import net.evecom.core.rbac.model.entity.*;
import net.evecom.core.db.exception.ResourceException;
import net.evecom.core.rbac.exception.UserException;
import net.evecom.core.db.model.service.ResourceService;
import net.evecom.tools.constant.consts.CacheGroupConst;
import net.evecom.core.db.database.query.QueryParam;
import net.evecom.tools.exception.CommonException;
import net.evecom.tools.message.email.SendEmail;
import net.evecom.tools.message.sms.SendSMS;
import net.evecom.utils.database.redis.RedisClient;
import net.evecom.utils.datetime.DTUtil;
import net.evecom.utils.request.IPUtils;
import net.evecom.utils.string.RandomUtil;
import net.evecom.utils.string.StringUtil;
import net.evecom.utils.verify.CheckUtil;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.db.DBStyle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service("userService")
public class UserService extends BaseService {
    @Resource(name = "redisClient")
    protected RedisClient redisClient;
    @Resource
    private ICrmUserDao userDao;
    @Resource(name = "sendEmail")
    private SendEmail sendEmail;
    @Resource
    private ResourceService resourceService;
    @Resource
    private ObjectMapper objectMapper;

    public CrmUser add(CrmUser user) throws Exception {
        resourceService.add(user);
        CrmUserExtra userExtra = new CrmUserExtra();
        userExtra.setCrmUserId(user.getId());
        userExtra.preInsert();
        resourceService.add(userExtra);
        return user;
    }

    public void delete(CrmUser user) throws Exception {
        user.setStatus(CrmUser.DEL_FLAG_DELETE);
        user.preUpdate();
        int i = userDao.updateTemplateById(user);
        if (i <= 0) {
            throw new CommonException(CommonException.OPERATE_FAILED);
        }
    }

    public void editCheck(CrmUser user) throws Exception {
        if (CheckUtil.isNull(user.getId())) {
            throw new ResourceException(ResourceException.ID_NULL);
        }
        if (CheckUtil.isNull(user.getMobile()) && CheckUtil.isNull(user.getEmail())
                && CheckUtil.isNull(user.getAccount())) {
            throw new UserException(UserException.MOBILE_EMAIL_ACCOUNT_NULL);
        }
        if (CheckUtil.isNotNull(user.getMobile())) {
            checkUser(user.getId(), user.getMobile(), 1);
        }
        if (CheckUtil.isNotNull(user.getEmail())) {
            checkUser(user.getId(), user.getEmail(), 2);
        }
        if (CheckUtil.isNotNull(user.getAccount())) {
            checkUser(user.getId(), user.getAccount(), 3);
        }
    }

    public CrmUser edit(CrmUser user) throws Exception {
        user.preUpdate();
        int i = userDao.updateTemplateById(user);
        if (i <= 0) {
            throw new CommonException(CommonException.OPERATE_FAILED);
        }
        return user;
    }

    /**
     * 手机登录验证
     *
     * @param mobile   手机号
     * @param validate 验证码
     * @return
     */
    public CrmUser mobileloginCheck(String mobile, String validate) throws Exception {
        if (!CheckUtil.isNotNull(validate)) {
            throw new UserException(UserException.VALIDATE_INPUT_NULL);
        }
        CrmUser user = new CrmUser();
        if (CheckUtil.isMobile(mobile)) {
            user.setMobile(mobile);
            user = userDao.templateOne(user);
            if (user == null) {
                throw new UserException(UserException.MOBILE_NO_EXIST);
            }
        } else {
            throw new UserException(UserException.MOBILE_FORMAT_ERROR);
        }

        Object redis_validate = redisClient.get(CacheGroupConst.CODE_REDIS, CacheGroupConst.LOGIN_CHECKCODE + mobile);
        redis_validate = (redis_validate == null ? "" : redis_validate.toString());
        if (CheckUtil.isNull(redis_validate)) {
            throw new UserException(UserException.VALIDATE_NULL);
        }
        if (!redis_validate.equals(validate)) {
            throw new UserException(UserException.VALIDATE_ERROR);
        }
        return user;
    }

    public List<Long> batchdelete(Long[] ids) throws Exception {
        List<Long> result = new ArrayList<Long>();
        CrmUser user = new CrmUser();
        user.setStatus(CrmUser.DEL_FLAG_DELETE);
        user.preUpdate();
        for (Long id : ids) {
            user.setId(id);
            int i = userDao.updateTemplateById(user);
            if (i <= 0) {
                result.add(id);
            }
        }
        return result;
    }

    /**
     * 登录验证
     *
     * @param account  手机号/邮箱/账号
     * @param password 密码
     * @return
     */
    public CrmUser loginCheck(HttpSession session, String account, String password, String validate) throws Exception {
        Object check_code = session.getAttribute(CacheGroupConst.CHECK_CODE_NAME);
        if (check_code != null && !check_code.toString().equals(validate)) {
            throw new UserException(UserException.VALIDATE_ERROR);
        }
        if (CheckUtil.isNull(password)) {
            throw new UserException(UserException.PASSWORD_INPUT_NULL);
        }
        CrmUser user = new CrmUser();
        if (CheckUtil.isEmail(account)) {
            user.setEmail(account);
        } else if (CheckUtil.isMobile(account)) {
            user.setMobile(account);
        } else if (CheckUtil.stringLengthCheck(account, 3, 20)) {
            user.setAccount(account);
        } else {
            throw new UserException(UserException.ACCOUNT_FORMAT_3TO20);
        }
        SQLManager sqlManager = userDao.getSQLManager();
        DBStyle dbStyle = sqlManager.getDbStyle();
        dbStyle.getName();
        user = userDao.templateOne(user);
        if (user == null) {
            throw new UserException(UserException.USER_NO_EXIST);
        }
        if (!user.getPassword().equals(password)) {
            throw new UserException(UserException.PASSWORD_ERROR);
        }
        return user;
    }

    public CrmUser login(CrmUser user, HttpServletResponse response, HttpServletRequest request) throws Exception {
        setUserExtra(user);
        try {
            logout(request, response);
        } catch (Exception e) {

        }
        if(user.getId()==1){
            //admin 用户 获取所有权限,菜单
            QueryParam queryParam=new QueryParam();
            queryParam.setNeedPage(false);
            queryParam.setNeedTotal(false);
            user.setMenuList((List<UiRouter>) resourceService.list(UiRouter.class,queryParam).getList());
            user.setPowerList((List<CrmPower>) resourceService.list(CrmPower.class,queryParam).getList());
        }else{
            List<UiRouter> menuList = userDao.getMenuList(user.getId());
            List<CrmPower> powerList = userDao.getPowerList(user.getId());
            user.setMenuList(menuList);
            user.setPowerList(powerList);
        }
        List<CrmRole> roleList = userDao.getRoleList(user.getId());
        user.setRoleList(roleList);


        // 保存登录日志
        String login_ip = IPUtils.getIpAddr(request);
        System.out.println(login_ip);
        CrmUserloginlog userLog = new CrmUserloginlog();
        userLog.setIp(login_ip);
        userLog.setCrmUserId(user.getId());
        user.setLoginIp(login_ip);
        user.setLoginDate(DTUtil.getNowDataStr());
        String sid = saveLoginUser(user, response);
        resourceService.add(userLog);

        // User temp = iUserDao.queryUsers(user.getId());
        // List<Role> roles = (List<Role>) temp.get("role");
        // List<Menu> menu = (List<Menu>) temp.get("menu");
        // List<Power> power = (List<Power>) temp.get("power");
        // temp.setRoleList(roles);
        // temp.setMenuList(menu);
        // temp.setPowerList(power);

        return user;
    }

    public void setUserExtra(CrmUser user) {
        QueryParam<CrmUserExtra> queryParam = new QueryParam<>();
        queryParam.append(CrmUserExtra::getCrmUserId, user.getId());
        CrmUserExtra userExtra;
        try {
            userExtra = (CrmUserExtra) resourceService.get(CrmUserExtra.class, queryParam);
            CrmOffice office = (CrmOffice) resourceService.get(CrmOffice.class, user.getCrmOfficeId());
            user.setCrmOffice(office);
        } catch (Exception e) {
            userExtra = new CrmUserExtra();
            userExtra.setCrmUserId(user.getId());
        }
        user.setCrmUserExtra(userExtra);
    }

    public CrmUser passwordRecoveryCheck(String mobile, String email, String validate, String password)
            throws Exception {
        // 类型 1手机号找回2邮箱找回
        if (CheckUtil.isNull(validate)) {
            throw new UserException(UserException.VALIDATE_INPUT_NULL);
        }
        if (CheckUtil.isNull(password)) {
            throw new UserException(UserException.PASSWORD_INPUT_NULL);
        }
        CrmUser user = new CrmUser();
        String hint = "";
        if (CheckUtil.isNotNull(mobile)) {
            if (!CheckUtil.isMobile(mobile)) {
                throw new UserException(UserException.MOBILE_FORMAT_ERROR);
            }
            user.setMobile(mobile);
            hint = "mobile";
        } else if (CheckUtil.isNotNull(email)) {
            if (!CheckUtil.isEmail(email)) {
                throw new UserException(UserException.EMAIL_FORMAT_ERROR);
            }
            user.setEmail(email);
            hint = "email";
        } else {
            throw new UserException(UserException.MOBILE_EMAIL_NULL);
        }
        CrmUser temp = userDao.templateOne(user);
        if (temp == null) {
            if (hint.equals("mobile")) {
                throw new UserException(UserException.MOBILE_NO_EXIST);
            } else {
                throw new UserException(UserException.EMAIL_NO_EXIST);
            }
        } else {
            user.setId(temp.getId());
            String key = CacheGroupConst.FIND_CHECKCODE;
            if (hint.equals("mobile")) {
                key += mobile;
            } else {
                key += email;
            }
            Object redis_validate = redisClient.get(CacheGroupConst.CODE_REDIS, key);
            redis_validate = (redis_validate == null ? "" : redis_validate.toString());
            if (CheckUtil.isNull(redis_validate)) {
                throw new UserException(UserException.VALIDATE_NULL);
            }
            if (!redis_validate.equals(validate)) {
                throw new UserException(UserException.VALIDATE_ERROR);
            }
        }
        user.setPassword(password);
        return user;
    }

    public void passwordRecovery(CrmUser user) throws Exception {
        try {
            user.preUpdate();
            int i = userDao.updateTemplateById(user);
            if (i <= 0) {
                throw new UserException(UserException.PASSWORD_UPDATE_FAILED);
            }
        } catch (Exception e) {
            throw new UserException(UserException.PASSWORD_UPDATE_FAILED);
        }
    }

    public CrmUser registerCheck(String account, String validate, String mobile, String email, String password)
            throws Exception {
        if (CheckUtil.isNull(validate)) {
            throw new UserException(UserException.VALIDATE_INPUT_NULL);
        }
        CrmUser user = null;
        String key = CacheGroupConst.REGISTER_CHECKCODE;
        if (CheckUtil.isNotNull(mobile)) {
            user = checkUser(null, mobile, 1);
            key += mobile;
        } else if (CheckUtil.isNotNull(email)) {
            user = checkUser(null, email, 2);
            key += email;
        } else if (CheckUtil.isNotNull(account)) {
            user = checkUser(null, account, 3);
            key += account;
        } else {
            throw new UserException(UserException.MOBILE_EMAIL_NULL);
        }
        Object redis_validate = redisClient.get(CacheGroupConst.CODE_REDIS, key);
        redis_validate = (redis_validate == null ? "" : redis_validate.toString());
        if (CheckUtil.isNull(redis_validate)) {
            throw new UserException(UserException.VALIDATE_NULL);
        }
        if (!redis_validate.equals(validate)) {
            throw new UserException(UserException.VALIDATE_ERROR);
        }
        return user;
    }

    /**
     * 账号注册
     *
     * @param user
     * @return
     * @throws Exception
     */
    public CrmUser register(CrmUser user) throws Exception {
        user = add(user);
        if (user == null) {
            throw new UserException(UserException.REGISTER_ERROR);
        }
        // 新注册用户默认添加基础角色(id为2)
        CrmUserRole crmUserRole = new CrmUserRole();
        crmUserRole.setCrmUserId(user.getId());
        crmUserRole.setCrmRoleId(2L);
        resourceService.add(crmUserRole);
        return user;
    }

    /**
     * @param id      用户id,新增时为空
     * @param account 1手机号2邮箱3账号
     * @param type    类型 1手机号2邮箱3账号
     * @return
     * @throws Exception
     */
    public CrmUser checkUser(Long id, String account, Integer type) throws Exception {
        //
        CrmUser user = new CrmUser();
        if (type == null) {
            throw new UserException(UserException.TYPE_NO_EXIST);
        }
        if (type == 1) {
            if (CheckUtil.isMobile(account)) {
                user.setMobile(account);
            } else {
                throw new UserException(UserException.MOBILE_FORMAT_ERROR);
            }
        } else if (type == 2) {
            if (CheckUtil.isEmail(account)) {
                user.setEmail(account);
            } else {
                throw new UserException(UserException.EMAIL_FORMAT_ERROR);
            }
        } else if (type == 3) {
            if (CheckUtil.stringLengthCheck(account, 3, 20)) {
                user.setAccount(account);
            } else {
                throw new UserException(UserException.ACCOUNT_FORMAT_3TO20);
            }
        } else {
            throw new UserException(UserException.TYPE_NO_EXIST);
        }
        CrmUser temp = userDao.templateOne(user);
        if (temp != null && temp.getId() != id) {
            if (type == 1) {
                throw new UserException(UserException.MOBILE_HAS_EXIST);
            } else if (type == 2) {
                throw new UserException(UserException.EMAIL_HAS_EXIST);
            } else if (type == 3) {
                throw new UserException(UserException.ACCOUNT_HAS_EXIST);
            } else {
                throw new UserException(UserException.TYPE_NO_EXIST);
            }
        }
        return user;
    }

    public MessageSms sendSMSCheck(HttpServletRequest request, String mobile, Integer id, String validateImageCode) throws Exception {
       if(CheckUtil.isNull(validateImageCode)){
           throw new UserException(UserException.VALIDATE_IMAGE_INPUT_NULL);
       }
        Object check_code = request.getSession().getAttribute(CacheGroupConst.CHECK_CODE_NAME);
        if (check_code != null && !check_code.toString().equals(validateImageCode)) {
            throw new UserException(UserException.VALIDATE_ERROR);
        }
        if (!CheckUtil.isMobile(mobile)) {
            throw new UserException(UserException.MOBILE_FORMAT_ERROR);
        }
        if (id == null) {
            throw new UserException(UserException.TYPE_NO_EXIST);
        }
        if (id == 4) {
            try {
                loginUser(request);
            } catch (Exception e) {
                throw new UserException(UserException.USER_NO_LOGIN);
            }
        }
        CrmUser user = new CrmUser();
        user.setMobile(mobile);
        CrmUser temp = userDao.templateOne(user);
        if ((id == 2) && temp != null) {
            throw new UserException(UserException.MOBILE_HAS_EXIST);
        } else if ((id == 3 || id == 1 || id == 4) && temp == null) {
            throw new UserException(UserException.MOBILE_NO_EXIST);
        } else if (id > 4 || id <= 0) {
            throw new UserException(UserException.TYPE_NO_EXIST);
        }
        MessageSms messageSms = (MessageSms) resourceService.get(MessageSms.class, Long.valueOf(id));
        if (messageSms == null) {
            throw new UserException(UserException.TEMPLATE_NO_EXIST);
        }
        return messageSms;
    }

    public void sendSMS(String mobile, int id, MessageSms messageSms) throws Exception {
        Map<String,Object>map=new HashMap<String,Object>();
        map.put("mobile",mobile);
        map.put("id",id);
        String json = objectMapper.writeValueAsString(messageSms);
        map.put("messageSms",json);
        //RsMq.send("sms", "java-platform-rsmq", "controller", map);
    }
    public  void trueSendSMS(String mobile, int id, MessageSms messageSms) throws Exception {
        String text = messageSms.getContent();
        String validate = RandomUtil.randomString("0123456789", 4);
        Map<String, String> map = new HashMap<String, String>();
        map.put("validate", validate);
        text = StringUtil.messageTemplate(text, map);
        boolean bo = SendSMS.send_sms2(text, mobile);
        if (!bo) {
            throw new UserException(UserException.SMS_SEND_ERROR);
        }
        System.out.println("mobile:" + mobile + ";text:" + text);
        //保存至短信推送记录表
        MessageSmsPush smsPush=new MessageSmsPush();
        smsPush.setContent(text);
        smsPush.setMobile(mobile);
        smsPush.setName(messageSms.getName());
        resourceService.add(smsPush);
        mobile=getRedisKey(id,mobile);
        redisClient.set(CacheGroupConst.CODE_REDIS, mobile, validate, 5 * 60);
    }
    public MessageEmail sendEmailCheck(HttpServletRequest request, String email, Integer id, String validateImageCode) throws Exception {
        if(CheckUtil.isNull(validateImageCode)){
            throw new UserException(UserException.VALIDATE_IMAGE_INPUT_NULL);
        }
        Object check_code = request.getSession().getAttribute(CacheGroupConst.CHECK_CODE_NAME);
        if (check_code != null && !check_code.toString().equals(validateImageCode)) {
            throw new UserException(UserException.VALIDATE_ERROR);
        }
        if (!CheckUtil.isEmail(email)) {
            throw new UserException(UserException.EMAIL_FORMAT_ERROR);
        }
        if (id == null) {
            throw new UserException(UserException.TYPE_NO_EXIST);
        }
        if (id == 4) {
            try {
                loginUser(request);
            } catch (Exception e) {
                throw new UserException(UserException.USER_NO_LOGIN);
            }
        }
        CrmUser user = new CrmUser();
        user.setEmail(email);
        CrmUser temp = userDao.templateOne(user);
        if ((id == 2) && temp != null) {
            throw new UserException(UserException.EMAIL_HAS_EXIST);
        } else if ((id == 3 || id == 4) && temp == null) {
            throw new UserException(UserException.EMAIL_NO_EXIST);
        } else if (id > 4 || id <= 1) {
            throw new UserException(UserException.TYPE_NO_EXIST);
        }
        MessageEmail messageEmail = (MessageEmail) resourceService.get(MessageEmail.class, Long.valueOf(id));
        if (messageEmail == null) {
            throw new UserException(UserException.TEMPLATE_NO_EXIST);
        }
        return messageEmail;
    }
    public void sendEmail(String email, int id, MessageEmail messageEmail) throws Exception {
        Map<String,Object>map=new HashMap<String,Object>();
        map.put("email",email);
        map.put("id",id);
        String json = objectMapper.writeValueAsString(messageEmail);
        map.put("messageEmail",json);
        //RsMq.send("email", "java-platform-rsmq", "controller", map);
    }
    public void trueSendEmail(String email, int id, MessageEmail messageEmail) throws Exception {
        // 类型 2注册 3找回4完善资料
        String text = messageEmail.getContent();
        String validate = RandomUtil.randomString("0123456789", 4);
        Map<String, String> map = new HashMap<String, String>();
        map.put("validate", validate);
        text = StringUtil.messageTemplate(text, map);
        try {
            sendEmail.send(email, messageEmail.getName(), text, null, true);
            //保存邮件发送记录
            MessageEmailPush messageEmailPush=new MessageEmailPush();
            messageEmailPush.setContent(text);
            messageEmailPush.setEmail(email);
            messageEmailPush.setTheme(messageEmail.getTheme());
            messageEmailPush.setType(messageEmail.getType());
            messageEmailPush.setName(messageEmail.getName());
            //RsMq.send("sys_log", "java-platform-rsmq", "controller", sysLog);
            resourceService.add(messageEmailPush);
            email=getRedisKey(id,email);
            redisClient.set(CacheGroupConst.CODE_REDIS, email, validate, 5 * 60);
        } catch (Exception e) {
            throw new UserException(UserException.EMAIL_SEND_ERROR);
        }

    }

    /**
     * 注销账号
     *
     * @param request
     * @param response
     * @throws Exception
     */
    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean bo = delUser(request, response);
        if (!bo) {
            throw new UserException(UserException.LOGOUT_FAILED);
        }
    }

    public void passwordModifyCheck(String passwordOld, String passwordNew, String passwordNew2) throws Exception {
        if (CheckUtil.isNull(passwordOld)) {
            throw new UserException(UserException.PASSWORDOLD_INPUT_NULL);
        }
        if (CheckUtil.isNull(passwordNew) || CheckUtil.isNull(passwordNew2)) {
            throw new UserException(UserException.PASSWORDNEW_INPUT_NULL);
        }
        if (!passwordNew.equals(passwordNew2)) {
            throw new UserException(UserException.PASSWORDNEW_NO_EQUALS);
        }
        // if (passwordNew.equals(passwordOld)) {
        // throw new
        // UserException(UserException.PASSWORDNEW_NO_EQUAL_PASSWORDOLD);
        // }
    }

    public void perfectInfoCheck(HttpServletRequest request, CrmUser user) throws Exception {
        CrmUser loginUser = null;
        try {
            loginUser = loginUser(request);
        } catch (Exception e) {
            throw new UserException(UserException.USER_NO_LOGIN);
        }
        if (loginUser.getId() != user.getId()) {
            throw new UserException(UserException.ILLEGAL_USER);
        }
        editCheck(user);
    }

    public void perfectInfo(CrmUser user, CrmUserExtra userExtra) throws Exception {
        edit(user);
        if (CheckUtil.isNull(userExtra.getId())) {
            resourceService.add(userExtra);
        } else {
            resourceService.update(userExtra);
        }
    }

    /**
     * 更新手机号验证
     *
     * @param id
     * @param mobile
     * @param validate
     * @return
     * @throws Exception
     */
    public CrmUser perfectMobileCheck(Long id, String mobile, String validate) throws Exception {
        CrmUser user = checkUser(id, mobile, 1);
        Object redis_validate = redisClient.get(CacheGroupConst.CODE_REDIS,
                CacheGroupConst.PERFECT_CHECKCODE + user.getMobile());
        redis_validate = (redis_validate == null ? "" : redis_validate.toString());
        if (CheckUtil.isNull(redis_validate)) {
            throw new UserException(UserException.PERFECT_MOBILE_VALIDATE_NULL);
        }
        if (!redis_validate.equals(validate)) {
            throw new UserException(UserException.PERFECT_MOBILE_VALIDATE_ERROR);
        }
        return user;
    }

    public CrmUser perfectEmailCheck(Long id, String email, String validate) throws Exception {
        CrmUser user = checkUser(id, email, 2);
        Object redis_validate = redisClient.get(CacheGroupConst.CODE_REDIS,
                CacheGroupConst.PERFECT_CHECKCODE + user.getEmail());
        redis_validate = (redis_validate == null ? "" : redis_validate.toString());
        if (CheckUtil.isNull(redis_validate)) {
            throw new UserException(UserException.PERFECT_EMAIL_VALIDATE_NULL);
        }
        if (!redis_validate.equals(validate)) {
            throw new UserException(UserException.PERFECT_EMAIL_VALIDATE_ERROR);
        }
        return user;
    }

    public List<CrmPower> getPowerByMenuId(HttpServletRequest request, Long menuId) throws Exception {
        CrmUser user = loginUser(request);
        return getPowerByMenuId(user, menuId);
    }

    public List<CrmPower> getPowerByMenuId(CrmUser user, Long menuId) throws Exception {
        List<CrmPower> powerList = user.getPowerList();
        List<CrmPower> resultList = new ArrayList<CrmPower>();
        for (CrmPower power : powerList) {
            Long id = power.getRouterId();
            if (id.equals(menuId)) {
                resultList.add(power);
            }
        }
        return resultList;
    }

    public CrmUser updateUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CrmUser user = loginUser(request);
        if(user.getId()==1){
            //admin 用户 获取所有权限,菜单
            QueryParam queryParam=new QueryParam();
            queryParam.setNeedPage(false);
            queryParam.setNeedTotal(false);
            user.setMenuList((List<UiRouter>) resourceService.list(UiRouter.class,queryParam).getList());
            user.setPowerList((List<CrmPower>) resourceService.list(CrmPower.class,queryParam).getList());
        }else{
            List<UiRouter> menuList = userDao.getMenuList(user.getId());
            List<CrmPower> powerList = userDao.getPowerList(user.getId());
            user.setMenuList(menuList);
            user.setPowerList(powerList);
        }
        List<CrmRole> roleList = userDao.getRoleList(user.getId());
        user.setRoleList(roleList);
        updateLoginUser(user, request);
        return user;
    }

    private String getRedisKey(int type,String key){
        switch (type) {
            case 1:
                key = CacheGroupConst.LOGIN_CHECKCODE + key;
                break;
            case 2:
                key = CacheGroupConst.REGISTER_CHECKCODE + key;
                break;
            case 3:
                key = CacheGroupConst.FIND_CHECKCODE + key;
                break;
            case 4:
                key = CacheGroupConst.PERFECT_CHECKCODE + key;
                break;
            default:
                break;
        }
        return key;
    }
}
