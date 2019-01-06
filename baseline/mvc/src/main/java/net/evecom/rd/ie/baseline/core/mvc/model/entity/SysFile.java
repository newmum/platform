package net.evecom.rd.ie.baseline.core.mvc.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description: 文件实体类
 * @author： zhengc
 * @date： 2018年11月20日
 */
@Table(name = "SYS_FILE")
@ToString
public class SysFile extends DataEntity<SysFile> implements Serializable {

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
	private String parentId;
	@Column(name = "path")
	@ApiModelProperty(value = "地址")
	private String path;
	@Column(name = "user_id")
	@ApiModelProperty(value = "所属用户")
	private String userId;
	@Column(name = "create_user_id")
	@ApiModelProperty(value = "创建人id")
	private String createUserId;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
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
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

    @JsonIgnore
    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
