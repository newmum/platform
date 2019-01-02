package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import lombok.ToString;
import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName: Priv
 * @Description: 权限对象
 * @author： zhengc
 * @date： 2018年10月31日
 */
@Table(name = "RM_PRIV_T")
@ToString
public class Priv extends DataEntity<Priv> implements Serializable {


	@ApiModelProperty(value = "名称")
	@NotNull(message = "名称不能为空")
	private String privName;

	@ApiModelProperty(value = "标题")
	@NotNull(message = "标题不能为空")
	private String title;

	@ApiModelProperty(value = "请求方式")
	@NotNull(message = "请求方式不能为空")
	private String method;

	@ApiModelProperty(value = "请求路径")
	@NotNull(message = "请求路径不能为空")
	private String url;

	@ApiModelProperty(value = "菜单id")
	private Long menuId;

	@ApiModelProperty(value = "创建人id", hidden = true)
	@NotNull(message = "创建者不能为空")
	private Long createUser;

	@ApiModelProperty(value = "权限描述")
	private String privDesc;

	@ApiModelProperty(value = "用户对象")
	private User user;

	@Column(name = "TID")
	@SeqID(name="RM_ROLE_S")
	public Long getTid() {
		return tid;
	}
	public void setTid(Long tid) {
		this.tid = tid;
	}

	@Column(name = "PRIV_NAME")
	public String getPrivName() {
		return privName;
	}
	public void setPrivName(String privName) {
		this.privName = privName;
	}

	@Column(name = "TITLE")
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

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

}
