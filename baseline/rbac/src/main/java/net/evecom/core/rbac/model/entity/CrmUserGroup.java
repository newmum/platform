package net.evecom.core.rbac.model.entity;

import net.evecom.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;

/**
 *
 * @author xiejun
 * @since 1.0
 */
@Table(name = "rm_user_group_rela_t")
public class CrmUserGroup extends DataEntity<CrmUserGroup> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "USER_ID")
	@ApiModelProperty(value = "用户id")
	private Long crmUserId;
	@Column(name = "GROUP_ID")
	@ApiModelProperty(value = "分组id")
	private Long groupId;

	public Long getCrmUserId() {
		return crmUserId;
	}

	public void setCrmUserId(Long crmUserId) {
		this.crmUserId = crmUserId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		return "CrmUserGroup [" + "crmUserId=" + crmUserId + "," + "groupId=" + groupId + "]" + "Address ["
				+ getClass().getName() + "@" + Integer.toHexString(hashCode()) + "]";
	}

}
