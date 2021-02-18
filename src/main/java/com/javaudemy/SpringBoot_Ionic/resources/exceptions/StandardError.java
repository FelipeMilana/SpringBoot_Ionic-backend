package com.javaudemy.SpringBoot_Ionic.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StandardError implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer httpStatus;
	private String msg;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant timeStamp;
	private String path;
	
	public StandardError() {
	}

	public StandardError(Integer httpStatus, String msg, Instant timeStamp, String path) {
		this.httpStatus = httpStatus;
		this.msg = msg;
		this.timeStamp = timeStamp;
		this.path = path;
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Instant getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Instant timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
}