package net.evecom.core.rbac.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import net.evecom.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: User
 * @Description: 用户对象
 * @author： zhengc
 * @date： 2018年10月30日
 */
@Table(name = "SYSTEM_USER")
@ToString
public class User extends DataEntity<User> {

	@Column(name = "ACCOUNT")
    @ApiModelProperty(value = "账号")
    private String account;
    @Column(name = "PASSWORD")
    @ApiModelProperty(value = "密码", hidden = true)
    private String password;
    @Column(name = "EMAIL")
    @ApiModelProperty(value = "邮箱", hidden = true)
    private String email;
	@Column(name = "DEPT_ID")
	@ApiModelProperty(value = "机构编号", hidden = true)
	private Long deptId;
	@ApiModelProperty(value = "用户机构对象", hidden = true)
	private Department department;
	@Column(name = "USER_TYPE")
	@ApiModelProperty(value = "用户类型", hidden = true)
	private Integer userType;
	@Column(name = "MOBILE")
	@ApiModelProperty(value = "手机号", hidden = true)
	private String mobile;
	@Column(name = "CREATE_USER_ID")
	@ApiModelProperty(value = "创建人编号", hidden = true)
	private Long createUserId;
	@ApiModelProperty(value = "基本信息", hidden = true)
	private CrmUserExtra crmUserExtra;
	@ApiModelProperty(value = "角色集合", hidden = true)
	private List<Role> roleList;
	@ApiModelProperty(value = "菜单集合", hidden = true)
	private List<UiRouter> menuList;
	@ApiModelProperty(value = "菜单权限集合", hidden = true)
	private List<Power> powerList;

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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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
    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public CrmUserExtra getCrmUserExtra() {
		return crmUserExtra;
	}

	public void setCrmUserExtra(CrmUserExtra crmUserExtra) {
		this.crmUserExtra = crmUserExtra;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<UiRouter> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<UiRouter> menuList) {
		this.menuList = menuList;
	}

	@JsonIgnore
	public List<Power> getPowerList() {
		return powerList;
	}

	public void setPowerList(List<Power> powerList) {
		this.powerList = powerList;
	}

}
