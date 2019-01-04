package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import lombok.ToString;
import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @ClassName: UserGroup
 * @Description: 用户角色关联实体类
 * @author： zhengc
 * @date： 2018年11月20日
 */
@Table(name = "RM_USER_GROUP_RELA_T")
@ToString
public class UserGroup extends DataEntity<UserGroup> implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	private Long crmUserId;

	@ApiModelProperty(value = "分组id")
	private Long groupId;

	@Column(name = "USER_ID")
	public Long getCrmUserId() {
		return crmUserId;
	}

	public void setCrmUserId(Long crmUserId) {
		this.crmUserId = crmUserId;
	}

	@Column(name = "GROUP_ID")
	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

}
