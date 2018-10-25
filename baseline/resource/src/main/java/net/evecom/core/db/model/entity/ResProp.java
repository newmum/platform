package net.evecom.core.db.model.entity;

import net.evecom.core.db.untis.GenCodeUtile;
import net.evecom.utils.verify.CheckUtil;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: ResProp
 * @Description: 资源属性对象 @author： zhengc @date： 2017年10月25日
 */
@Table(name = "res_prop")
public class ResProp extends DataEntity<ResProp> implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "resources_id")
    @ApiModelProperty(value = "资源id")
    @NotNull(message = "资源id不能为空")
    private Long resourcesId;
    @Column(name = "name")
    @ApiModelProperty(value = "属性名")
    private String name;
    @Column(name = "type")
    @ApiModelProperty(value = "属性类型")
    @NotNull(message = "属性类型不能为空")
    private String type;
    @Column(name = "jdbc_field")
    @ApiModelProperty(value = "数据库字段名", hidden = true)
    private String jdbcField;
    @Column(name = "jdbc_type")
    @ApiModelProperty(value = "数据库字段类型")
    private String jdbcType;
    @Column(name = "length")
    @ApiModelProperty(value = "数据库字段长度")
    private String length;
    @Column(name = "ui_view")
    @ApiModelProperty(value = "界面展示")
    private String uiView;
    @Column(name = "is_pk")
    @ApiModelProperty(value = "是否主键（1：主键）", hidden = true)
    private int isPk = 0;        // 是否主键（1：主键）
    @Column(name = "is_auto")
    @ApiModelProperty(value = "是否自增（1：是）", hidden = true)
    private int isAuto = 0;        // 是否主键（1：主键）AUTO_INCREMENT
    @Column(name = "value")
    @ApiModelProperty(value = "默认值")
    private String value;        // 默认值
    @Column(name = "title")
    @ApiModelProperty(value = "标题")
    private String title;        //标题
    @Column(name = "sort")
    @ApiModelProperty(value = "排序")
    private int sort = 0;
    @Column(name = "is_system")
    @ApiModelProperty(value = "是否是系统字段(0不是,1是)")
    private int isSystem = 0;
    @Column(name = "comments")
    @ApiModelProperty(value = "字段备注", hidden = true)
    private String comments;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getUiView() {
        return uiView;
    }

    public void setUiView(String uiView) {
        this.uiView = uiView;
    }

    public int getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(int isAuto) {
        this.isAuto = isAuto;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<ResPropVerify> getVerifyList() {
        return verifyList;
    }

    public void setVerifyList(List<ResPropVerify> verifyList) {
        this.verifyList = verifyList;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getIsPk() {
        return isPk;
    }

    public void setIsPk(int isPk) {
        this.isPk = isPk;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public Long getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(Long resourcesId) {
        this.resourcesId = resourcesId;
    }

    public String getName() {
        if (CheckUtil.isNull(name)) {
            name = GenCodeUtile.setColName(this.jdbcField);
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJdbcField() {
        return jdbcField;
    }

    public void setJdbcField(String jdbcField) {
        this.jdbcField = jdbcField;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ResourceAttribute [" + "resourcesId=" + resourcesId + "name=" + name + "jdbcField=" + jdbcField
                + "type=" + type + "]" + "Address [" + getClass().getName() + "@" + Integer.toHexString(hashCode())
                + "]";
    }

}
