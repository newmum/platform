package net.evecom.core.rbac.model.entity;

import net.evecom.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author xiejun
 * @since 1.0
 */
@Table(name = "crm_office")
public class CrmOffice extends DataEntity<CrmOffice> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "parent_id")
	@ApiModelProperty(value = "父类id")
	@NotNull(message = "父类不能为空")
	private Long parentId;
	@Column(name = "name")
	@ApiModelProperty(value = "名称")
	@NotNull(message = "名称不能为空")
	private String name;
	@Column(name = "sort")
	@ApiModelProperty(value = "排序")
	private Long sort;
	@Column(name = "code")
	@ApiModelProperty(value = "区域编码")
	private String code;
	@Column(name = "type")
	@ApiModelProperty(value = "机构类型")
	private Long type;
	@Column(name = "grade")
	@ApiModelProperty(value = "机构等级")
	private Long grade;
	@Column(name = "address")
	@ApiModelProperty(value = "地址")
	private String address;
	@Column(name = "zip_code")
	@ApiModelProperty(value = "邮政编码")
	private String zipCode;
	@Column(name = "master")
	@ApiModelProperty(value = "负责人")
	private Long master;
	@Column(name = "phone")
	@ApiModelProperty(value = "电话")
	private String phone;
	@Column(name = "fax")
	@ApiModelProperty(value = "传真")
	private String fax;
	@Column(name = "email")
	@ApiModelProperty(value = "邮箱")
	private String email;
	@Column(name = "create_user_id")
	@ApiModelProperty(value = "创建人id", hidden = true)
	private Long createUserId;
	@Column(name = "remarks")
	@ApiModelProperty(value = "备注")
	private String remarks;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getMaster() {
		return master;
	}

	public void setMaster(Long master) {
		this.master = master;
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

	@Override
	public String toString() {
		return "CrmOffice [" + "parentId=" + parentId + "," + "name=" + name + "," + "sort=" + sort + "," + "code="
				+ code + "," + "type=" + type + "," + "grade=" + grade + "," + "address=" + address + "," + "zipCode="
				+ zipCode + "," + "master=" + master + "," + "phone=" + phone + "," + "fax=" + fax + "," + "email="
				+ email + "," + "createUserId=" + createUserId + "," + "remarks=" + remarks + "]" + "Address ["
				+ getClass().getName() + "@" + Integer.toHexString(hashCode()) + "]";
	}

}
