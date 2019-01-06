package net.evecom.rd.ie.baseline.core.mvc.model.entity;

import lombok.ToString;
import net.evecom.rd.ie.baseline.core.db.model.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @ClassName: SystemLog
 * @Description: 系统日志对象
 * @author： zhengc
 * @date： 2018年10月31日
 */
@Table(name = "SYSTEM_LOG")
@ToString
public class SystemLog extends DataEntity<SystemLog> implements Serializable {


	@ApiModelProperty(value = "用户id")
	private String userId;

	@ApiModelProperty(value = "方法名")
	private String method;

	@ApiModelProperty(value = "参数")
	private String parameter;

	@ApiModelProperty(value = "耗时")
	private Long time;

	@ApiModelProperty(value = "执行状态(1成功,0失败)")
	private int isSuccess=0;

	@ApiModelProperty(value = "备注")
	private String remarks;

	public SystemLog(){
		this.isSuccess=0;
	}

	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

    @Column(name = "method")
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

    @Column(name = "parameter")
	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

    @Column(name = "time")
	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

    @Column(name = "is_success")
	public int getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}

    @Column(name = "remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
