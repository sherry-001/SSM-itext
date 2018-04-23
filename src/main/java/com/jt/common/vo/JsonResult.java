package com.jt.common.vo;

import java.io.Serializable;

public class JsonResult implements Serializable{


	private static final long serialVersionUID = 1L;
	
	/**表示状态码 1表示ok,0表示失败*/
	private int state=1;
	/**具体消息*/
	private String message;
	/*具体数据*/
	private Object data;
	public JsonResult() {
	}
	public JsonResult(Object data) {
		this.data = data;
	}
	
	public JsonResult(String message) {
		this.message = message;
	}
	public JsonResult(String message, Object data) {
		this.message = message;
		this.data = data;
	}
	public JsonResult(Throwable tx) {
		this.state =0;
		this.message=tx.getMessage();
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	}
