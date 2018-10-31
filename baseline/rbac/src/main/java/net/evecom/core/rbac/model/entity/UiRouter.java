package net.evecom.core.rbac.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import net.evecom.core.db.model.entity.DataEntity;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName: UiRouter
 * @Description: 前端路由对象
 * @author： zhengc
 * @date： 2018年10月31日
 */
@Table(name = "UI_ROUTER")
@ToString
public class UiRouter extends DataEntity<UiRouter> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "parent_id")
	@ApiModelProperty(value = "父类id")
	@NotNull(message = "父类不能为空")
	private Long parentId;
	@Column(name = "title")
	@ApiModelProperty(value = "名称")
	@NotNull(message = "名称不能为空")
	private String title;
	@Column(name = "sort")
	@ApiModelProperty(value = "排序")
	private Long sort;
	@Column(name = "path")
	@ApiModelProperty(value = "界面路径")
	private String path;
	@Column(name = "path_type")
	@ApiModelProperty(value = "链接类型(1是超链接 2不是超链接)", hidden = true)
	private int pathType;
	@Column(name = "href")
	@ApiModelProperty(value = "链接")
	private String href;
	@Column(name = "name")
	@ApiModelProperty(value = "路由名")
	private String name;
	@Column(name = "icon")
	@ApiModelProperty(value = "图标")
	private String icon;
	@Column(name = "is_menu")
	@ApiModelProperty(value = "是否展示")
	private int isMenu;
	@Column(name = "crm_power_id")
	@ApiModelProperty(value = "权限id")
	private Long crmPowerId;
	@Column(name = "create_user_id")
	@ApiModelProperty(value = "创建人id", hidden = true)
	private Long createUserId;
	@Column(name = "remarks")
	@ApiModelProperty(value = "备注")
	private String remarks;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(int isMenu) {
		this.isMenu = isMenu;
	}

	public Long getCrmPowerId() {
		return crmPowerId;
	}

	public void setCrmPowerId(Long crmPowerId) {
		this.crmPowerId = crmPowerId;
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
		return "UiRouter [" + "parentId=" + parentId + "," + "title=" + title + "," + "sort=" + sort + "," + "path="
				+ path + "," + "pathType=" + pathType + "," + "href=" + href + "," + "name=" + name + "," + "icon="
				+ icon + "," + "isMenu=" + isMenu + "," + "crmPowerId=" + crmPowerId + "," + "createUserId="
				+ createUserId + "]" + "Address [" + getClass().getName() + "@"
				+ Integer.toHexString(hashCode()) + "]";
	}

}
