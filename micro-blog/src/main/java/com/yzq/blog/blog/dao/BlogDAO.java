package com.yzq.blog.blog.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yzq.blog.pojo.dbo.Blog;

@Repository
public class BlogDAO extends AbstractDAO {

	@Override
	String getNameSpace() {
		return "Blog";
	}

	public List<Blog> selectListOrderById(int size) {
		return this.sqlSessionTemplate.selectList(getNameSpace() + ".selectListOrderById", size);
	}

	public Blog selectOneByBusNo(String busNo) {
		return this.sqlSessionTemplate.selectOne(getNameSpace() + ".selectOneByBusNo", busNo);
	}

	public void insert(Blog blog) {
		this.sqlSessionTemplate.insert(getNameSpace() + ".insert", blog);
	}

}
