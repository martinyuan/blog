package com.yzq.blog.util;

import org.apache.commons.lang.StringUtils;

import com.yzq.blog.exception.ServiceException;

/**
 * 服务检查工具类
 * 
 * @author YuanZhiQiang
 *
 */
public abstract class CheckServiceUtil {

	private CheckServiceUtil() {
	}

	public static void mustNull(Object obj, String error) {
		if (obj != null) {
			throw new ServiceException(error);
		}
	}

	public static void mustNotNull(Object obj, String error) {
		if (obj == null) {
			throw new ServiceException(error);
		}
	}

	public static void mustEqual(String s1, String s2, String error) {
		if (!StringUtils.equals(s1, s2)) {
			throw new ServiceException(error);
		}
	}
}
