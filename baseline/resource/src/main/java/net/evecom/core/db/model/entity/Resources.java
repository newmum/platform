package net.evecom.core.db.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import org.beetl.sql.core.annotatoin.Table;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: Resources
 * @Description: 资源对象
 * @author： zhengc
 * @date： 2017年10月25日
 */
@Table(name = "RESOURCES")
@ToString
public class Resources extends DataEntity<Resources> implements Serializable {

	@Column(name = "name")
	@ApiModelProperty(value = "名称")
	private String name;
	@Column(name = "classpath")
	@ApiModelProperty(value = "路径")
	private String classpath;
	@Column(name = "table_name")
	@ApiModelProperty(value = "表名")
	private String tableName;
	@Column(name = "is_cache")
	@Range(min = 0, max = 1, message = "缓存状态错误(0不缓存1缓存)")
	@ApiModelProperty(value = "是否缓存(0不缓存1缓存)")
	private Integer isCache;
	@Column(name = "sql")
	@ApiModelProperty(value = "sql模板")
	private String sql;
	@Column(name = "res_type")
	@Range(min = 0, max = 1, message = "类型错误(0基本类型1sql类型)")
	@ApiModelProperty(value = "类型(0基本类型1sql类型)")
	private Integer resType;

	@ApiModelProperty(value = "是否创建表格(0不创建1创建)")
	private Integer isCreate;
	@ApiModelProperty(value = "字段属性集合")
	private List<ResProp> resPropList;

	public List<ResProp> getResPropList() {
		return resPropList;
	}

	public void setResPropList(List<ResProp> resPropList) {
		this.resPropList = resPropList;
	}

	public Integer getIsCreate() {
		return isCreate;
	}

	public void setIsCreate(Integer isCreate) {
		this.isCreate = isCreate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClasspath() {
		return classpath;
	}

	public void setClasspath(String classpath) {
		this.classpath = classpath;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getIsCache() {
		return isCache;
	}

	public void setIsCache(Integer isCache) {
		this.isCache = isCache;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Integer getResType() {
		return resType;
	}

	public void setResType(Integer resType) {
		this.resType = resType;
	}

	@Override
	public String toString() {
		return "Resources [" + "name=" + name + "classpath=" + classpath + "tableName=" + tableName + "isCache="
				+ isCache + "sql=" + sql + "resType=" + resType + "]" + "Address [" + getClass().getName() + "@"
				+ Integer.toHexString(hashCode()) + "]";
	}

}
