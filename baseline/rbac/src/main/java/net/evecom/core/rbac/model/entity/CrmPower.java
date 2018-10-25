package net.evecom.core.rbac.model.entity;

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
@Table(name = "crm_power")
public class CrmPower extends DataEntity<CrmPower> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "name")
	@ApiModelProperty(value = "名称")
	@NotNull(message = "名称不能为空")
	private String name;
	@Column(name = "method")
	@ApiModelProperty(value = "请求方式")
	@NotNull(message = "请求方式不能为空")
	private String method;
	@Column(name = "url")
	@ApiModelProperty(value = "请求路径")
	@NotNull(message = "请求路径不能为空")
	private String url;
	@Column(name = "router_id")
	@ApiModelProperty(value = "菜单id")
	private Long routerId;
	@Column(name = "create_user_id")
	@ApiModelProperty(value = "创建人id", hidden = true)
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

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getRouterId() {
		return routerId;
	}

	public void setRouterId(Long routerId) {
		this.routerId = routerId;
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

	@Override
	public String toString() {
		return "CrmPower [" + "name=" + name + "," + "method=" + method + "," + "url=" + url + "," + "routerId="
				+ routerId + "," + "createUserId=" + createUserId + "," + "remarks=" + remarks + "]" + "Address ["
				+ getClass().getName() + "@" + Integer.toHexString(hashCode()) + "]";
	}

}
