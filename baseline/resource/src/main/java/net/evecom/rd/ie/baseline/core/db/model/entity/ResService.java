package net.evecom.rd.ie.baseline.core.db.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import org.beetl.sql.core.annotatoin.Table;
import javax.persistence.Column;

/**
 * @ClassName: ResService
 * @Description: 资源对象
 * @author： lwz
 * @date： 2018年12月24日
 */

@Table(name = "DB_RESOURCE_SERVICE_T")
@ToString
public class ResService extends DataEntity<Resources>{

    @ApiModelProperty(value = "资源ID")
    private Integer resourceId;

    @ApiModelProperty(value = "名称")
    private String title;

    @ApiModelProperty(value = "服务名称")
    private String serviceName;

    @ApiModelProperty(value = "是否启用(0不启用1启用)")
    private Integer isUse;

    @Column(name = "sql")
    @ApiModelProperty(value = "服务SQL")
    private String sql;

    @ApiModelProperty(value = "请求类型")
    private String serviceType;

    @ApiModelProperty(value = "服务描述")
    private String serviceDesc;

    @ApiModelProperty(value = "权限ID")
    private Integer privId;

    @Column(name = "RESOURCE_ID")
    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "SERVICE_NAME")
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Column(name = "IS_USE")
    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    @Column(name = "SQL")
    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Column(name = "SERVICE_TYPE")
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Column(name = "SERVICE_DESC")
    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    @Column(name = "PRIV_ID")
    public Integer getPrivId() {
        return privId;
    }

    public void setPrivId(Integer privId) {
        this.privId = privId;
    }

    @Override
    public String toString() {
        return "ResService{" +
                "resourceId=" + resourceId +
                ", title='" + title + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", isUse=" + isUse +
                ", sql='" + sql + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", serviceDesc='" + serviceDesc + '\'' +
                ", privId=" + privId +
                '}';
    }
}
