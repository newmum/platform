package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import lombok.ToString;
import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName: UserLoginLog
 * @Description: 用户登录日志对象
 * @author： zhengc
 * @date： 2018年10月31日
 */
@Table(name = "LOG_USER_LOGIN_T")
@ToString
public class UserLoginLog extends DataEntity<UserLoginLog> implements Serializable {


	@ApiModelProperty(value = "登录ip")
	@NotNull(message = "登录ip不能为空")
	private String ip;

	@ApiModelProperty(value = "用户id")
	@NotNull(message = "用户id不能为空")
	private String userId;

	@ApiModelProperty(value = "备注")
	private String remarks;

	@Column(name = "IP")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "REMARK")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
