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

    @ApiModelProperty(value = "配置名称", hidden = true)
    private String configName;

    @ApiModelProperty(value = "配置标题")
    private String title;

    @ApiModelProperty(value = "配置值")
    private String configValue;

    @ApiModelProperty(value = "是否启用")
    private Integer isUse;

    @ApiModelProperty(value = "排序")
    private String sort;

    @ApiModelProperty(value = "类型")
    private Integer configType;

    @ApiModelProperty(value = "创建者")
    private Long createUser;

    @ApiModelProperty(value = "配置描述")
    private String configDesc;

    @Column(name = "CONFIG_NAME")
    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "CONFIG_VALUE")
    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    @Column(name = "IS_USE")
    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    @Column(name = "SORT")
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Column(name = "CONFIG_TYPE")
    public Integer getConfigType() {
        return configType;
    }

    public void setConfigType(Integer configType) {
        this.configType = configType;
    }

    @Column(name = "CREATE_USER")
    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    @Column(name = "CONFIG_DESC")
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
