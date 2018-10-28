package net.evecom.core.mvc.model.entity;

import net.evecom.core.db.model.entity.DataEntity;
import net.evecom.utils.verify.CheckUtil;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xiejun
 * @since 1.0
 */
@Table(name = "ui_component")
public class UiComponent extends DataEntity<UiComponent> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "parent_id")
	@ApiModelProperty(value = "父类id")
	private Long parentId;
	@Column(name = "name")
	@ApiModelProperty(value = "名称")
	private String name;
	@Column(name = "title")
	@ApiModelProperty(value = "标题")
	private String title;
	@Column(name = "element_id")
	@ApiModelProperty(value = "元素id")
	private Long elementId;
	@Column(name = "sort")
	@ApiModelProperty(value = "排序,正序 越大排越后 ")
	private Long sort;
	@Column(name = "is_use")
	@ApiModelProperty(value = "是否使用(1使用2不使用)")
	private int isUse;
	@Column(name = "crm_power_id")
	@ApiModelProperty(value = "权限id")
	private Long crmPowerId;
	@Column(name = "ui_router_id")
	@ApiModelProperty(value = "菜单id")
	private Long uiRouterId;
	@ApiModelProperty(value = "子类组件集合", hidden = true)
	private List<UiComponent> childList;
	@ApiModelProperty(value = "组件属性集合", hidden = true)
	private List<UiComEle> elelist;

	public void addEle(UiComEle comEle) {
		if (CheckUtil.isNull(elelist)) {
			elelist = new ArrayList<UiComEle>();
		}
		elelist.add(comEle);
	}

	public List<UiComEle> getElelist() {
		if (CheckUtil.isNull(elelist)) {
			elelist = new ArrayList<UiComEle>();
		}
		return elelist;
	}

	public void setElelist(List<UiComEle> elelist) {
		this.elelist = elelist;
	}

	public List<UiComponent> getChildList() {
		if (CheckUtil.isNull(childList)) {
			childList = new ArrayList<UiComponent>();
		}
		return childList;
	}

	public void setChildList(List<UiComponent> childList) {
		this.childList = childList;
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

	public Long getElementId() {
		return elementId;
	}

	public void setElementId(Long elementId) {
		this.elementId = elementId;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public int getIsUse() {
		return isUse;
	}

	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}

	public Long getCrmPowerId() {
		return crmPowerId;
	}

	public void setCrmPowerId(Long crmPowerId) {
		this.crmPowerId = crmPowerId;
	}

	public Long getUiRouterId() {
		return uiRouterId;
	}

	public void setUiRouterId(Long uiRouterId) {
		this.uiRouterId = uiRouterId;
	}

	@Override
	public String toString() {
		return "UiComponent [" + "parentId=" + parentId + ",name=" + name + ",title=" + title + ",elementId="
				+ elementId + ",sort=" + sort + ",isUse=" + isUse + ",crmPowerId=" + crmPowerId + ",uiRouterId="
				+ uiRouterId + "]" + "Address [" + getClass().getName() + "@" + Integer.toHexString(hashCode()) + "]";
	}

}
