package com.yzq.blog.util;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;

public abstract class UniqueUtil {
	private UniqueUtil() {
	}

	/**
	 * UUID去掉-
	 * 
	 * @return
	 */
	public static String uuidFreshUpperCase() {
		String uuid = UUID.randomUUID().toString();
		String fresh = StringUtils.remove(uuid, '-');
		return StringUtils.upperCase(fresh);
	}
}
