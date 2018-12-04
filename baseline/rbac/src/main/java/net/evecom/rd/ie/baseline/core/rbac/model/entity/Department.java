package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName: Department
 * @Description: 组织机构对象
 * @author： zhengc
 * @date： 2018年10月30日
 */
@Table(name = "rm_department_t")
public class Department extends DataEntity<Department> implements Serializable {

	@Column(name = "PARENT_ID")
	@ApiModelProperty(value = "上级机构编号")
	@NotNull(message = "上级机构不能为空")
	private Long parentId;
	@Column(name = "TITLE")
	@ApiModelProperty(value = "组织机构名称")
	@NotNull(message = "名称不能为空")
	private String deptName;
	@Column(name = "SORT")
	@ApiModelProperty(value = "排序")
	private Long sort;
	@Column(name = "AREA_CODE")
	@ApiModelProperty(value = "区域编码")
	private String code;
	@Column(name = "DEPT_TYPE")
	@ApiModelProperty(value = "机构类型")
	private Long type;
	@Column(name = "LEVEL")
	@ApiModelProperty(value = "机构等级")
	private Long grade;
	@Column(name = "ADDRESS")
	@ApiModelProperty(value = "地址")
	private String address;
	@Column(name = "ZIP")
	@ApiModelProperty(value = "邮政编码")
	private String zipCode;
	/*@Column(name = "master")
	@ApiModelProperty(value = "负责人")
	private Long master;*/
	@Column(name = "PHONE")
	@ApiModelProperty(value = "电话")
	private String phone;
	@Column(name = "FAX")
	@ApiModelProperty(value = "传真")
	private String fax;
	@Column(name = "EMAIL")
	@ApiModelProperty(value = "邮箱")
	private String email;
	@Column(name = "CREATE_USER")
	@ApiModelProperty(value = "创建人id", hidden = true)
	private Long createUserId;
	@Column(name = "DEPT_DESC")
	@ApiModelProperty(value = "备注")
	private String remarks;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getGrade() {
		return grade;
	}

	public void setGrade(Long grade) {
		this.grade = grade;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/*public Long getMaster() {
		return master;
	}

	public void setMaster(Long master) {
		this.master = master;
	}*/

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

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
