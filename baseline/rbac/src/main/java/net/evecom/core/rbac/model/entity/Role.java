package net.evecom.core.rbac.model.entity;

import net.evecom.core.db.model.entity.DataEntity;
import net.evecom.utils.report.exl.ExcelField;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

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

	@Column(name = "ROLE_NAME")
	@ApiModelProperty(value = "角色名称")
	@ExcelField(align = 2, sort = 1, title = "角色名称")
	private String roleName;
	@Column(name = "OFFICE_ID")
	@ApiModelProperty(value = "机构编号")
	@ExcelField(align = 2, sort = 2, title = "机构编号")
	private Long deptId;
	@Column(name = "ROLE_TYPE")
	@ApiModelProperty(value = "角色类型")
	private int roleType;
	@Column(name = "CREATE_USER")
	@ApiModelProperty(value = "创建人编号", hidden = true)
	private Long createUserId;
	@Column(name = "REMARKS")
	@ApiModelProperty(value = "备注")
	private String remarks;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
