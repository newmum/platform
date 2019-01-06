package net.evecom.rd.ie.baseline.core.mvc.model.entity;

import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @Description: UI组件属性类
 * @author： zhengc
 * @date： 2018年11月20日
 */
@Table(name = "UI_COMPONENT_PROP")
public class UiComponentProp extends DataEntity<UiComponentProp> implements Serializable {

	@Column(name = "component_id")
	@ApiModelProperty(value = "组件id")
	private String componentId;
	@Column(name = "name")
	@ApiModelProperty(value = "名称")
	private String name;
	@Column(name = "title")
	@ApiModelProperty(value = "标题")
	private String title;
	@Column(name = "prop_value")
	@ApiModelProperty(value = "属性值")
	private String propValue;
	@Column(name = "sort")
	@ApiModelProperty(value = "排序,正序 越大排越后 ")
	private Long sort;
	@Column(name = "is_use")
	@ApiModelProperty(value = "是否使用(1使用2不使用)")
	private int isUse;
	@Column(name = "element_id")
	@ApiModelProperty(value = "元素id")
	private String elementId;

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
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

	public String getPropValue() {
		return propValue;
	}

	public void setPropValue(String propValue) {
		this.propValue = propValue;
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

}
