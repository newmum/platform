/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.rd.ie.baseline.core.rbac.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.evecom.rd.ie.baseline.core.rbac.base.BaseController;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.Department;
import net.evecom.rd.ie.baseline.core.rbac.model.service.DepartmentService;
import net.evecom.rd.ie.baseline.tools.service.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述
 * @author Klaus Zhuang
 * @created 2018/12/5 14:49
 */
@RestController
@RequestMapping("/department")
@Api(tags = "部门模块")
public class DepartmentController extends BaseController {
    /**
     * 描述
     */
    @Resource DepartmentService departmentService;

    /**
     * 描述 获取部门信息树形结构
     * @author Klaus Zhuang
     * @created 2018/12/5 15:14
     * @return
     * @param
     */
    @ApiOperation(value = "获取部门列表树形结构", notes = "获取部门列表树形结构")
    @RequestMapping(method = RequestMethod.GET)
    public Result<List<Department>> tree() {
        return Result.success("success",departmentService.getTree());
    }

    /**
     * 描述 获取所有部门信息
     * @author Klaus Zhuang
     * @created 2018/12/5 15:14
     * @return
     * @param
     */
    @ApiOperation(value = "获取所有部门信息", notes = "获取所有部门信息")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result<List<Department>> all() {
        return Result.success("success",departmentService.getAll());
    }


    /**
     * 描述 获取单个部门信息
     * @author Klaus Zhuang
     * @created 2018/12/5 15:14
     * @return
     * @param
     */
    @ApiOperation(value = "获取部门对象", notes = "获取部门对象")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Department> get(@PathVariable("id") String id) {
        return Result.success("success",departmentService.get(id));
    }

    /**
     * 描述 删除部门信息
     * @author Klaus Zhuang
     * @created 2018/12/5 15:14
     * @return
     * @param
     */
    @ApiOperation(value = "删除部门对象", notes = "删除部门对象")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result<Department> delete(@PathVariable("id") String id) {
        //部门下含有子部门 不可删除  部门下含有人员 不可删除
        boolean hasChild = departmentService.hasChild(id);
        if(hasChild){
            return Result.failed("该部门含有子部门！不可删除！");
        }
        boolean hasUser = departmentService.hasUser(id);
        if(hasUser){
            return Result.failed("该部门含有用户！不可删除！");
        }
        departmentService.delete(id);
        return Result.success("删除成功");
    }



    /**
     * 描述
     * @author Klaus Zhuang
     * @created 2018/12/5 15:14
     * @return
     * @param
     */
    @ApiOperation(value = "保存部门对象", notes = "保存部门对象")
    @RequestMapping(method = RequestMethod.POST)
    public Result<Department> save(Department department) {
        department.preInsert();
        int id = departmentService.save(department);
        return Result.success("success",id);
    }

    /**
     * 描述
     * @author Klaus Zhuang
     * @created 2018/12/5 15:14
     * @return
     * @param
     */
    @ApiOperation(value = "修改部门对象", notes = "修改部门对象")
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Department> update(Department department) {
        department.preUpdate();
        int id = departmentService.update(department);
        return Result.success("success",id);
    }


}
