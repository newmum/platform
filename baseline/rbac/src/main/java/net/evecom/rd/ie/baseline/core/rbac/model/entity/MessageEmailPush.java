package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @ClassName: MessageEmailPush
 * @Description: 邮件推送实体类
 * @author： zhengc
 * @date： 2018年11月20日
 */
 @Table(name = "msg_email_push_t")
public class MessageEmailPush  extends DataEntity<MessageEmailPush> implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "内容")
	private String content;

	@ApiModelProperty(value = "主题")
	private String theme;

	@ApiModelProperty(value = "类型")
	private int type;

	@ApiModelProperty(value = "创建人id")
	private Long createUserId;

	@ApiModelProperty(value = "备注")
	private String remarks;

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "theme")
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Column(name = "type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(name = "create_user")
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
		return "MessageEmailPush ["+
		"email=" + email + ","+
		"name=" + name + ","+
		"content=" + content + ","+
		"theme=" + theme + ","+
		"type=" + type + ","+
		"createUserId=" + createUserId + ","+
		"remarks=" + remarks +
		"]"+"Address ["+
		getClass().getName() + "@" +
		Integer.toHexString(hashCode())+
		"]";
	}

}
