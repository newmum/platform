package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName: UI_ROUTER_T
 * @Description: 前端路由对象
 * @author： zhengc
 * @date： 2018年12月18日
 */
@Table(name = "UI_ROUTER_T")
@ToString
public class Router extends DataEntity<Router> implements Serializable {

	@ApiModelProperty(value = "名称")
	@NotNull(message = "名称不能为空")
	private String title;

	@ApiModelProperty(value = "界面路径")
	private String path;

	@ApiModelProperty(value = "路由名")
	private String routerName;

    @ApiModelProperty(value = "前端组件位置")
    private String component;

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "PATH")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Column(name = "ROUTER_NAME")
    public String getRouterName() {
        return routerName;
    }

    public void setRouterName(String routerName) {
        this.routerName = routerName;
    }

    @Column(name = "COMPONENT")
    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }
}
