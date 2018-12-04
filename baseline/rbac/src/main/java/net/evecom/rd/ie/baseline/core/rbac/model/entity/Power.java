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

	@Column(name = "PRIV_NAME")
	@ApiModelProperty(value = "名称")
	@NotNull(message = "名称不能为空")
	private String privName;
	@Column(name = "METHOD")
	@ApiModelProperty(value = "请求方式")
	@NotNull(message = "请求方式不能为空")
	private String method;
	@Column(name = "URL")
	@ApiModelProperty(value = "请求路径")
	@NotNull(message = "请求路径不能为空")
	private String url;
	@Column(name = "MENU_ID")
	@ApiModelProperty(value = "菜单id")
	private Long menuId;
	@Column(name = "CREATE_USER")
	@ApiModelProperty(value = "创建人id", hidden = true)
	private Long createUser;
	@Column(name = "PRIV_DESC")
	@ApiModelProperty(value = "权限描述")
	private String privDesc;

	public String getPrivName() {
		return privName;
	}

	public void setPrivName(String privName) {
		this.privName = privName;
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

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

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
