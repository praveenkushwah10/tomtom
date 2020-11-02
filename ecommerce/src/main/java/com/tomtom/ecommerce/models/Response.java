package com.tomtom.ecommerce.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Response<T>{
	
	private T data;
	@JsonIgnore
	private int httpStatus = 200;
	
	private List<MessageModel> errors;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public List<MessageModel> getErrors() {
		return errors;
	}

	public void setErrors(List<MessageModel> errors) {
		this.errors = errors;
	}

}

