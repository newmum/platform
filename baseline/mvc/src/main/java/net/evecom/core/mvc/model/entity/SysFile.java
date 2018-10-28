package net.evecom.core.mvc.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.evecom.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author xiejun
 * @since 1.0
 */
@Table(name = "sys_file")
public class SysFile extends DataEntity<SysFile> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "name")
	@NotNull(message = "名称不能为空")
	@ApiModelProperty(value = "名称")
	private String name;
	@Column(name = "true_name")
	@ApiModelProperty(value = "真实名称")
	private String trueName;
	@Column(name = "sort")
	@ApiModelProperty(value = "排序")
	private Long sort ;
	@Column(name = "type")
	@NotNull(message = "类型不能为空")
	@ApiModelProperty(value = "类型 1文件2文件夹")
	private Integer type;
	@Column(name = "parent_id")
	@NotNull(message = "父类不能为空")
	@ApiModelProperty(value = "父类id")
	private Long parentId;
	@Column(name = "path")
	@ApiModelProperty(value = "地址")
	private String path;
	@Column(name = "user_id")
	@ApiModelProperty(value = "所属用户")
	private Long userId;
	@Column(name = "create_user_id")
	@ApiModelProperty(value = "创建人id")
	private Long createUserId;
	@Column(name = "remarks")
	@ApiModelProperty(value = "备注")
	private String remarks;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	@JsonIgnore
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	@JsonIgnore
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@JsonIgnore
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

	@Override
	public String toString() {
		return "SysFile [" + "name=" + name + "trueName=" + trueName + "sort=" + sort + "type=" + type + "parentId="
				+ parentId + "path=" + path + "userId=" + userId + "createUserId=" + createUserId + "remarks=" + remarks
				+ "]" + "Address [" + getClass().getName() + "@" + Integer.toHexString(hashCode()) + "]";
	}

}
