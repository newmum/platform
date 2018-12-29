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

	@ApiModelProperty(value = "上级机构编号")
	@NotNull(message = "上级机构不能为空")
	private Long parentId;

	@ApiModelProperty(value = "组织机构名称")
	@NotNull(message = "名称不能为空")
	private String title;

	@ApiModelProperty(value = "排序")
	private Long sort;

	@ApiModelProperty(value = "区域编码")
	private String code;

	@ApiModelProperty(value = "机构类型")
	private Long type;

	@ApiModelProperty(value = "机构等级")
	private Long grade;

	@ApiModelProperty(value = "地址")
	private String address;

	@ApiModelProperty(value = "邮政编码")
	private String zipCode;

	@ApiModelProperty(value = "电话")
	private String phone;

	@ApiModelProperty(value = "传真")
	private String fax;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "创建人id", hidden = true)
	private Long createUserId;

	@ApiModelProperty(value = "备注")
	private String remarks;

	@Column(name = "PARENT_ID")
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "SORT")
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	@Column(name = "AREA_CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "DEPT_TYPE")
	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	@Column(name = "LEVEL")
	public Long getGrade() {
		return grade;
	}

	public void setGrade(Long grade) {
		this.grade = grade;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "ZIP")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "FAX")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "CREATE_USER")
	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "DEPT_DESC")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
