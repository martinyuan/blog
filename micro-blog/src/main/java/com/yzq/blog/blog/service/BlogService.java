package com.yzq.blog.blog.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzq.blog.blog.dao.BlogDAO;
import com.yzq.blog.pojo.dbo.Blog;
import com.yzq.blog.util.UniqueUtil;

@Service
public class BlogService {

	@Autowired
	private BlogDAO blogDAO;
	
	public List<Blog> queryLastBlogs(int size){
		return blogDAO.selectListOrderById(size);
	}

	public Blog queryByBusNo(String busNo) {
		return blogDAO.selectOneByBusNo(busNo);
	}

	public boolean publish(String userNo, String title, String content) {
		Blog blog = buildBlog( userNo,  title,  content);
		System.out.println("userNo:"+userNo);
		System.out.println("title:"+title);
		System.out.println("content:"+content);
		blogDAO.insert(blog);
		return true;
	}
	
	public Blog buildBlog(String userNo, String title, String content){
		Blog blog = new Blog();
		blog.setBusNo(UniqueUtil.uuidFreshUpperCase());
		blog.setContent(content);
		blog.setCreateTime(new Date());
		blog.setTitle(title);
		blog.setUpdateTime(new Date());
		blog.setUserNo(userNo);
		return blog;
	}
}
