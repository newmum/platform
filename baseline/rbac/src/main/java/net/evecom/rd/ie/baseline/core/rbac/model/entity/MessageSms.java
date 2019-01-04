package net.evecom.rd.ie.baseline.core.rbac.model.entity;

import lombok.ToString;
import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName: MessageSms
 * @Description: 短信消息实体类
 * @author： zhengc
 * @date： 2018年11月20日
 */
@Table(name = "MSG_SMS_T")
@ToString
public class MessageSms extends DataEntity<MessageSms> implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "名称")
	@NotNull(message = "名称不能为空")
	private String name;
	@ApiModelProperty(value = "内容")
	@NotNull(message = "内容不能为空")
	private String content;
	@ApiModelProperty(value = "类型")
	private String type;
	@ApiModelProperty(value = "创建人", hidden = true)
	private Long createUserId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

}
