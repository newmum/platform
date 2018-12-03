package net.evecom.core.db.model.entity;

import io.swagger.annotations.ApiModelProperty;
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
@Table(name = "db_resource_prop_exl_t")
public class ResPropExl extends DataEntity<ResPropExl> implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "PROP_ID")
    @ApiModelProperty(value = "资源属性id")
    private Long propId;
    @Column(name = "TITLE")
    @ApiModelProperty(value = "标题")
    private String title;
    @Range(min = 0, max = 1, message = "显示状态错误(0不显示,1显示)")
    @Column(name = "IS_SHOW")
    @ApiModelProperty(value = "是否显示(0不显示,1显示)")
    private Integer isShow;
    @Column(name = "show_type")
    @ApiModelProperty(value = "显示类型(1,基本,2关联表)")
    private Integer showType;
    @Column(name = "ALIGN")
    @ApiModelProperty(value = "导出字段对齐方式（0：自动；1：靠左；2：居中；3：靠右）")
    private Integer align;
    @Column(name = "PROP_TYPE")
    @ApiModelProperty(value = "字段类型（0：导出导入；1：仅导出；2：仅导入）")
    private Integer type;
    @Column(name = "SORT")
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


    public ResPropExl() {

    }

    public Long getPropId() {
        return propId;
    }

    public void setPropId(Long propId) {
        this.propId = propId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public Integer getAlign() {
        return align;
    }

    public void setAlign(Integer align) {
        this.align = align;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "ResPropExl [" +
                "resPropId=" + propId + "," +
                "title=" + title + "," +
                "isShow=" + isShow + "," +
                "showType=" + showType + "," +
                "align=" + align + "," +
                "sort=" + sort + "," +
                "type=" + type +
                "]" + "Address [" +
                getClass().getName() + "@" +
                Integer.toHexString(hashCode()) +
                "]";
    }

}
