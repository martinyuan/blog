package com.yzq.blog.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yzq.blog.exception.ArgumentException;
import com.yzq.blog.exception.ServiceException;
import com.yzq.blog.util.CheckArgumentUtil;
import com.yzq.blog.web.service.UserGrpcClientService;

@Controller
@RequestMapping(value = "user")
public class UserController extends AbstractController {

	@Autowired
	private UserGrpcClientService userGrpcClientService;

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register() {
		return "user/register";
	}

	@RequestMapping(value = "doRegister", method = RequestMethod.POST)
	public String doRegister(String name, String password, Model model) {
		try {
			CheckArgumentUtil.mustNotBlank(name, "请输入名称");
			CheckArgumentUtil.mustNotBlank(password, "请输入密码");
			boolean result = userGrpcClientService.doRegister(name, password);
			if (result) {
				addInfoMessage(model, "注册成功");
			}
		} catch (ArgumentException e) {
			addErrorMessage(model, e.getMessage());
		} catch (ServiceException e) {
			addErrorMessage(model, e.getMessage());
		}
		return "user/register";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	@RequestMapping(value = "doLogin", method = RequestMethod.POST)
	public String doLogin(String name, String password, Model model, HttpSession session) {
		try {
			CheckArgumentUtil.mustNotBlank(name, "请输入名称");
			CheckArgumentUtil.mustNotBlank(password, "请输入密码");
			String userNo = userGrpcClientService.checklogin(name, password);
			session.setAttribute(SESSION_USER_KEY, userNo);
			addInfoMessage(model, "登录成功");
		} catch (ArgumentException e) {
			addErrorMessage(model, e.getMessage());
		} catch (ServiceException e) {
			addErrorMessage(model, e.getMessage());
		}
		return "redirect:/index";
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute(SESSION_USER_KEY);
		return "user/login";
	}
}
