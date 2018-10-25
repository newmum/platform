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
@Table(name = "crm_user_visit")
public class CrmUserVisit extends DataEntity<CrmUserVisit> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "ui_router_id")
	@ApiModelProperty(value = "菜单id")
	private Long uiRouterId;
	@Column(name = "crm_user_id")
	@ApiModelProperty(value = "用户id")
	private Long crmUserId;

	public Long getUiRouterId() {
		return uiRouterId;
	}

	public void setUiRouterId(Long uiRouterId) {
		this.uiRouterId = uiRouterId;
	}

	public Long getCrmUserId() {
		return crmUserId;
	}

	public void setCrmUserId(Long crmUserId) {
		this.crmUserId = crmUserId;
	}

	@Override
	public String toString() {
		return "CrmUserVisit [" + "uiRouterId=" + uiRouterId + "," + "crmUserId=" + crmUserId + "]" + "Address ["
				+ getClass().getName() + "@" + Integer.toHexString(hashCode()) + "]";
	}

}
