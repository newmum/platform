package net.evecom.rd.ie.baseline.core.db.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.evecom.rd.ie.baseline.utils.report.exl.ExcelField;
import net.evecom.rd.ie.baseline.utils.datetime.DTUtil;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.TailBean;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.DateTemplate;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: DataEntity
 * @Description: JAVABEAN基类
 * @author： zhengc
 * @date： 2018-10-30
 */
public class DataEntity<T> extends TailBean implements Serializable {

    /**
     * 是否标记（0：否；1：是；）
     */
    public static final int NO  = 0;
    public static final int YES = 1;

	@ApiModelProperty(value = "编号")
	protected Long tid;

    @ExcelField(align = 2, sort = 4, title = "创建时间")
    @ApiModelProperty(value = "创建时间", hidden = true)
    protected Date createTime;

	@ExcelField(align = 2, sort = 3, title = "更新时间")
	@ApiModelProperty(value = "更新时间", hidden = true)
	protected Date updateTime;

    @ApiModelProperty(value = "是否删除：0否；1是；", hidden = true)
    protected Integer isDel = NO;

	/**
	 * 插入之前执行方法，需要手动调用
	 */
	public void preInsert() {
		this.createTime = DTUtil.nowDate();
        this.updateTime = DTUtil.nowDate();
	}

	/**
	 * 更新之前执行方法，需要手动调用
	 */
	public void preUpdate() {
		this.updateTime = DTUtil.nowDate();
	}

    @AssignID("createId")
    @Column(name = "TID")
    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "IS_DEL")
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
