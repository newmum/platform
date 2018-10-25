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
@Table(name = "crm_user_login_log")
public class CrmUserloginlog extends DataEntity<CrmUserloginlog> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "ip")
	@ApiModelProperty(value = "登录ip")
	@NotNull(message = "登录ip不能为空")
	private String ip;
	@Column(name = "crm_user_id")
	@ApiModelProperty(value = "用户id")
	@NotNull(message = "用户id不能为空")
	private Long crmUserId;
	@Column(name = "remarks")
	@ApiModelProperty(value = "备注")
	private String remarks;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getCrmUserId() {
		return crmUserId;
	}

	public void setCrmUserId(Long crmUserId) {
		this.crmUserId = crmUserId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "CrmUserLog [" + "ip=" + ip + "," + "crmUserId=" + crmUserId + "," + "remarks=" + remarks + "]"
				+ "Address [" + getClass().getName() + "@" + Integer.toHexString(hashCode()) + "]";
	}

}
