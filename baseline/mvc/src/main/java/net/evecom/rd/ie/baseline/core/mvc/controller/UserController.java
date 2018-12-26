package net.evecom.rd.ie.baseline.core.mvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.evecom.rd.ie.baseline.core.db.annotatoin.Token;
import net.evecom.rd.ie.baseline.core.db.database.query.QueryParam;
import net.evecom.rd.ie.baseline.core.db.model.entity.ResService;
import net.evecom.rd.ie.baseline.core.db.model.entity.Resources;
import net.evecom.rd.ie.baseline.core.db.model.service.ResourceService;
import net.evecom.rd.ie.baseline.core.rbac.base.BaseController;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.User;
import net.evecom.rd.ie.baseline.core.rbac.model.service.UserService;
import net.evecom.rd.ie.baseline.tools.constant.consts.SuccessConst;
import net.evecom.rd.ie.baseline.tools.exception.CommonException;
import net.evecom.rd.ie.baseline.tools.service.Page;
import net.evecom.rd.ie.baseline.tools.service.Result;
import net.evecom.rd.ie.baseline.utils.verify.CheckUtil;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/users")
@Api(tags = "用户管理模块")
public class UserController extends BaseController {
    @Resource
    private UserService userService;
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private ResourceService resourceService;

    @ApiOperation(value = "查询指定用户信息", notes = "查询指定用户信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<?> query(@PathVariable(value = "id") String id) throws Exception {
        return Result.success(SuccessConst.OPERATE_SUCCESS, userService.queryUserById(id));
    }

    @ApiOperation(value = "查询所有用户信息", notes = "查询所有用户信息")
    @RequestMapping(value = "/queryAll", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "动态条件(json格式QueryParam对象)", dataType = "String", paramType = "query", required = true) })
    public Result<?> queryAll(String param) throws Exception {
        if (CheckUtil.isNull(param)) {
            throw new CommonException(CommonException.PARAM_NULL);
        }
        QueryParam<?> queryParam = null;
        try {
            queryParam = objectMapper.readValue(param, QueryParam.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(CommonException.JSON_FORMAT_ERROR);
        }
        Page<User> page=userService.queryUsers(queryParam);
        return Result.success(SuccessConst.OPERATE_SUCCESS, page);
    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    @Token(remove = true)
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "数据(json格式对象)", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "token", value = "token(测试环境可以填写1来跳过)", dataType = "string", paramType = "header", required = true) })
    public Result<?> edit(String data) throws Exception {
        if (CheckUtil.isNull(data)) {
            throw new CommonException(CommonException.DATA_NULL);
        }
        Resources resource = resourceService.get("user");
        Class<?> itemBean = Class.forName(resource.getClasspath());
        Object entity = null;
        try {
            entity = objectMapper.readValue(data, itemBean);
        } catch (Exception e) {
            throw new CommonException(CommonException.JSON_FORMAT_ERROR);
        }
        //更新rm_user_t表
        resourceService.update(entity);

        User user = (User)entity;

        //更新rm_user_extra_t表
        resourceService.update(user.getUserExtra());

        //更新rm_department_t表
        resourceService.update(user.getDepartment());

        //更新rm_role_t表
        for(int i = 0; i < user.getRole().size(); i++){
            resourceService.update(user.getRole().get(i));
        }
        return Result.success(SuccessConst.OPERATE_SUCCESS);
    }

    @ApiOperation(value = "新增指定资源的数据", notes = "对指定资源新增数据")
    @Token(remove = true)
    @RequestMapping(value = "/{resourceName}", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "数据(json格式对象)", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "token", value = "token(测试环境可以填写1来跳过)", dataType = "string", paramType = "header", required = true) })
    public Result<?> add(@PathVariable(value = "resourceName") String name, String data) throws Exception {
        if (CheckUtil.isNull(data)) {
            throw new CommonException(CommonException.DATA_NULL);
        }
        Resources resource = resourceService.get(name);
        Class<?> itemBean = Class.forName(resource.getClasspath());
        Object entity = null;
        try {
            entity = objectMapper.readValue(data, itemBean);
        } catch (Exception e) {
            throw new CommonException(CommonException.JSON_FORMAT_ERROR);
        }
        //在rm_user_t表中增加数据
        resourceService.add(entity);

        User user = (User)entity;

        //在rm_user_t表中增加数据
//        if (CheckUtil.isNotNull(user.getDepartment().toString())) {
//            resourceService.add(user.getDepartment());
//        }

        //在rm_user_extra_t表中增加数据
        //resourceService.add(user.getUserExtra());
        //在rm_role_t表中增加数据
        //resourceService.add(user.getRole());

        return Result.success(SuccessConst.OPERATE_SUCCESS);
    }

    @ApiOperation(value = "获取所有用户的数据", notes = "获取用户的数据")
    @Token(remove = true)
    @RequestMapping(value = "/{resourceNames}/userList", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "数据(json格式对象)", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "token", value = "token(测试环境可以填写1来跳过)", dataType = "string", paramType = "header") })
    public Result<?> getUsers(@PathVariable(value = "resourceNames") String name, String param) throws Exception {
        if (CheckUtil.isNull(param)) {
            throw new CommonException(CommonException.PARAM_NULL);
        }
        QueryParam<?> queryParam = null;
        try {
            queryParam = objectMapper.readValue(param, QueryParam.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(CommonException.JSON_FORMAT_ERROR);
        }
        Resources resources = resourceService.get(name);
        Class<?> itemBean = Class.forName(resources.getClasspath());
        ResService resService = resourceService.getRes(resources.getTid());
        return Result.success(SuccessConst.OPERATE_SUCCESS,resourceService.listBySql(itemBean,resService.getSql(),queryParam));
    }
}
