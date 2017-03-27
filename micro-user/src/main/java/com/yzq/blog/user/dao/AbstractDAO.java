package com.yzq.blog.user.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractDAO {

	@Autowired
	protected SqlSessionTemplate sqlSessionTemplate;

	abstract String getNameSpace();
}
