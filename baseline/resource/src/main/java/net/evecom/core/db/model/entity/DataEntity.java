package net.evecom.core.db.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.evecom.utils.report.exl.ExcelField;
import net.evecom.utils.datetime.DTUtil;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.AutoID;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: Resources
 * @Description: 对象抽象基类 @author： zhengc @date： 2014-05-16
 */
public abstract class DataEntity<T> implements Serializable {
	/**
	 * 删除标记（1：正常；2：删除；3：审核；）
	 */
	public static final int DEL_FLAG_NORMAL = 1;
	public static final int DEL_FLAG_DELETE = 2;
	public static final int DEL_FLAG_AUDIT = 3;
	@Column(name = "id")
	@ApiModelProperty(value = "id")
	@AutoID
	protected Long id;
	@Column(name = "utime")
	@ExcelField(align = 2, sort = 3, title = "更新时间")
	@ApiModelProperty(value = "更新时间", hidden = true)
	protected Date utime; // 更新日期
	@Column(name = "atime")
	@ExcelField(align = 2, sort = 4, title = "加入时间")
	@ApiModelProperty(value = "加入时间", hidden = true)
	protected Date atime; // 加入日期
	@Column(name = "status")
	@ApiModelProperty(value = "状态 1正常2删除3审核", hidden = true)
	private Integer status = DEL_FLAG_NORMAL;

	public DataEntity() {
		super();
	}

	/**
	 * 插入之前执行方法，需要手动调用
	 */
	public void preInsert() {
		// // 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		// if (!this.isNewRecord) {
		// setId(IdGen.uuid());
		// }
		// User user = UserUtils.getUser();
		// if (StringUtils.isNotBlank(user.getId())) {
		// this.updateUser = user;
		// this.createUser = user;
		// }
		if (this.utime == null) {
			this.utime = DTUtil.nowDate();
		}
		this.atime = new Date();
	}

	/**
	 * 更新之前执行方法，需要手动调用
	 */
	public void preUpdate() {
		// User user = UserUtils.getUser();
		// if (StringUtils.isNotBlank(user.getId())) {
		// this.updateUser = user;
		// }
		this.utime =DTUtil.nowDate();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUtime() {
		return utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAtime() {
		return atime;
	}

	public void setAtime(Date atime) {
		this.atime = atime;
	}

}
