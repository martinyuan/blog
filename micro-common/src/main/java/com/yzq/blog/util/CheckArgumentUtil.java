package com.yzq.blog.util;

import org.apache.commons.lang.StringUtils;

import com.yzq.blog.exception.ArgumentException;

/**
 * 参数检查工具类
 * 
 * @author YuanZhiQiang
 *
 */
public abstract class CheckArgumentUtil {

	private CheckArgumentUtil() {
	}

	/**
	 * 参数不为空检查,并返回trim过的字符串
	 * 
	 * @param arg
	 * @param error
	 */
	public static String mustNotBlank(String arg, String error) {
		if (StringUtils.isNotBlank(arg)) {
			return StringUtils.trim(arg);
		}
		throw new ArgumentException(error);
	}

}
