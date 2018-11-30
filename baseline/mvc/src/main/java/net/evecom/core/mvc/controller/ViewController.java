package net.evecom.core.mvc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.evecom.core.db.annotatoin.Token;
import net.evecom.core.db.database.query.QueryParam;
import net.evecom.core.db.model.service.ResourceService;
import net.evecom.core.mvc.model.entity.UiComponent;
import net.evecom.core.rbac.base.BaseController;
import net.evecom.core.rbac.model.entity.Power;
import net.evecom.core.rbac.model.entity.User;
import net.evecom.core.rbac.model.entity.UiRouter;
import net.evecom.core.rbac.model.service.UserService;
import net.evecom.tools.constant.consts.SuccessConst;
import net.evecom.tools.service.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/views")
@Api(value = "UI模块", tags = "UI模块")
public class ViewController extends BaseController {
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private UserService userService;

	@ApiOperation(value = "访问首页", notes = "访问首页")
	@RequestMapping(value = "/showMain", method = RequestMethod.GET)
	public Result<?> showMain() throws Exception {
		User user = userService.loginUser(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		return Result.success(SuccessConst.OPERATE_SUCCESS, map);
	}

	@Token(save = true)
	@ApiOperation(value = "访问路由", notes = "访问路由")
	@RequestMapping(value = "/{resourceName}", method = RequestMethod.GET)
	public Result<?> view(@PathVariable(value = "resourceName") String name) throws Exception {
		User user = userService.loginUser(request);
		String stp_url = request.getRequestURI();
		String stp_method = request.getMethod();
		// String main_url = global.getKey("server.servlet.context-path");
		String main_url = request.getContextPath();
		main_url = (main_url == null ? "" : main_url);
		stp_url = stp_url.substring(main_url.length());
		List<UiComponent> compentlist =new ArrayList<UiComponent>();
		Power power = hasPower(user.getPowerList(), stp_url, stp_method);
		Map<String, Object> map = new HashMap<String, Object>();
		if(power!=null){
			QueryParam<UiRouter>queryParam=new QueryParam<>();
			queryParam.append(UiRouter::getCrmPowerId,power.getTid());
            UiRouter uiRouter= (UiRouter) resourceService.get(UiRouter.class,queryParam);
//			List<Power> list = userService.getPowerByMenuId(request, power.getRouterId());
			//compentlist = uiComponentService.getComponents(user.getPowerList(), uiRouter.getId());
		}
		//map.put("components", compentlist);
		return Result.success(SuccessConst.OPERATE_SUCCESS, map);
	}

	private static Power hasPower(List<Power> powerList, String url, String method) {
		Power crmPower = null;
		if (powerList == null || powerList.size() == 0) {
			return crmPower;
		}
		for (Power power : powerList) {
			String regex = "^" + power.getUrl().replaceAll("\\*", "\\.\\*") + "$";
			if (Pattern.matches(regex, url) && power.getMethod().toUpperCase().equals(method)) {
				crmPower = power;
				break;
			}
		}
		return crmPower;
	}

}
