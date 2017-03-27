package com.yzq.blog.comment.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzq.blog.comment.dao.CommentDAO;
import com.yzq.blog.pojo.dbo.Comment;
import com.yzq.blog.util.UniqueUtil;

@Service
public class CommentService {

	@Autowired
	private CommentDAO commentDAO;

	public List<Comment> queryByBlogNo(String blogNo) {
		return this.commentDAO.selectListByBlogNo(blogNo);
	}

	public boolean addComment(String blogNo, String userNo, String content) {
		Comment comment  =buildComment( blogNo,  userNo,  content);
		commentDAO.insert(comment);
		return true;
	}
	
	public Comment buildComment(String blogNo, String userNo, String content){
		Comment comment  =new Comment();
		comment.setBlogNo(blogNo);
		comment.setBusNo(UniqueUtil.uuidFreshUpperCase());
		comment.setContent(content);
		comment.setCreateTime(new Date());
		comment.setUserNo(userNo);
		return comment;
	}
}
