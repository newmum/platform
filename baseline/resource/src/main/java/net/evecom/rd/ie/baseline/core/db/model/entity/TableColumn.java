package net.evecom.rd.ie.baseline.core.db.model.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 列的属性
 *
 * @author xiejun
 * @date 2018年6月1日14:19:51
 */
public class TableColumn {
	@ApiModelProperty(value = "字段名", hidden = true)
	private String columnName;
	@ApiModelProperty(value = "字段属性", hidden = true)
	private String dataType;
	@ApiModelProperty(value = "字段备注", hidden = true)
	private String comments;
	@ApiModelProperty(value = "是否主键（1：主键）", hidden = true)
	private int isPk=0;        // 是否主键（1：主键）
	// 属性名称(第一个字母大写)，如：user_name => UserName
	private String attrName;
	// 属性名称(第一个字母小写)，如：user_name => userName
	private String attrname;
	// 属性类型
	private String attrType;
	// auto_increment
	private String extra;

	public TableColumn() {
		this.isPk = 0;
	}

	public int getIsPk() {
		return isPk;
	}

	public void setIsPk(int isPk) {
		this.isPk = isPk;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getAttrname() {
		return attrname;
	}

	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrType() {
		return attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
}
