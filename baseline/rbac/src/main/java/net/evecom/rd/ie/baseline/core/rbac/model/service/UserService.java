package net.evecom.rd.ie.baseline.core.rbac.model.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.evecom.rd.ie.baseline.core.rbac.base.BaseService;
import net.evecom.rd.ie.baseline.core.rbac.model.dao.UserDao;
import net.evecom.rd.ie.baseline.core.db.exception.ResourceException;
import net.evecom.rd.ie.baseline.core.rbac.exception.UserException;
import net.evecom.rd.ie.baseline.core.db.model.service.ResourceService;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.*;
import net.evecom.rd.ie.baseline.tools.constant.consts.CacheGroupConst;
import net.evecom.rd.ie.baseline.core.db.database.query.QueryParam;
import net.evecom.rd.ie.baseline.tools.exception.CommonException;
import net.evecom.rd.ie.baseline.tools.message.email.SendEmail;
import net.evecom.rd.ie.baseline.tools.message.sms.SendSMS;
import net.evecom.rd.ie.baseline.utils.database.redis.RedisClient;
import net.evecom.rd.ie.baseline.utils.request.IPUtils;
import net.evecom.rd.ie.baseline.utils.string.RandomUtil;
import net.evecom.rd.ie.baseline.utils.string.StringUtil;
import net.evecom.rd.ie.baseline.utils.verify.CheckUtil;
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
    private UserDao userDao;
    @Resource(name = "sendEmail")
    private SendEmail sendEmail;
    @Resource
    private ResourceService resourceService;
    @Resource
    private ObjectMapper objectMapper;

    /**
     *
     * @param user
     * @return
     * @throws Exception
     */
    public User add(User user) throws Exception {
        resourceService.add(user);
        UserExtra userExtra = new UserExtra();
        userExtra.setCrmUserId(user.getTid());
        userExtra.preInsert();
        resourceService.add(userExtra);
        return user;
    }

    public void delete(User user) throws Exception {
        user.setIsDel(User.YES);
        user.preUpdate();
        int i = userDao.updateTemplateById(user);
        if (i <= 0) {
            throw new CommonException(CommonException.OPERATE_FAILED);
        }
    }

    public void editCheck(User user) throws Exception {
        if (CheckUtil.isNull(user.getTid())) {
            throw new ResourceException(ResourceException.ID_NULL);
        }
        if (CheckUtil.isNull(user.getMobile()) && CheckUtil.isNull(user.getEmail())
                && CheckUtil.isNull(user.getAccount())) {
            throw new UserException(UserException.MOBILE_EMAIL_ACCOUNT_NULL);
        }
        if (CheckUtil.isNotNull(user.getMobile())) {
            checkUser(user.getTid(), user.getMobile(), 1);
        }
        if (CheckUtil.isNotNull(user.getEmail())) {
            checkUser(user.getTid(), user.getEmail(), 2);
        }
        if (CheckUtil.isNotNull(user.getAccount())) {
            checkUser(user.getTid(), user.getAccount(), 3);
        }
    }

    public User edit(User user) throws Exception {
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
    public User mobileloginCheck(String mobile, String validate) throws Exception {
        if (!CheckUtil.isNotNull(validate)) {
            throw new UserException(UserException.VALIDATE_INPUT_NULL);
        }
        User user = new User();
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
        User user = new User();
        user.setIsDel(User.YES);
        user.preUpdate();
        for (Long id : ids) {
            user.setTid(id);
            int i = userDao.updateTemplateById(user);
            if (i <= 0) {
                result.add(id);
            }
        }
        return result;
    }

    /**
     * 登录验证
     * @param account  手机号/邮箱/账号
     * @param password 密码
     * @return
     */
    public User loginCheck(HttpSession session, String account, String password, String validate) throws Exception {
        Object check_code = session.getAttribute(CacheGroupConst.CHECK_CODE_NAME);
        if (check_code != null && !check_code.toString().equals(validate)) {
            throw new UserException(UserException.VALIDATE_ERROR);
        }
        if (CheckUtil.isNull(password)) {
            throw new UserException(UserException.PASSWORD_INPUT_NULL);
        }
        User user = new User();
        if (CheckUtil.isEmail(account)) {
            user.setEmail(account);
        } else if (CheckUtil.isMobile(account)) {
            user.setMobile(account);
        } else if (CheckUtil.stringLengthCheck(account, 3, 20)) {
            user.setAccount(account);
        } else {
            throw new UserException(UserException.ACCOUNT_FORMAT_3TO20);
        }
        user = userDao.templateOne(user);
        if (user == null) {
            throw new UserException(UserException.USER_NO_EXIST);
        }
        if (!user.getPassword().equals(password)) {
            throw new UserException(UserException.PASSWORD_ERROR);
        }
        return user;
    }

    public User login(User user, HttpServletResponse response, HttpServletRequest request) throws Exception {
        setUserExtra(user);
        try {
            logout(request, response);
        } catch (Exception e) {

        }
        if(user.getTid()==1){
            //admin 用户 获取所有权限,菜单
            QueryParam queryParam=new QueryParam();
            queryParam.setNeedPage(false);
            queryParam.setNeedTotal(false);
            user.setMenuList((List<UiRouter>) resourceService.list(UiRouter.class,queryParam).getList());
            user.setPowerList((List<Power>) resourceService.list(Power.class,queryParam).getList());
        }else{
            List<UiRouter> menuList = userDao.getMenuList(user.getTid());
            List<Power> powerList = userDao.getPowerList(user.getTid());
            user.setMenuList(menuList);
            user.setPowerList(powerList);
        }
        List<Role> roleList = userDao.getRoleList(user.getTid());
        user.setRoleList(roleList);
        // 保存登录日志
        String login_ip = IPUtils.getIpAddr(request);
        UserLoginLog userLog = new UserLoginLog();
        userLog.setIp(login_ip);
        userLog.setCrmUserId(user.getTid());
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

    public void setUserExtra(User user) {
        QueryParam<UserExtra> param = new QueryParam<>(UserExtra.class);
        param.append(UserExtra::getCrmUserId, user.getTid());
        UserExtra userExtra;
        try {
            userExtra = (UserExtra) resourceService.get(UserExtra.class, param);
            Department dept = (Department) resourceService.get(Department.class, user.getDeptId());
            user.setDepartment(dept);
        } catch (Exception e) {
            userExtra = new UserExtra();
            userExtra.setCrmUserId(user.getTid());
        }
        user.setCrmUserExtra(userExtra);
    }

    public User passwordRecoveryCheck(String mobile, String email, String validate, String password)
            throws Exception {
        // 类型 1手机号找回2邮箱找回
        if (CheckUtil.isNull(validate)) {
            throw new UserException(UserException.VALIDATE_INPUT_NULL);
        }
        if (CheckUtil.isNull(password)) {
            throw new UserException(UserException.PASSWORD_INPUT_NULL);
        }
        User user = new User();
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
        User temp = userDao.templateOne(user);
        if (temp == null) {
            if (hint.equals("mobile")) {
                throw new UserException(UserException.MOBILE_NO_EXIST);
            } else {
                throw new UserException(UserException.EMAIL_NO_EXIST);
            }
        } else {
            user.setTid(temp.getTid());
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

    public void passwordRecovery(User user) throws Exception {
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

    public User registerCheck(String account, String validate, String mobile, String email, String password)
            throws Exception {
        if (CheckUtil.isNull(validate)) {
            throw new UserException(UserException.VALIDATE_INPUT_NULL);
        }
        User user = null;
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
    public User register(User user) throws Exception {
        user = add(user);
        if (user == null) {
            throw new UserException(UserException.REGISTER_ERROR);
        }
        // 新注册用户默认添加基础角色(id为2)
        UserRole crmUserRole = new UserRole();
        crmUserRole.setCrmUserId(user.getTid());
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
    public User checkUser(Long id, String account, Integer type) throws Exception {
        //
        User user = new User();
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
        User temp = userDao.templateOne(user);
        if (temp != null && temp.getTid() != id) {
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
        User user = new User();
        user.setMobile(mobile);
        User temp = userDao.templateOne(user);
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
        User user = new User();
        user.setEmail(email);
        User temp = userDao.templateOne(user);
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
            //messageEmailPush.setTheme(messageEmail.getTheme());
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

    public void perfectInfoCheck(HttpServletRequest request, User user) throws Exception {
        User loginUser = null;
        try {
            loginUser = loginUser(request);
        } catch (Exception e) {
            throw new UserException(UserException.USER_NO_LOGIN);
        }
        if (loginUser.getTid() != user.getTid()) {
            throw new UserException(UserException.ILLEGAL_USER);
        }
        editCheck(user);
    }

    public void perfectInfo(User user, UserExtra userExtra) throws Exception {
        edit(user);
        if (CheckUtil.isNull(userExtra.getTid())) {
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
    public User perfectMobileCheck(Long id, String mobile, String validate) throws Exception {
        User user = checkUser(id, mobile, 1);
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

    public User perfectEmailCheck(Long id, String email, String validate) throws Exception {
        User user = checkUser(id, email, 2);
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

    public List<Power> getPowerByMenuId(HttpServletRequest request, Long menuId) throws Exception {
        User user = loginUser(request);
        return getPowerByMenuId(user, menuId);
    }

    public List<Power> getPowerByMenuId(User user, Long menuId) throws Exception {
        List<Power> powerList = user.getPowerList();
        List<Power> resultList = new ArrayList<Power>();
        for (Power power : powerList) {
            Long id = power.getMenuId();
            if (id.equals(menuId)) {
                resultList.add(power);
            }
        }
        return resultList;
    }

    public User updateUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = loginUser(request);
        if(user.getTid()==1){
            //admin 用户 获取所有权限,菜单
            QueryParam queryParam=new QueryParam();
            queryParam.setNeedPage(false);
            queryParam.setNeedTotal(false);
            user.setMenuList((List<UiRouter>) resourceService.list(UiRouter.class,queryParam).getList());
            user.setPowerList((List<Power>) resourceService.list(Power.class,queryParam).getList());
        }else{
            List<UiRouter> menuList = userDao.getMenuList(user.getTid());
            List<Power> powerList = userDao.getPowerList(user.getTid());
            user.setMenuList(menuList);
            user.setPowerList(powerList);
        }
        List<Role> roleList = userDao.getRoleList(user.getTid());
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
