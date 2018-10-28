package net.evecom.core.mvc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.evecom.core.db.annotatoin.Token;
import net.evecom.core.db.database.query.QueryParam;
import net.evecom.core.db.model.service.ResourceService;
import net.evecom.core.mvc.model.entity.UiComponent;
import net.evecom.core.rbac.base.BaseController;
import net.evecom.core.rbac.model.entity.CrmPower;
import net.evecom.core.rbac.model.entity.CrmUser;
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
@Api(value = "UI生成模块", tags = "UI生成模块")
public class ViewController extends BaseController {
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private UserService userService;

	@ApiOperation(value = "进入主页面", notes = "进入主页面")
	@RequestMapping(value = "/showMain", method = RequestMethod.GET)
	public Result<?> showMain() throws Exception {
		CrmUser user = userService.loginUser(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		return Result.success(SuccessConst.OPERATE_SUCCESS, map);
	}

	@Token(save = true)
	@ApiOperation(value = "进入页面", notes = "进入页面")
	@RequestMapping(value = "/{resourceName}", method = RequestMethod.GET)
	public Result<?> view(@PathVariable(value = "resourceName") String name) throws Exception {
		CrmUser user = userService.loginUser(request);
		String stp_url = request.getRequestURI();
		String stp_method = request.getMethod();
		// String main_url = global.getKey("server.servlet.context-path");
		String main_url = request.getContextPath();
		main_url = (main_url == null ? "" : main_url);
		stp_url = stp_url.substring(main_url.length());
		List<UiComponent> compentlist =new ArrayList<UiComponent>();
		CrmPower power = hasPower(user.getPowerList(), stp_url, stp_method);
		Map<String, Object> map = new HashMap<String, Object>();
		if(power!=null){
			QueryParam<UiRouter>queryParam=new QueryParam<>();
			queryParam.append(UiRouter::getCrmPowerId,power.getId());
            UiRouter uiRouter= (UiRouter) resourceService.get(UiRouter.class,queryParam);
//			List<CrmPower> list = userService.getPowerByMenuId(request, power.getRouterId());
			//compentlist = uiComponentService.getComponents(user.getPowerList(), uiRouter.getId());
		}
		//map.put("components", compentlist);
		return Result.success(SuccessConst.OPERATE_SUCCESS, map);
	}

	private static CrmPower hasPower(List<CrmPower> powerList, String url, String method) {
		CrmPower crmPower = null;
		if (powerList == null || powerList.size() == 0) {
			return crmPower;
		}
		for (CrmPower power : powerList) {
			String regex = "^" + power.getUrl().replaceAll("\\*", "\\.\\*") + "$";
			if (Pattern.matches(regex, url) && power.getMethod().toUpperCase().equals(method)) {
				crmPower = power;
				break;
			}
		}
		return crmPower;
	}

}
