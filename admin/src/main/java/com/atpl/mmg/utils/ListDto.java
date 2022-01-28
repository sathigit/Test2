package com.atpl.mmg.utils;

import java.util.List;

import com.atpl.mmg.domain.blood.BloodDomain;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class ListDto<T> {
	
	private int totalSize;
	private List<T> list;

	public ListDto() {
	}
	
	public ListDto(int totalSize, List<T> list) {

		this.totalSize = totalSize;
		this.list = list;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
