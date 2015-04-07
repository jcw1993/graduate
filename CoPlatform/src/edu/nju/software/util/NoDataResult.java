package edu.nju.software.util;

public class NoDataResult {
	private int resultCode;
	
	public NoDataResult() {
		this.resultCode = ResultCode.NORMAL;
	}
	
	public NoDataResult(int resultCode) {
		this.resultCode = resultCode;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
}
