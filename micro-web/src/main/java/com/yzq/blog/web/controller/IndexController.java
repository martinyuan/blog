package com.yzq.blog.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yzq.blog.pojo.dbo.Blog;
import com.yzq.blog.pojo.vo.IndexUser;
import com.yzq.blog.web.service.BlogGrpcClientService;
import com.yzq.blog.web.service.UserGrpcClientService;

@Controller
public class IndexController extends AbstractController {

	private static final int BLOG_SHOW_SIZE = 10;
	@Autowired
	private UserGrpcClientService userGrpcClientService;

	@Autowired
	private BlogGrpcClientService blogGrpcClientService;

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(HttpSession session, Model model) {
		String userNo =getSessionUserNo(session);
		if (StringUtils.isNotBlank(userNo)) {
			IndexUser user = userGrpcClientService.getUserInfoByUserNo(userNo);
			model.addAttribute("user", user);
		}
		List<Blog> blogs = blogGrpcClientService.queryLast(BLOG_SHOW_SIZE);
		model.addAttribute("blogs", blogs);
		return "index";
	}

}
