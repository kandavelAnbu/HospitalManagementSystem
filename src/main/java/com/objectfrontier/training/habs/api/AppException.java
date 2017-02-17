package com.objectfrontier.training.habs.api;

public class AppException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public AppException(AppErrorCode errorcode) {
		super(errorcode.toString());
	}
	
	public AppException(Throwable cause) {
		super(AppErrorCode.UNKNOWN_ERROR.toString(), cause);
	}
}
