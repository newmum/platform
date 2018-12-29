package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import net.evecom.rd.ie.baseline.utils.verify.RegexUtil;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Description: 黑名单用户实体类
 * @author： zhengc
 * @date： 2018年11月20日
 */
@Table(name = "rm_blacklist_t")
public class UserBlacklist extends DataEntity<UserBlacklist> implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	@NotNull(message = "用户不能为空")
	private Long crmUserId;

	@ApiModelProperty(value = "ip地址")
	@Pattern(regexp = RegexUtil.IP, message = "ip地址输入有误")
	private String ip;

	@Column(name = "TID")
	public Long getCrmUserId() {
		return crmUserId;
	}

	public void setCrmUserId(Long crmUserId) {
		this.crmUserId = crmUserId;
	}

	@Column(name = "USER_IP")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "UserBlacklist [" + "crmUserId=" + crmUserId + "," + "ip=" + ip + "]" + "Address ["
				+ getClass().getName() + "@" + Integer.toHexString(hashCode()) + "]";
	}

}
