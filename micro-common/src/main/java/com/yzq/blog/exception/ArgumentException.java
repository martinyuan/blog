package com.yzq.blog.exception;

/**
 * 参数校验异常
 * 
 * @author YuanZhiQiang
 *
 */
public class ArgumentException extends RuntimeException {
	private static final long serialVersionUID = 4160587175471324954L;

	public ArgumentException(String message) {
		super(message);
	}

}
