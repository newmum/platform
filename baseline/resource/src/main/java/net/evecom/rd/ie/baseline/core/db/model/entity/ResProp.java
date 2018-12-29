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

    @ApiModelProperty(value = "资源id")
    @NotNull(message = "资源id不能为空")
    private Long resourcesId;

    @ApiModelProperty(value = "属性名")
    private String propName;

    @ApiModelProperty(value = "属性类型")
    @NotNull(message = "属性类型不能为空")
    private String propType;

    @ApiModelProperty(value = "数据库字段长度")
    private String fieldLength;

    @ApiModelProperty(value = "是否主键（1：主键）", hidden = true)
    private int isPk = 0;        // 是否主键（1：主键）

    @ApiModelProperty(value = "是否自增（1：是）", hidden = true)
    private int isAuto = 0;        // 是否主键（1：主键）AUTO_INCREMENT

    @ApiModelProperty(value = "标题")
    private String title;        //标题

    @ApiModelProperty(value = "字段名")
    private String tableField;

    @ApiModelProperty(value = "是否启用")
    private int isSystem = 0;

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

    @Column(name = "IS_USE")
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

    @Column(name = "TABLE_FIELD")
    public String getTableField() {
        return tableField;
    }

    public void setTableField(String tableField) {
        this.tableField = tableField;
    }

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "IS_AUTO")
    public int getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(int isAuto) {
        this.isAuto = isAuto;
    }

    @Column(name = "PROP_TYPE")
    public String getPropType() {
        return propType;
    }

    public void setPropType(String propType) {
        this.propType = propType;
    }

    @Column(name = "FIELD_LENGTH")
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

    @Column(name = "FIELD_COMMENT")
    public String getFieldComment() {
        return fieldComment;
    }

    public void setFieldComment(String fieldComment) {
        this.fieldComment = fieldComment;
    }

    @Column(name = "IS_PK")
    public int getIsPk() {
        return isPk;
    }

    public void setIsPk(int isPk) {
        this.isPk = isPk;
    }

    @Column(name = "RESOURCE_ID")
    public Long getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(Long resourcesId) {
        this.resourcesId = resourcesId;
    }

    @Column(name = "PROP_NAME")
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
