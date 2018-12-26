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

	@Column(name = "PARENT_ID")
	@ApiModelProperty(value = "父类id")
	@NotNull(message = "父类不能为空")
	private Long parentId;
	@Column(name = "TITLE")
	@ApiModelProperty(value = "名称")
	@NotNull(message = "名称不能为空")
	private String title;
	@Column(name = "SORT")
	@ApiModelProperty(value = "排序")
	private Long sort;
	@Column(name = "PATH")
	@ApiModelProperty(value = "界面路径")
	private String path;
	@Column(name = "PATH_TYPE")
	@ApiModelProperty(value = "链接类型(1是超链接 2不是超链接)", hidden = true)
	private int pathType;
	@Column(name = "HREF")
	@ApiModelProperty(value = "链接")
	private String href;
	@Column(name = "ROUTER_NAME")
	@ApiModelProperty(value = "路由名")
	private String routerName;
	@Column(name = "ICON")
	@ApiModelProperty(value = "图标")
	private String icon;
	@Column(name = "IS_SHOW")
	@ApiModelProperty(value = "是否展示")
	private int isShow;
	@Column(name = "POWER_ID")
	@ApiModelProperty(value = "权限id")
	private Long crmPowerId;
	@Column(name = "CREATE_USER")
	@ApiModelProperty(value = "创建者", hidden = true)
	private Long createUser;
	@ApiModelProperty(value = "请求方式")
	private String method;
	@ApiModelProperty(value = "请求路径")
	private String url;

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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getPathType() {
		return pathType;
	}

	public void setPathType(int pathType) {
		this.pathType = pathType;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public Long getCrmPowerId() {
		return crmPowerId;
	}

	public void setCrmPowerId(Long crmPowerId) {
		this.crmPowerId = crmPowerId;
	}

	public String getRouterName() {
		return routerName;
	}

	public void setRouterName(String routerName) {
		this.routerName = routerName;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
}
