package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName: Menu
 * @Description: 前端菜单组件对象
 * @author： zhengc
 * @date： 2018年12月18日
 */
@Table(name = "RM_MENU_T")
@ToString
public class Menu extends DataEntity<Menu> implements Serializable {

	@ApiModelProperty(value = "父类id")
	@NotNull(message = "父类不能为空")
	private Long parentId;

	@ApiModelProperty(value = "名称")
	@NotNull(message = "名称不能为空")
	private String title;

	@ApiModelProperty(value = "排序")
	private Long sort;

	@ApiModelProperty(value = "界面路径")
	private String path;

	@ApiModelProperty(value = "链接类型(1是超链接 2不是超链接)", hidden = true)
	private int pathType;

	@ApiModelProperty(value = "链接")
	private String href;

	@ApiModelProperty(value = "路由名")
	private String routerName;

	@ApiModelProperty(value = "图标")
	private String icon;

	@ApiModelProperty(value = "是否展示")
	private int isShow;

	@ApiModelProperty(value = "权限id")
	private Long crmPowerId;

	@ApiModelProperty(value = "创建者", hidden = true)
	private Long createUser;
	@ApiModelProperty(value = "请求方式")
	private String method;
	@ApiModelProperty(value = "请求路径")
	private String url;

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

	@Column(name = "PARENT_ID")
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "SORT")
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	@Column(name = "PATH")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "PATH_TYPE")
	public int getPathType() {
		return pathType;
	}

	public void setPathType(int pathType) {
		this.pathType = pathType;
	}

	@Column(name = "HREF")
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Column(name = "ICON")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "IS_SHOW")
	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	@Column(name = "POWER_ID")
	public Long getCrmPowerId() {
		return crmPowerId;
	}

	public void setCrmPowerId(Long crmPowerId) {
		this.crmPowerId = crmPowerId;
	}

	@Column(name = "ROUTER_NAME")
	public String getRouterName() {
		return routerName;
	}

	public void setRouterName(String routerName) {
		this.routerName = routerName;
	}

	@Column(name = "CREATE_USER")
	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
}
