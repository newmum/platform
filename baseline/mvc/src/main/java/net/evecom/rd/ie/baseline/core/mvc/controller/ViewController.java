package net.evecom.rd.ie.baseline.core.mvc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.evecom.rd.ie.baseline.core.db.annotatoin.Token;
import net.evecom.rd.ie.baseline.core.db.database.query.QueryParam;
import net.evecom.rd.ie.baseline.core.db.model.service.ResourceService;
import net.evecom.rd.ie.baseline.core.mvc.model.entity.UiComponent;
import net.evecom.rd.ie.baseline.core.mvc.model.service.ViewService;
import net.evecom.rd.ie.baseline.core.rbac.base.BaseController;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.Menu;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.Priv;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.User;
import net.evecom.rd.ie.baseline.tools.constant.consts.SuccessConst;
import net.evecom.rd.ie.baseline.tools.service.Result;
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
	private ViewService viewService;

	@ApiOperation(value = "访问首页", notes = "访问首页")
	@RequestMapping(value = "/showMain", method = RequestMethod.GET)
	public Result<?> showMain() throws Exception {
        return Result.success(SuccessConst.OPERATE_SUCCESS, viewService.showMain(request));
	}

	@Token(save = true)
	@ApiOperation(value = "访问路由", notes = "访问路由")
	@RequestMapping(value = "/{resourceName}", method = RequestMethod.GET)
	public Result<?> view(@PathVariable(value = "resourceName") String name) throws Exception {
		User user = viewService.loginUser(request);
		String stp_url = request.getRequestURI();
		String stp_method = request.getMethod();
		// String main_url = global.getKey("server.servlet.context-path");
		String main_url = request.getContextPath();
		main_url = (main_url == null ? "" : main_url);
		stp_url = stp_url.substring(main_url.length());
		List<UiComponent> compentlist =new ArrayList<UiComponent>();
		Priv power = hasPower(user.getPowerList(), stp_url, stp_method);
		Map<String, Object> map = new HashMap<String, Object>();
		if(power!=null){
			QueryParam<Menu>queryParam=new QueryParam<>();
			queryParam.append(Menu::getCrmPowerId,power.getTid());
            Menu uiRouter= (Menu) resourceService.get(Menu.class,queryParam);
//			List<Priv> list = userService.getPowerByMenuId(request, power.getRouterId());
			//compentlist = uiComponentService.getComponents(user.getPowerList(), uiRouter.getId());
		}
		//map.put("components", compentlist);
		return Result.success(SuccessConst.OPERATE_SUCCESS, map);
	}

	private static Priv hasPower(List<Priv> powerList, String url, String method) {
		Priv crmPower = null;
		if (powerList == null || powerList.size() == 0) {
			return crmPower;
		}
		for (Priv power : powerList) {
			String regex = "^" + power.getUrl().replaceAll("\\*", "\\.\\*") + "$";
			if (Pattern.matches(regex, url) && power.getMethod().toUpperCase().equals(method)) {
				crmPower = power;
				break;
			}
		}
		return crmPower;
	}

}
