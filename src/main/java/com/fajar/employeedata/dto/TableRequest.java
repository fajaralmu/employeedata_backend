package com.fajar.employeedata.dto;

import java.io.Serializable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
public class TableRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2868888441245438947L;
	private int limit;
	private int page;
	private String orderBy;
	@Getter(value=AccessLevel.NONE)
	private String orderType;
	
	public String getOrderType() {
		if ("asc".equals(orderType)) {
			orderType = "asc";
		} else {
			orderType = "desc";
		}
		return orderType;
	}

	public Order getOrder() {
		if (null == getOrderBy()) return null;
		if ("asc".equals(getOrderType())) {
			return Order.asc(getOrderBy());
		} else {
			return Order.desc(getOrderBy());
		}
	}
	
	@JsonIgnore

	public Pageable getPageRequest() {
		Sort sort = Sort.by(getOrder());
		int size = getLimit() > 0 ? getLimit() : Integer.MAX_VALUE;
		int page = getPage();
		return PageRequest.of(page, size, sort);
	}

}
