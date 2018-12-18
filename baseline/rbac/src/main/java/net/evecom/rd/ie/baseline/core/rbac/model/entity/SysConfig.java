package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import io.swagger.annotations.ApiModelProperty;
import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;

/**
 * @ClassName: SysConfig
 * @Description: 系统默认配置类
 * @author： lwz
 * @date： 2018年12月14日
 */
@Table(name = "SYS_CONFIG_T")
public class SysConfig extends DataEntity<SysConfig>{
    @Column(name = "CONFIG_NAME")
    @ApiModelProperty(value = "配置名称", hidden = true)
    private String configName;
    @Column(name = "TITLE")
    @ApiModelProperty(value = "配置标题")
    private String title;
    @Column(name = "CONFIG_VALUE")
    @ApiModelProperty(value = "配置值")
    private String configValue;
    @Column(name = "IS_USE")
    @ApiModelProperty(value = "是否启用")
    private Integer isUse;
    @Column(name = "SORT")
    @ApiModelProperty(value = "排序")
    private String sort;
    @Column(name = "CONFIG_TYPE")
    @ApiModelProperty(value = "类型")
    private Integer configType;
    @Column(name = "CREATE_USER")
    @ApiModelProperty(value = "创建者")
    private Long createUser;
    @Column(name = "CONFIG_DESC")
    @ApiModelProperty(value = "配置描述")
    private String configDesc;

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Integer getConfigType() {
        return configType;
    }

    public void setConfigType(Integer configType) {
        this.configType = configType;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getConfigDesc() {
        return configDesc;
    }

    public void setConfigDesc(String configDesc) {
        this.configDesc = configDesc;
    }

    @Override
    public String toString() {
        return "SysConfig{" +
                "configName=" + configName +
                ", title='" + title + '\'' +
                ", configValue='" + configValue + '\'' +
                ", isUse='" + isUse + '\'' +
                ", sort='" + sort + '\'' +
                ", configType=" + configType +
                ", createUser=" + createUser +
                ", configDesc='" + configDesc + '\'' +
                '}';
    }
}
