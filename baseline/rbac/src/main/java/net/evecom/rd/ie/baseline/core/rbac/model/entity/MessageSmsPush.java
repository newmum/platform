package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @ClassName: MessageSmsPush
 * @Description: 短信推送实体类
 * @author： zhengc
 * @date： 2018年11月20日
 */
@Table(name = "msg_sms_push_t")
public class MessageSmsPush extends DataEntity<MessageSmsPush> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "类型")
    private int type;

    @ApiModelProperty(value = "创建人id")
    private Long createUserId;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Column(name = "create_user_id")
    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    @Column(name = "remarks")
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
