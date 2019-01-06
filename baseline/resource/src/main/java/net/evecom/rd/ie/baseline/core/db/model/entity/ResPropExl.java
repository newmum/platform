package net.evecom.rd.ie.baseline.core.db.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import org.beetl.sql.core.annotatoin.Table;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @ClassName: ResPropExl
 * @Description: 资源属性报表对象
 * @author： zhengc
 * @date： 2017年10月25日
 */
@Table(name = "DB_RESOURCE_PROP_EXL_T")
@ToString
public class ResPropExl extends DataEntity<ResPropExl> implements Serializable {

    @ApiModelProperty(value = "资源属性id")
    private String propId;

    @ApiModelProperty(value = "标题")
    private String title;
    @Range(min = 0, max = 1, message = "显示状态错误(0不显示,1显示)")

    @ApiModelProperty(value = "是否显示(0不显示,1显示)")
    private Integer isShow;

    @ApiModelProperty(value = "显示类型(1,基本,2关联表)")
    private Integer showType;

    @ApiModelProperty(value = "导出字段对齐方式（0：自动；1：靠左；2：居中；3：靠右）")
    private Integer align;

    @ApiModelProperty(value = "字段类型（0：导出导入；1：仅导出；2：仅导入）")
    private Integer type;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    public static ResPropExl getDefaultData(){
        ResPropExl resPropExl=new ResPropExl();
        resPropExl.setAlign(2);
        resPropExl.setType(0);
        resPropExl.setIsShow(1);
        resPropExl.setShowType(1);
        return resPropExl;
    }

    @Column(name = "PROP_ID")
    public String getPropId() {
        return propId;
    }

    public void setPropId(String propId) {
        this.propId = propId;
    }

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Column(name = "IS_SHOW")
    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }
    @Column(name = "SHOW_TYPE")
    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }
    @Column(name = "ALIGN")
    public Integer getAlign() {
        return align;
    }

    public void setAlign(Integer align) {
        this.align = align;
    }
    @Column(name = "PROP_TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    @Column(name = "SORT")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}
