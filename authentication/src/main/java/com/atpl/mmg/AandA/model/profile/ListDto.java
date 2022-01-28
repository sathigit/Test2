package com.atpl.mmg.AandA.model.profile;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class ListDto<T> {
	private Integer totalSize;
	private List<T> list;

	public ListDto() {
	}

	public ListDto(Integer totalSize, List<T> list) {

		this.totalSize = totalSize;
		this.list = list;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Integer getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}

}
