package com.yzq.blog.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yzq.blog.web.service.CommentGrpcClientService;

@Controller
@RequestMapping(value = "comment")
public class CommentController extends AbstractController {

	@Autowired
	private CommentGrpcClientService commentGrpcClientService;

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String doRegister(String blogNo, String content, HttpSession session) {
		String userNo = getSessionUserNo(session);
		commentGrpcClientService.addComment(blogNo, userNo, content);
		return "redirect:/blog/" + blogNo;
	}

}
