package net.evecom.rd.ie.baseline.core.db.model.entity;

import net.evecom.rd.ie.baseline.core.db.untis.GenCodeUtile;
import net.evecom.rd.ie.baseline.utils.verify.CheckUtil;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: ResProp
 * @Description: 资源属性对象
 * @author： zhengc
 * @date： 2017年10月25日
 */
@Table(name = "DB_RESOURCE_PROP_T")
public class ResProp extends DataEntity<ResProp> implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "RESOURCE_ID")
    @ApiModelProperty(value = "资源id")
    @NotNull(message = "资源id不能为空")
    private Long resourcesId;
    @Column(name = "PROP_NAME")
    @ApiModelProperty(value = "属性名")
    private String propName;
    @Column(name = "PROP_TYPE")
    @ApiModelProperty(value = "属性类型")
    @NotNull(message = "属性类型不能为空")
    private String propType;
    @Column(name = "FIELD_LENGTH")
    @ApiModelProperty(value = "数据库字段长度")
    private String fieldLength;
    @Column(name = "IS_PK")
    @ApiModelProperty(value = "是否主键（1：主键）", hidden = true)
    private int isPk = 0;        // 是否主键（1：主键）
    @Column(name = "IS_AUTO")
    @ApiModelProperty(value = "是否自增（1：是）", hidden = true)
    private int isAuto = 0;        // 是否主键（1：主键）AUTO_INCREMENT
    @Column(name = "TITLE")
    @ApiModelProperty(value = "标题")
    private String title;        //标题
    @Column(name = "TABLE_FIELD")
    @ApiModelProperty(value = "字段名")
    private String tableField;
    @Column(name = "IS_USE")
    @ApiModelProperty(value = "是否启用")
    private int isSystem = 0;
    @Column(name = "FIELD_COMMENT")
    @ApiModelProperty(value = "字段备注", hidden = true)
    private String fieldComment;
    @ApiModelProperty(value = "字段验证", hidden = true)
    private List<ResPropVerify> verifyList;
    @ApiModelProperty(value = "字段导出规则", hidden = true)
    private ResPropExl resPropExl;
    @ApiModelProperty(value = "是否创建列(0不创建1创建)")
    private Integer isCreate;

    public Integer getIsCreate() {
        return isCreate;
    }

    public void setIsCreate(Integer isCreate) {
        this.isCreate = isCreate;
    }

    public int getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(int isSystem) {
        this.isSystem = isSystem;
    }

    public ResPropExl getResPropExl() {
        return resPropExl;
    }

    public void setResPropExl(ResPropExl resPropExl) {
        this.resPropExl = resPropExl;
    }

    public String getTableField() {
        return tableField;
    }

    public void setTableField(String tableField) {
        this.tableField = tableField;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public int getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(int isAuto) {
        this.isAuto = isAuto;
    }

    public String getPropType() {
        return propType;
    }

    public void setPropType(String propType) {
        this.propType = propType;
    }

    public String getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(String fieldLength) {
        this.fieldLength = fieldLength;
    }

    public List<ResPropVerify> getVerifyList() {
        return verifyList;
    }

    public void setVerifyList(List<ResPropVerify> verifyList) {
        this.verifyList = verifyList;
    }

    public String getFieldComment() {
        return fieldComment;
    }

    public void setFieldComment(String fieldComment) {
        this.fieldComment = fieldComment;
    }

    public int getIsPk() {
        return isPk;
    }

    public void setIsPk(int isPk) {
        this.isPk = isPk;
    }

    public Long getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(Long resourcesId) {
        this.resourcesId = resourcesId;
    }

    public String getPropName() {
        if (CheckUtil.isNull(propName)) {
            propName = GenCodeUtile.setColName(this.propType);
        }
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }


    @Override
    public String toString() {
        return "ResourceAttribute [" + "resourcesId=" + resourcesId + "name=" + propName +
                "type=" + propType + "]" + "Address [" + getClass().getName() + "@" + Integer.toHexString(hashCode())
                + "]";
    }

}
