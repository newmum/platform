package net.evecom.core.rbac.model.entity;

import net.evecom.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName: UserRole
 * @Description: 用户角色对象
 * @author： zhengc
 * @date： 2018年10月30日
 */
@Table(name = "crm_user_role")
public class UserRole extends DataEntity<UserRole> implements Serializable {

	@Column(name = "crm_user_id")
	@ApiModelProperty(value = "用户id")
	@NotNull(message = "用户不能为空")
	private Long crmUserId;
	@Column(name = "crm_role_id")
	@ApiModelProperty(value = "角色id")
	@NotNull(message = "角色不能为空")
	private Long crmRoleId;

	public Long getCrmUserId() {
		return crmUserId;
	}

	public void setCrmUserId(Long crmUserId) {
		this.crmUserId = crmUserId;
	}

	public Long getCrmRoleId() {
		return crmRoleId;
	}

	public void setCrmRoleId(Long crmRoleId) {
		this.crmRoleId = crmRoleId;
	}

	@Override
	public String toString() {
		return "UserRole [" + "crmUserId=" + crmUserId + "," + "crmRoleId=" + crmRoleId + "]" + "Address ["
				+ getClass().getName() + "@" + Integer.toHexString(hashCode()) + "]";
	}

}