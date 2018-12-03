package net.evecom.core.rbac.model.entity;

import net.evecom.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author xiejun
 * @since 1.0
 */
@Table(name = "msg_sms_push_t")
public class MessageSmsPush extends DataEntity<MessageSmsPush> implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "name")
    @ApiModelProperty(value = "名称")
    private String name;
    @Column(name = "mobile")
    @ApiModelProperty(value = "手机")
    private String mobile;
    @Column(name = "content")
    @ApiModelProperty(value = "内容")
    private String content;
    @Column(name = "type")
    @ApiModelProperty(value = "类型")
    private int type;
    @Column(name = "create_user_id")
    @ApiModelProperty(value = "创建人id")
    private Long createUserId;
    @Column(name = "remarks")
    @ApiModelProperty(value = "备注")
    private String remarks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
        return "MessageSmsPush [" +
                "name=" + name + "," +
                "mobile=" + mobile + "," +
                "content=" + content + "," +
                "type=" + type + "," +
                "createUserId=" + createUserId + "," +
                "remarks=" + remarks +
                "]" + "Address [" +
                getClass().getName() + "@" +
                Integer.toHexString(hashCode()) +
                "]";
    }

}
