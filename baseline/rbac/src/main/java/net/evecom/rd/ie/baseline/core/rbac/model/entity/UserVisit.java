package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @ClassName: UserVisit
 * @Description: 用户访问实体类
 * @author： zhengc
 * @date： 2018年11月20日
 */
@Table(name = "log_user_visit_t")
public class UserVisit extends DataEntity<UserVisit> implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "菜单id")
	private Long uiRouterId;

	@ApiModelProperty(value = "用户id")
	private Long crmUserId;

	@Column(name = "MENU_ID")
	public Long getUiRouterId() {
		return uiRouterId;
	}

	public void setUiRouterId(Long uiRouterId) {
		this.uiRouterId = uiRouterId;
	}

	@Column(name = "USER_ID")
	public Long getCrmUserId() {
		return crmUserId;
	}

	public void setCrmUserId(Long crmUserId) {
		this.crmUserId = crmUserId;
	}

	@Override
	public String toString() {
		return "UserVisit [" + "uiRouterId=" + uiRouterId + "," + "crmUserId=" + crmUserId + "]" + "Address ["
				+ getClass().getName() + "@" + Integer.toHexString(hashCode()) + "]";
	}

}
