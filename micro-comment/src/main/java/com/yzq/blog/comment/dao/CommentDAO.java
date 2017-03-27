package com.yzq.blog.comment.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yzq.blog.pojo.dbo.Comment;

@Repository
public class CommentDAO extends AbstractDAO {

	@Override
	String getNameSpace() {
		return "Comment";
	}

	public List<Comment> selectListByBlogNo(String blogNo) {
		return this.sqlSessionTemplate.selectList(getNameSpace() + ".selectListByBlogNo", blogNo);
	}

	public void insert(Comment comment) {
		this.sqlSessionTemplate.insert(getNameSpace() + ".insert", comment);
	}

}
