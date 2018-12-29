package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.util.List;

/**
 * @ClassName: User
 * @Description: 用户对象
 * @author： zhengc
 * @date： 2018年10月30日
 */
@Table(name = "RM_USER_T")
@ToString
public class User extends DataEntity<User> {

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "密码", hidden = true)
    private String password;

    @ApiModelProperty(value = "邮箱", hidden = true)
    private String email;

	@ApiModelProperty(value = "机构编号", hidden = true)
	private Long deptId;

	@ApiModelProperty(value = "用户类型", hidden = true)
	private Integer userType;

	@ApiModelProperty(value = "手机号", hidden = true)
	private String mobile;

	@ApiModelProperty(value = "创建人编号", hidden = true)
	private Long createUser;

	@ApiModelProperty(value = "是否锁定", hidden = true)
	private Integer isLock;

	@ApiModelProperty(value = "用户机构对象", hidden = true)
	private Department department;

	@ApiModelProperty(value = "基本信息", hidden = true)
	private UserExtra userExtra;

	@ApiModelProperty(value = "角色集合", hidden = true)
	private List<Role> role;

	@ApiModelProperty(value = "菜单集合", hidden = true)
	private List<Menu> menuList;

	@ApiModelProperty(value = "菜单权限集合", hidden = true)
	private List<Power> powerList;

	@Column(name = "ACCOUNT")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "USER_TYPE")
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

	@Column(name = "MOBILE")
    public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "PASSWORD")
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "CREATE_USER")
	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	@Column(name = "DEPT_ID")
    //@JsonIgnore
    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

	@Column(name = "IS_LOCK")
	public Integer getIsLock() {
		return isLock;
	}

	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}

	public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

	public UserExtra getUserExtra() {
		return userExtra;
	}

	public void setUserExtra(UserExtra userExtra) {
		this.userExtra = userExtra;
	}

	public List<Role> getRole() {
		return role;
	}

	public void setRole(List<Role> role) {
		this.role = role;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
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
