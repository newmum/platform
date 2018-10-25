package net.evecom.core.rbac.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.evecom.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author xiejun
 * @since 1.0
 */
@Table(name = "crm_user")
public class CrmUser extends DataEntity<CrmUser> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "account")
	@ApiModelProperty(value = "账号")
	private String account;
	@Column(name = "crm_office_id")
	@ApiModelProperty(value = "所属机构", hidden = true)
	private Long crmOfficeId;
	@ApiModelProperty(value = "所属机构", hidden = true)
	private CrmOffice crmOffice;
	@Column(name = "email")
	@ApiModelProperty(value = "邮箱", hidden = true)
	private String email;
	@Column(name = "type")
	@ApiModelProperty(value = "用户类型", hidden = true)
	private Integer type;
	@Column(name = "mobile")
	@ApiModelProperty(value = "手机号", hidden = true)
	private String mobile;
	@Column(name = "password")
	@ApiModelProperty(value = "密码", hidden = true)
	private String password;
	@Column(name = "create_user_id")
	@ApiModelProperty(value = "创建人id", hidden = true)
	private Long createUserId;
	@ApiModelProperty(value = "基本信息", hidden = true)
	private CrmUserExtra crmUserExtra;
	@ApiModelProperty(value = "角色集合", hidden = true)
	private List<CrmRole> roleList;
	@ApiModelProperty(value = "菜单集合", hidden = true)
	private List<UiRouter> menuList;
	@ApiModelProperty(value = "菜单权限集合", hidden = true)
	private List<CrmPower> powerList;
	@ApiModelProperty(value = "登录ip", hidden = true)
	private String loginIp;
	@ApiModelProperty(value = "登录时间", hidden = true)
	private String loginDate;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	@JsonIgnore
	public Long getCrmOfficeId() {
		return crmOfficeId;
	}

	public void setCrmOfficeId(Long crmOfficeId) {
		this.crmOfficeId = crmOfficeId;
	}

	public CrmOffice getCrmOffice() {
		return crmOffice;
	}

	public void setCrmOffice(CrmOffice crmOffice) {
		this.crmOffice = crmOffice;
	}

	public CrmUserExtra getCrmUserExtra() {
		return crmUserExtra;
	}

	public void setCrmUserExtra(CrmUserExtra crmUserExtra) {
		this.crmUserExtra = crmUserExtra;
	}

	public List<CrmRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<CrmRole> roleList) {
		this.roleList = roleList;
	}

	public List<UiRouter> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<UiRouter> menuList) {
		this.menuList = menuList;
	}

	@JsonIgnore
	public List<CrmPower> getPowerList() {
		return powerList;
	}

	public void setPowerList(List<CrmPower> powerList) {
		this.powerList = powerList;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}

	@Override
	public String toString() {
		return "CrmUser [" + "account=" + account + "," + "crmOfficeId=" + crmOfficeId + "," + "email=" + email + ","
				+ "type=" + type + "," + "mobile=" + mobile + "," + "password=" + password + "," + "createUserId="
				+ createUserId + "]" + "Address [" + getClass().getName() + "@" + Integer.toHexString(hashCode()) + "]";
	}

}
