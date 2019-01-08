/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.rd.ie.baseline.core.rbac.base;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.User;
import net.evecom.rd.ie.baseline.tools.service.RequestBean;
import net.evecom.rd.ie.baseline.tools.service.Result;
import net.evecom.rd.ie.baseline.utils.verify.CheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述 控制层基类
 * @author Klaus Zhuang
 * @created 2019/1/3 15:42
 * @return
 * @param
 */
@RestController
@RequestMapping("/baseController")
@Api(value = "通用模块", tags = "通用模块")
public class BaseController {
	/**
	 * 描述
	 */
	protected HttpServletRequest request;
	/**
	 * 描述
	 */
	protected HttpServletResponse response;
	/**
	 * 描述
	 */
	protected HttpSession session;

	/**
	 * 描述
	 */
	protected static Logger log = LoggerFactory.getLogger(BaseController.class);


	/**
	 * 描述 基类服务
	 */
	@Resource
	public BaseService baseService;



	/**
	 * 描述 表名，字段名，字段值
	 * @author Klaus Zhuang
	 * @created 2019/1/3 15:04
	 * @return
	 * @param 
	 */
	@ApiOperation(value = "检测某值是否已存在", notes = "检测某值是否已存在")
	@RequestMapping(value ="/checkExist", method = RequestMethod.POST)
	public Result checkExist(String tableName,String columnName,String columnValue){
		boolean isExist = baseService.checkExist(tableName,columnName,columnValue);
		if(isExist)
			return Result.success();
		return Result.failed();
	}
	
	

	/**
	 * 请求该类的每个Action前都会首先执行它可以放置准备数据的操作
	 *
	 * @param request
	 * @param response
	 */
	@ModelAttribute
	public void setBaseController(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
		RequestBean bean = RequestBean.getRequestBean();
		if (bean != null && CheckUtil.isNotNull(bean.getToken())) {
			this.response.addHeader("token", bean.getToken());
		}
	}

	/**
	 * 转换前端数据格式为JavaBean中格式
	 *
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 输出信息到页面
	 *
	 * @param msg：要输出的信息（可以是js脚本等）
	 */
	public void printOutMsg(String msg) {
		try {
			this.response.setCharacterEncoding("UTF-8");
			this.response.setContentType("text/html;charset=utf-8");
			PrintWriter out = null;
			out = this.response.getWriter();
			out.print(msg);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected Result<?> toView(String view) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("view", view);
		return Result.success("success", map);
	}

	/**
	 * 获取请求完整路径
	 *
	 * @param request
	 * @return
	 */
	public String getUrl(HttpServletRequest request) {
		String url = request.getRequestURI();
		String params = "";
		if (request.getQueryString() != null) {
			params = request.getQueryString();
		}
		if (!"".equals(params)) {
			url = url + "?" + params;
		}
		return url;
	}

	/**
	 * 获取登录用户信息
	 *
	 * @return
	 */
	public User getSessionUser() {
		// Object session =
		// request.getSession().getAttribute(Constants.KEY_SESSION_USER);
		return session == null ? null : (User) session;
	}

	/**
	 * 获取token
	 *
	 * @param type(1:登录;2:注册;3:账号找回)
	 * @return
	 */
	// protected RSAToken getRSAToken(int type){
	// // 生成一个token
	// String token = UUID.randomUUID().toString();
	// if(type == 1){
	// session.setAttribute(Constants.KEY_SESSION_TOKEN_LOGIN, token);
	// }else if(type == 2){
	// session.setAttribute(Constants.KEY_SESSION_TOKEN_REGISTER, token);
	// }else if(type == 3){
	// session.setAttribute(Constants.KEY_SESSION_TOKEN_FIND_PWD, token);
	// }
	//
	// // 生成公钥信息
	// RSAPublicKey publicKey = RSAUtils.getDefaultPublicKey();
	// String modulus = new
	// String(Hex.encodeHex(publicKey.getModulus().toByteArray()));
	// String exponent = new
	// String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray()));
	//
	// RSAToken rsaToken = new RSAToken();
	// rsaToken.setToken(token);
	// rsaToken.setModulus(modulus);
	// rsaToken.setExponent(exponent);
	//
	// return rsaToken;
	// }

	/**
	 * 设置用户session
	 *
	 * @param user
	 */
	public void setSessionUser(User user) {
		if (user != null) {
			// SessionUser sessionUser = new SessionUser();
			// userExtra
			// session.setAttribute(Constants.KEY_SESSION_USER, sessionUser);
		}
	}

	/**
	 * 捕获处理异常：CommonException
	 */
	// @ResponseBody
	// @ExceptionHandler(value = CommonException.class)
	// @ResponseStatus(HttpStatus.BAD_REQUEST)
	// protected BaseRspEntity restExceptionHandler(CommonException rse){
	// LOGGER.warn("exception",rse);
	// BaseRspEntity bre = new BaseRspEntity(rse.getRsc());
	// LOGGER.warn("response:{}", JSON.toJSONString(bre));
	// return bre;
	// }
}
