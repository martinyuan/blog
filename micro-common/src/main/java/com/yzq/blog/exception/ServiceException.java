package com.yzq.blog.exception;

/**
 * 服务异常
 * 
 * @author YuanZhiQiang
 *
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -2784721094286364894L;

	public ServiceException(String message) {
		super(message);
	}

}
