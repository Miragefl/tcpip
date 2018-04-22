package com.viscum.exception;

public class WxException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 7512008460932207941L;


	/**
	 * 错误码
	 */
	private String errCode;
	/**
	 * 错误信息
	 */
	private String errMsg;
	/**
	 * 异常
	 */
	private Exception ex;


	public WxException(String errCode, String errMsg, Exception ex) {
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.ex = ex;
	}
	public WxException(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public Exception getEx() {
		return ex;
	}
	public void setEx(Exception ex) {
		this.ex = ex;
	}

	@Override
	public String toString() {
		return "WxException [errCode=" + errCode + ", errMsg=" + errMsg
				+ ", ex=" + ex + "]";
	}


}
