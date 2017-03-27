package com.yzq.blog.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public abstract class AbstractController {

	/**
	 * session用户保存key
	 */
	protected static final String SESSION_USER_KEY = "SESSION_USER";

	/**
	 * 错误信息类型
	 */
	protected static final String MSG_TYPE_ERROR = "error";

	/**
	 * 提示信息类型
	 */
	protected static final String MSG_TYPE_INFO = "info";

	protected String getSessionUserNo(HttpSession session){
		Object obj = session.getAttribute(SESSION_USER_KEY);
		return (String) obj;
	}
	protected void addErrorMessage(Model model, String error) {
		model.addAttribute("msgType", MSG_TYPE_ERROR);
		model.addAttribute("message", error);
	}

	protected void addInfoMessage(Model model, String message) {
		model.addAttribute("msgType", MSG_TYPE_INFO);
		model.addAttribute("message", message);
	}
}
