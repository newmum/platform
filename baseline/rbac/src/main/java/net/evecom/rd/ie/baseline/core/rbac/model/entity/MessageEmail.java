package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @ClassName: MessageEmail
 * @Description: 邮件消息实体类
 * @author： zhengc
 * @date： 2018年11月20日
 */
 @Table(name = "msg_email_t")
public class MessageEmail  extends DataEntity<MessageEmail> implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "标题")
	private String name;

	@ApiModelProperty(value = "内容")
	private String content;

	@ApiModelProperty(value = "类型")
	private int type;

	@ApiModelProperty(value = "创建人id")
	private Long createUserId;

	@Column(name = "TITLE")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "EMAIL_TYPE")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(name = "CREATE_USER")
	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	@Override
	public String toString() {
		return "MessageEmail ["+
		"name=" + name + ","+
		"content=" + content + ","+
		"type=" + type + ","+
		"createUserId=" + createUserId + ","+
		"]"+"Address ["+
		getClass().getName() + "@" +
		Integer.toHexString(hashCode())+
		"]";
	}

}
