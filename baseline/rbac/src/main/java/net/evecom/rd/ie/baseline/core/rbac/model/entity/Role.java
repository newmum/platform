package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import net.evecom.rd.ie.baseline.utils.report.exl.ExcelField;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;
import org.beetl.sql.core.annotatoin.Tail;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @ClassName: Role
 * @Description: 角色对象
 * @author： zhengc
 * @date： 2018年10月30日
 */
@Table(name = "RM_ROLE_T")
public class Role extends DataEntity<Role> implements Serializable {

	@ApiModelProperty(value = "编号")
	protected Long tid;
	@ApiModelProperty(value = "角色名称")
	@ExcelField(align = 2, sort = 1, title = "角色名称")
	private String roleName;
	@ApiModelProperty(value = "标题")
	@ExcelField(align = 2, sort = 1, title = "标题")
	private String title;
	@ApiModelProperty(value = "机构编号")
	@ExcelField(align = 2, sort = 2, title = "机构编号")
	private Long deptId;
	@ApiModelProperty(value = "角色类型")
	private int roleType;
	@ApiModelProperty(value = "创建人编号", hidden = true)
	private Long createUser;
	@ApiModelProperty(value = "备注")
	private String roleDesc;

	@SeqID(name="RM_ROLE_S")
	@Column(name = "TID")
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	@Column(name = "ROLE_NAME")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "DEPT_ID")
	public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

	@Column(name = "ROLE_TYPE")
    public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	@Column(name = "CREATE_USER")
	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUserId) {
		this.createUser = createUser;
	}

	@Column(name = "ROLE_DESC")
	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	@Override
	public String toString() {
		return "Role(" +
				"roleName='" + roleName + '\'' +
				", title='" + title + '\'' +
				", deptId=" + deptId +
				", roleType=" + roleType +
				", createUserId=" + createUser +
				", roleDesc='" + roleDesc + '\'' +
				')';
	}
}
