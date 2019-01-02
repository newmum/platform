package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import lombok.ToString;
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
@ToString
public class RolePriv extends DataEntity<RolePriv> implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "权限id")
	@NotNull(message = "权限不能为空")
	private Long privId;

	@ApiModelProperty(value = "角色id")
	@NotNull(message = "角色不能为空")
	private Long roleId;
	private Long crmPowerId;

	@ApiModelProperty(value = "角色id")
	@NotNull(message = "角色不能为空")
	private Long crmRoleId;

	@Column(name = "PRIV_ID")
	public Long getCrmPowerId() {
		return crmPowerId;
	}

	@Column(name = "PRIV_ID")
	public Long getPrivId() {
		return privId;
	}
	public void setPrivId(Long privId) {
		this.privId = privId;
	}

	@Column(name = "ROLE_ID")
	public Long getCrmRoleId() {
		return crmRoleId;
	}

	@Column(name = "ROLE_ID")
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
