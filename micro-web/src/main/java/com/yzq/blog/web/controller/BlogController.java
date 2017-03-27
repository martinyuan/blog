package com.yzq.blog.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yzq.blog.pojo.vo.BlogShowComment;
import com.yzq.blog.pojo.vo.BlogShowVo;
import com.yzq.blog.web.service.BlogGrpcClientService;
import com.yzq.blog.web.service.CommentGrpcClientService;

@Controller
@RequestMapping(value = "blog")
public class BlogController extends AbstractController {
	
	@Autowired
	private BlogGrpcClientService blogGrpcClientService;
	
	@Autowired
	private CommentGrpcClientService commentGrpcClientService;
	
	@RequestMapping(value = "/{blogNo}", method = RequestMethod.GET)
	public String doRegister(@PathVariable String blogNo, Model model,HttpSession session) {
		String userNo =getSessionUserNo(session);
		BlogShowVo blog = blogGrpcClientService.queryByBusNo(blogNo);
		List<BlogShowComment> comments =commentGrpcClientService.queryByBlogNo(blogNo);
		model.addAttribute("blog", blog);
		model.addAttribute("comments", comments);
		model.addAttribute("userNo", userNo);
		return "blog/show";
	}


	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String register() {
		return "blog/form";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(String title,String content,HttpSession session) {
		String userNo =getSessionUserNo(session);
		blogGrpcClientService.publish(userNo,title,content);
		return "redirect:/index";
	}
}
