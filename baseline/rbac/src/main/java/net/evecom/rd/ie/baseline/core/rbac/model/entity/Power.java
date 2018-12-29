package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName: Power
 * @Description: 权限对象
 * @author： zhengc
 * @date： 2018年10月31日
 */
@Table(name = "RM_PRIV_T")
public class Power extends DataEntity<Power> implements Serializable {

	@ApiModelProperty(value = "名称")
	@NotNull(message = "名称不能为空")
	private String privName;

	@ApiModelProperty(value = "请求方式")
	@NotNull(message = "请求方式不能为空")
	private String method;

	@ApiModelProperty(value = "请求路径")
	@NotNull(message = "请求路径不能为空")
	private String url;

	@ApiModelProperty(value = "菜单id")
	private Long menuId;

	@ApiModelProperty(value = "创建人id", hidden = true)
	private Long createUser;

	@ApiModelProperty(value = "权限描述")
	private String privDesc;

	@Column(name = "PRIV_NAME")
	public String getPrivName() {
		return privName;
	}

	public void setPrivName(String privName) {
		this.privName = privName;
	}

	@Column(name = "METHOD")
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Column(name = "URL")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "MENU_ID")
	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	@Column(name = "CREATE_USER")
	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	@Column(name = "PRIV_DESC")
	public String getPrivDesc() {
		return privDesc;
	}

	public void setPrivDesc(String privDesc) {
		this.privDesc = privDesc;
	}

	@Override
	public String toString() {
		return "Power [" + "name=" + privName + "," + "method=" + method + "," + "url=" + url + "," + "routerId="
				+ menuId + "," + "createUserId=" + createUser + "," + "remarks=" + privDesc + "]" + "Address ["
				+ getClass().getName() + "@" + Integer.toHexString(hashCode()) + "]";
	}

}
