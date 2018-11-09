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
 * @ClassName: DataEntity
 * @Description: JAVABEAN基类
 * @author： zhengc
 * @date： 2018-10-30
 */
public abstract class DataEntity<T> implements Serializable {

    /**
     * 是否标记（0：否；1：是；）
     */
    public static final int NO  = 0;
    public static final int YES = 1;

	@Column(name = "ID")
	@ApiModelProperty(value = "编号")
	@AutoID
	protected Long id;

    @Column(name = "CREATE_DATE")
    @ExcelField(align = 2, sort = 4, title = "创建时间")
    @ApiModelProperty(value = "创建时间", hidden = true)
    protected Date createDate;

	@Column(name = "EDIT_DATE")
	@ExcelField(align = 2, sort = 3, title = "更新时间")
	@ApiModelProperty(value = "更新时间", hidden = true)
	protected Date editDate;

    @Column(name = "IS_DEL")
    @ApiModelProperty(value = "是否删除：0否；1是；", hidden = true)
    protected Integer isDel = NO;

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
		if (this.editDate == null) {
			this.editDate = DTUtil.nowDate();
		}
		this.createDate = new Date();
	}

	/**
	 * 更新之前执行方法，需要手动调用
	 */
	public void preUpdate() {
		// User user = UserUtils.getUser();
		// if (StringUtils.isNotBlank(user.getId())) {
		// this.updateUser = user;
		// }
		this.editDate = DTUtil.nowDate();
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
