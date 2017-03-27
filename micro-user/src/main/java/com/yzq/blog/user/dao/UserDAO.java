package com.yzq.blog.user.dao;

import org.springframework.stereotype.Repository;

import com.yzq.blog.pojo.dbo.User;

@Repository
public class UserDAO extends AbstractDAO {

	@Override
	String getNameSpace() {
		return "User";
	}

	public User selectOneByName(String name) {
		return this.sqlSessionTemplate.selectOne(getNameSpace() + ".selectOneByName", name);
	}

	public void insert(User user) {
		this.sqlSessionTemplate.insert(getNameSpace() + ".insert", user);
	}

	public User selectOneByNo(String userNo) {
		return this.sqlSessionTemplate.selectOne(getNameSpace() + ".selectOneByNo", userNo);
	}

}
