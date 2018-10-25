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
@Table(name = "crm_role_power")
public class CrmRolePower extends DataEntity<CrmRolePower> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "crm_power_id")
	@ApiModelProperty(value = "权限id")
	@NotNull(message = "权限不能为空")
	private Long crmPowerId;
	@Column(name = "crm_role_id")
	@ApiModelProperty(value = "角色id")
	@NotNull(message = "角色不能为空")
	private Long crmRoleId;

	public Long getCrmPowerId() {
		return crmPowerId;
	}

	public void setCrmPowerId(Long crmPowerId) {
		this.crmPowerId = crmPowerId;
	}

	public Long getCrmRoleId() {
		return crmRoleId;
	}

	public void setCrmRoleId(Long crmRoleId) {
		this.crmRoleId = crmRoleId;
	}

	@Override
	public String toString() {
		return "CrmRolePower [" + "crmPowerId=" + crmPowerId + "," + "crmRoleId=" + crmRoleId + "]" + "Address ["
				+ getClass().getName() + "@" + Integer.toHexString(hashCode()) + "]";
	}

}
