/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.rd.ie.baseline.core.rbac.model.service;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.Department;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.User;
import org.beetl.sql.core.SQLManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 * @author Klaus Zhuang
 * @created 2018/12/5 14:50
 */
@Transactional
@Service("departmentService")
public class DepartmentService {

    /**
     * 描述
     */
    @Resource
    private SQLManager sqlManager;

    /**
     * 描述 获取所有部门
     * @author Klaus Zhuang
     * @created 2019/1/9 17:08
     * @return
     * @param
     */
    public List<Department> getAll() {
        List<Department> list = this.sqlManager.lambdaQuery(Department.class).orderBy("dept_sort asc").select();
        return list;
    }
    /**
     * 描述 获取部门树
     * @author Klaus Zhuang
     * @created 2018/12/5 15:14
     * @return
     * @param
     */
    public List<Department> getTree() {
        List<Department> list = this.sqlManager.lambdaQuery(Department.class).orderBy("dept_sort asc").select();
        for(Department department:list){
            Department parentDepart = this.sqlManager.single(Department.class,department.getParentId());
            if(parentDepart!=null){
                department.setParentName(parentDepart.getDeptName());
            }else{
                department.setParentName("");
            }
        }
        // 最后的结果
        List<Department> departList = new ArrayList<Department>();
        // 先找到所有的一级菜单
        for (int i = 0; i < list.size(); i++) {
            // 一级菜单没有parentId
            if (list.get(i).getParentId().equals("0")) {
                departList.add(list.get(i));
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (Department depart : departList) {
            depart.setChildren(getChild(depart.getTid(), list));
        }
        return departList;
    }


    /**
     * 描述 获取子部门
     * @author Klaus Zhuang
     * @created 2018/12/5 16:40
     * @return
     * @param
     */
    private List<Department> getChild(String id, List<Department> list) {
        // 子部门
        List<Department> childList = new ArrayList<Department>();
        for (Department depart : list) {
            // 遍历所有节点，将父部门id与传过来的id比较
            if (!depart.getParentId().equals("0")) {
                if (depart.getParentId().equals(id)) {
                    childList.add(depart);
                }
            }
        }
        // 把子部门的子部门再循环一遍
        for (Department depart : childList) {// 没有url子菜单还有子菜单
            depart.setChildren(getChild(depart.getTid(), list));
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    /**
     * 描述 获取部门信息
     * @author Klaus Zhuang
     * @created 2018/12/11 17:30
     * @return
     * @param
     */
    public Department get(String id) {
        Department department = sqlManager.single(Department.class,id);
        if(department.getParentId()!=null&&!department.getParentId().equals("0")){
            department.setParentName(sqlManager.single(Department.class,department.getParentId()).getDeptName());
        }
        return department;
    }

    /**
     * 描述 新增部门
     * @author Klaus Zhuang
     * @created 2018/12/28 9:46
     * @return
     * @param
     */
    public int save(Department department) {
        return sqlManager.insert(Department.class,department);
    }

    /**
     * 描述 修改部门
     * @author Klaus Zhuang
     * @created 2018/12/28 9:46
     * @return
     * @param
     */
    public int update(Department department) {
        return sqlManager.updateById(department);
    }

    /**
     * 描述 是否还有子机构
     * @author Klaus Zhuang
     * @created 2018/12/28 14:28
     * @return
     * @param
     */
    public boolean hasChild(String id) {
        List<Department> list =
                sqlManager.lambdaQuery(Department.class).andEq(Department::getParentId,id).select("tid");
        return list.size() > 0;
    }

    /**
     * 描述 是否含有用户
     * @author Klaus Zhuang
     * @created 2018/12/28 14:28
     * @return
     * @param
     */
    public boolean hasUser(String id) {
        List<User> list =
                sqlManager.lambdaQuery(User.class).andEq(User::getDeptId,id).select("tid");
        return list.size() > 0;
    }

    /**
     * 描述 删除部门
     * @author Klaus Zhuang
     * @created 2018/12/28 14:53
     * @return
     * @param
     */
    public void delete(String id) {
        sqlManager.deleteById(Department.class,id);
    }
}
