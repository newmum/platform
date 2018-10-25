package net.evecom.core.rbac.model.entity;

import net.evecom.core.db.model.entity.DataEntity;
import net.evecom.utils.report.exl.ExcelField;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;

/**
 *
 * @author xiejun
 * @since 1.0
 */
@Table(name = "crm_role")
public class CrmRole extends DataEntity<CrmRole> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "name")
	@ApiModelProperty(value = "名称")
	@ExcelField(align = 2, sort = 1, title = "名称")
	private String name;
	@Column(name = "crm_office_id")
	@ApiModelProperty(value = "所属机构")
	@ExcelField(align = 2, sort = 2, title = "机构id")
	private Long crmOfficeId;
	@Column(name = "role_type")
	@ApiModelProperty(value = "角色类型")
	private int roleType;
	@Column(name = "create_user_id")
	@ApiModelProperty(value = "创建人id", hidden = true)
	private Long createUserId;
	@Column(name = "remarks")
	@ApiModelProperty(value = "备注")
	private String remarks;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCrmOfficeId() {
		return crmOfficeId;
	}

	public void setCrmOfficeId(Long crmOfficeId) {
		this.crmOfficeId = crmOfficeId;
	}

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
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
		return "CrmRole [" + "name=" + name + "," + "crmOfficeId=" + crmOfficeId + "," + "roleType=" + roleType + ","
				+ "createUserId=" + createUserId + "," + "remarks=" + remarks + "]" + "Address [" + getClass().getName()
				+ "@" + Integer.toHexString(hashCode()) + "]";
	}

}
