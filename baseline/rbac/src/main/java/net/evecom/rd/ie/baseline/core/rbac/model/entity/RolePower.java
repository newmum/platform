package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description: 用户角色关联实体类
 * @author： zhengc
 * @date： 2018年11月20日
 */
@Table(name = "RM_ROLE_PRIV_RELA_T")
public class RolePower extends DataEntity<RolePower> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "PRIV_ID")
	@ApiModelProperty(value = "权限id")
	@NotNull(message = "权限不能为空")
	private Long crmPowerId;
	@Column(name = "ROLE_ID")
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
		return "RolePower [" + "crmPowerId=" + crmPowerId + "," + "crmRoleId=" + crmRoleId + "]" + "Address ["
				+ getClass().getName() + "@" + Integer.toHexString(hashCode()) + "]";
	}

}
