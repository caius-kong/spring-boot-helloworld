package com.kyh.rest.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 配合jquery Datatables的分页实现
 */
public class Datatables<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final List<T> data = new ArrayList<T>();
	private final long total;
	private final int draw; //步进计数器，必须与页面保持一致；
	
	public Datatables(int draw, List<T> content, long total){
		if (null == content) {
			throw new IllegalArgumentException("Content must not be null!");
		}
		this.data.addAll(content);
		this.total = total;
		this.draw = draw;
	}
	
	public int getDraw(){
		return this.draw;
	}
	
	public long getRecordsTotal(){
		return total;
	}
	
	public long getRecordsFiltered(){
		return total;
	}
	
	public List<T> getData() {
		return Collections.unmodifiableList(data);
	}
}
