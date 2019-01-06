package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;
import org.beetl.sql.core.annotatoin.Tail;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName: UserRole
 * @Description: 用户角色对象
 * @author： zhengc
 * @date： 2018年10月30日
 */
@Table(name = "RM_ROLE_POWER_RELA_T")
public class UserRole extends DataEntity<UserRole> implements Serializable {


	@ApiModelProperty(value = "用户id")
	@NotNull(message = "用户不能为空")
	private String userId;

	@ApiModelProperty(value = "角色id")
	@NotNull(message = "角色不能为空")
	private String roleId;

	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "ROLE_ID")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
