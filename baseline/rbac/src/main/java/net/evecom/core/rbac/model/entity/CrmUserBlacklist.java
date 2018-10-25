package net.evecom.core.rbac.model.entity;

import net.evecom.core.db.model.entity.DataEntity;
import net.evecom.utils.verify.RegexUtil;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 *
 * @author xiejun
 * @since 1.0
 */
@Table(name = "crm_user_blacklist")
public class CrmUserBlacklist extends DataEntity<CrmUserBlacklist> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "crm_user_id")
	@ApiModelProperty(value = "用户id")
	@NotNull(message = "用户不能为空")
	private Long crmUserId;
	@Column(name = "ip")
	@ApiModelProperty(value = "ip地址")
	@Pattern(regexp = RegexUtil.IP, message = "ip地址输入有误")
	private String ip;

	public Long getCrmUserId() {
		return crmUserId;
	}

	public void setCrmUserId(Long crmUserId) {
		this.crmUserId = crmUserId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "CrmUserBlacklist [" + "crmUserId=" + crmUserId + "," + "ip=" + ip + "]" + "Address ["
				+ getClass().getName() + "@" + Integer.toHexString(hashCode()) + "]";
	}

}
