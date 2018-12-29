package net.evecom.rd.ie.baseline.core.db.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import org.beetl.sql.core.annotatoin.Table;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import java.util.List;

/**
 * @ClassName: Resources
 * @Description: 资源对象
 * @author： zhengc
 * @date： 2017年10月25日
 */
@Table(name = "DB_RESOURCE_T")
@ToString
public class Resources extends DataEntity<Resources> {

	@ApiModelProperty(value = "名称")
	private String resourceName;

	@ApiModelProperty(value = "路径")
	private String classpath;

	@ApiModelProperty(value = "表名")
	private String tableName;

	@Range(min = 0, max = 1, message = "缓存状态错误(0不缓存1缓存)")
	@ApiModelProperty(value = "是否缓存(0不缓存1缓存)")
	private Integer isCache;

	@Range(min = 0, max = 1, message = "类型错误(0基本类型1sql类型)")
	@ApiModelProperty(value = "类型(0基本类型1sql类型)")
	private Integer resourceType;

	@Column(name = "RESOURCE_NAME")
	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

    @Column(name = "CLASSPATH")
	public String getClasspath() {
		return classpath;
	}

	public void setClasspath(String classpath) {
		this.classpath = classpath;
	}

    @Column(name = "TABLENAME")
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

    @Column(name = "IS_CACHE")
	public Integer getIsCache() {
		return isCache;
	}

	public void setIsCache(Integer isCache) {
		this.isCache = isCache;
	}

    @Column(name = "RESOURCE_TYPE")
	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

}
