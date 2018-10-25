package net.evecom.tools.service;

import java.io.Serializable;
import java.util.List;

/**
 * 分页:返回参数
 *
 * @param <T>
 */
public class Page<T> implements Serializable {

	private Integer page;

	private Integer pageSize;

	private Long total;

	private List<T> list;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
