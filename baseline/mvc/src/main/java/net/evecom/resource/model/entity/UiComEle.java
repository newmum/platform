package net.evecom.resource.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.evecom.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;

@Table(name = "ui_component_element_view")
public class UiComEle extends DataEntity<UiComEle> implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "component_id")
	@ApiModelProperty(value = "ui组件id")
	private Long componentId;
	// @Column(name = "name")
	// @ApiModelProperty(value = "")
	// private String name;
	// @Column(name = "title")
	// @ApiModelProperty(value = "")
	// private String title;
	@Column(name = "prop_name")
	@ApiModelProperty(value = "属性名称")
	private String propName;
	@Column(name = "prop_value")
	@ApiModelProperty(value = "属性值")
	private String propValue;

	@JsonIgnore
	public Long getComponentId() {
		return componentId;
	}

	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}
	//
	// @JsonIgnore
	// public String getName() {
	// return name;
	// }
	//
	// public void setName(String name) {
	// this.name = name;
	// }
	//
	// @JsonIgnore
	// public String getTitle() {
	// return title;
	// }
	//
	// public void setTitle(String title) {
	// this.title = title;
	// }

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getPropValue() {
		return propValue;
	}

	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}

}
