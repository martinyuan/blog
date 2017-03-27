package com.yzq.blog.pojo.vo;

import java.io.Serializable;

public class IndexUser implements Serializable {

	private static final long serialVersionUID = 1634587379730600363L;

	private String userNo;
	private String name;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
