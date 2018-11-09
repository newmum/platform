package net.evecom.core.mvc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.evecom.core.rbac.base.BaseController;
import net.evecom.core.rbac.model.entity.User;
import net.evecom.core.rbac.model.entity.MessageEmail;
import net.evecom.core.rbac.model.entity.MessageSms;
import net.evecom.core.rbac.model.service.UserService;
import net.evecom.tools.constant.consts.SuccessConst;
import net.evecom.tools.service.Result;
import net.evecom.utils.request.VerifyCodeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/account")
@Api(value = "身份认证模块", tags = "身份认证模块")
public class AccountController extends BaseController {
    @Resource
    private UserService userService;

    @ApiOperation(value = "普通登录", notes = "普通登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号/手机号/邮箱", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "password", value = "密码(前端MD5加密后的字符串)(670b14728ad9902aecba32e22fa4f6bd)", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "validateImageCode", value = "图形验证码(登录错误3次以上,为必填项)", dataType = "string", paramType = "query")})
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<?> login(String account, String password, String validateImageCode) throws Exception {
        User user = userService.loginCheck(session, account, password, validateImageCode);
        user = userService.login(user, response, request);
        return Result.success(SuccessConst.LOGIN_SUCCESS, user);
    }

    @ApiOperation(value = "手机号登录", notes = "手机号登录")
    @RequestMapping(value = "/mobileLogin", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "validateCode", value = "短信验证码", dataType = "string", paramType = "query", required = true)})
    public Result<?> mobilelogin(String mobile, String validateCode) throws Exception {
        User user = userService.mobileloginCheck(mobile, validateCode);
        user = userService.login(user, response, request);
        return Result.success(SuccessConst.LOGIN_SUCCESS, user);
    }

    /**
     * @throws Exception
     */
    @ApiOperation(value = "发送图形验证码", notes = "发送图形验证码")
    @RequestMapping(value = "/sendLoginCode", method = RequestMethod.GET)
    public void sendLoginCode() throws Exception {
        VerifyCodeUtils.image(request, response);
    }

    /**
     * @param mobile
     * @param type   类型 1登录 2注册 3手机号找回 4完善资料
     * @return
     */
    @ApiOperation(value = "发送短信验证码", notes = "发送短信验证码")
    @RequestMapping(value = "/sendSMS", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(name = "mobile", value = "手机号", dataType = "string", required = true),
            @ApiImplicitParam(name = "type", value = " 1登录 2注册 3找回4更换手机", dataType = "int", required = true),
            @ApiImplicitParam(name = "validateImageCode", value = "图形验证码", dataType = "string", paramType = "query", required = true)})
    public Result<?> sendSMS(String mobile, Integer type, String validateImageCode) throws Exception {
        MessageSms messageSms = userService.sendSMSCheck(request, mobile, type, validateImageCode);
        userService.sendSMS(mobile, type, messageSms);
        return Result.success(SuccessConst.OPERATE_SUCCESS);
    }

    @ApiOperation(value = "发送邮箱验证码", notes = "发送邮箱验证码")
    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(name = "email", value = "邮箱", dataType = "string", required = true),
            @ApiImplicitParam(name = "type", value = "1登录(预留,暂不使用)2注册 3找回4更换邮箱", dataType = "int", required = true),
            @ApiImplicitParam(name = "validateImageCode", value = "图形验证码", dataType = "string", paramType = "query", required = true)})
    public Result<?> sendEmail(String email, Integer type,String validateImageCode) throws Exception {
        System.out.println("email:" + email + ";");
        MessageEmail messageEmail = userService.sendEmailCheck(request, email, type,validateImageCode);
        userService.sendEmail(email, type, messageEmail);
        return Result.success(SuccessConst.OPERATE_SUCCESS);
    }

    @ApiOperation(value = "注册", notes = "注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(name = "account", value = "账号", dataType = "string", required = true),
            @ApiImplicitParam(name = "mobile", value = "手机号(手机号或者邮箱必填其一,都填以手机号为主)", dataType = "string"),
            @ApiImplicitParam(name = "email", value = "邮箱(手机号或者邮箱必填其一,都填以手机号为主)", dataType = "string"),
            @ApiImplicitParam(name = "validateCode", value = "验证码", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "string", required = true)})
    public Result<?> register(String account, String validateCode, String mobile, String email, String password)
            throws Exception {
        User user = userService.registerCheck(account, validateCode, mobile, email, password);
        // 类型 1手机号2邮箱
        user.setAccount(account);
        user.setPassword(password);
        userService.register(user);
        return Result.success(SuccessConst.REGISTER_SUCCESS);
    }

    @ApiOperation(value = "验证用户信息", notes = "账号或邮箱或手机号是否已存在")
    @ResponseBody
    @RequestMapping(value = "/checkUser", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(name = "account", value = "账号", dataType = "string", required = true),
            @ApiImplicitParam(name = "type", value = "类型 1手机号2邮箱3账号", dataType = "int", required = true)})
    public Result<?> check(String account, Integer type) throws Exception {
        Long id = null;
        try {
            id = userService.loginUser(request).getId();
        } catch (Exception e) {
        }
        userService.checkUser(id, account, type);
        return Result.success(SuccessConst.OPERATE_SUCCESS);
    }

    @ApiOperation(value = "密码找回", notes = "密码找回")
    @RequestMapping(value = "/passwordRecovery", method = RequestMethod.PUT)
    @ApiImplicitParams({@ApiImplicitParam(name = "mobile", value = "手机(手机号或者邮箱必填其一,都填以手机号为主)", dataType = "string"),
            @ApiImplicitParam(name = "email", value = "邮箱(手机号或者邮箱必填其一,都填以手机号为主)", dataType = "string"),
            @ApiImplicitParam(name = "validateCode", value = "验证码", dataType = "string", required = true),
            @ApiImplicitParam(name = "password", value = "新密码", dataType = "string", required = true)})
    public Result<?> passwordRecovery(String mobile, String email, String validateCode, String password)
            throws Exception {
        User user = userService.passwordRecoveryCheck(mobile, email, validateCode, password);
        userService.passwordRecovery(user);
        return Result.success(SuccessConst.OPERATE_SUCCESS);
    }

    @ApiOperation(value = "注销", notes = "注销")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Result<?> logout() throws Exception {
        userService.logout(request, response);
        return Result.success(SuccessConst.OPERATE_SUCCESS);
    }
}
