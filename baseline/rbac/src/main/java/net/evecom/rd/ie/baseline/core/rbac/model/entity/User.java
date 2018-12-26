package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;
import org.beetl.sql.core.annotatoin.Tail;

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
	@Column(name = "USER_TYPE")
	@ApiModelProperty(value = "用户类型", hidden = true)
	private Integer userType;
	@Column(name = "MOBILE")
	@ApiModelProperty(value = "手机号", hidden = true)
	private String mobile;
	@Column(name = "CREATE_USER")
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

	//@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

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
