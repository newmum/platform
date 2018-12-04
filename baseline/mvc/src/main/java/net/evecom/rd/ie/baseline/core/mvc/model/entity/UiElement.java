package net.evecom.rd.ie.baseline.core.mvc.model.entity;

import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description: UI元素实体类
 * @author： zhengc
 * @date： 2018年11月20日
 */
@Table(name = "ui_element")
public class UiElement extends DataEntity<UiElement> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "parent_id")
	@ApiModelProperty(value = "")
	private Long parentId;
	@Column(name = "name")
	@NotNull(message = "属性名称不能为空")
	@ApiModelProperty(value = "属性名称")
	private String name;
	@Column(name = "title")
	@ApiModelProperty(value = "")
	private String title;
	@Column(name = "sort")
	@ApiModelProperty(value = "排序,正序 越大排越后 ")
	private int sort;
	@Column(name = "is_use")
	@ApiModelProperty(value = "是否使用(1使用2不使用)")
	private int isUse;
	@Column(name = "value")
	@ApiModelProperty(value = "默认值")
	private String value;
	@Column(name = "remarks")
	@ApiModelProperty(value = "备注")
	private String remarks;

	public UiElement() {
		this.sort = 0;
		this.isUse = 1;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getIsUse() {
		return isUse;
	}

	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "UiElement [" + "parentId=" + parentId + ",name=" + name + ",title=" + title + ",sort=" + sort
				+ ",isUse=" + isUse + "]" + "Address [" + getClass().getName() + "@" + Integer.toHexString(hashCode())
				+ "]";
	}

}
