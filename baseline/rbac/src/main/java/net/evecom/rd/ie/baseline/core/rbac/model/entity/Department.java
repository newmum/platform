/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.rd.ie.baseline.core.rbac.model.entity;



import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

/**
 * 描述
 * @author Klaus Zhuang
 * @created 2018/12/5 16:31
 */
@Table(
        name = "rm_department_t"
)
public class Department extends DataEntity<Department> implements Serializable {

    @ApiModelProperty("上级机构编号")
    @NotNull(message = "上级机构不能为空")
    private String parentId;
    @ApiModelProperty("组织机构名称")
    @NotNull(message = "名称不能为空")
    private String deptName;
    @ApiModelProperty("组织机构编码")
    @NotNull(message = "组织机构编码不能为空")
    private String deptCode;
    @ApiModelProperty("排序")
    private Long deptSort;
    @ApiModelProperty("区域编码")
    private String areaCode;
    @ApiModelProperty("机构类型")
    private Long deptType;
    @ApiModelProperty("机构等级")
    private Long deptLevel;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("邮政编码")
    private String zip;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("传真")
    private String fax;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty(value = "创建人id",hidden = true)
    private Long createUser;
    @Column(
            name = "DEPT_DESC"
    )
    @ApiModelProperty("备注")
    private String deptDesc;

    @Transient
    private String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Transient
    private List<Department> children;

    public List<Department> getChildren() {
        return children;
    }

    public void setChildren(List<Department> children) {
        this.children = children;
    }

    public Department() {
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getDeptSort() {
        return deptSort;
    }

    public void setDeptSort(Long deptSort) {
        this.deptSort = deptSort;
    }

    public Long getDeptLevel() {
        return deptLevel;
    }

    public void setDeptLevel(Long deptLevel) {
        this.deptLevel = deptLevel;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Long getDeptType() {
        return deptType;
    }

    public void setDeptType(Long deptType) {
        this.deptType = deptType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }
}
