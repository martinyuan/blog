package com.yzq.blog.user.service;

import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzq.blog.pojo.dbo.User;
import com.yzq.blog.user.dao.UserDAO;
import com.yzq.blog.util.CheckServiceUtil;
import com.yzq.blog.util.Md5Util;
import com.yzq.blog.util.UniqueUtil;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;

	public boolean doRegister(String name, String password) {
		User user = userDAO.selectOneByName(name);
		CheckServiceUtil.mustNull(user, "用户名已存在");
		user = buildUser(name, password);
		userDAO.insert(user);
		return true;
	}

	public String checklogin(String name, String password) {
		User user = userDAO.selectOneByName(name);
		CheckServiceUtil.mustNotNull(user, "用户名或密码错误！");
		String dbPwd = user.getPassword();
		String inputPwd = encryptPassword(password, user.getSaltNo());
		CheckServiceUtil.mustEqual(dbPwd, inputPwd, "用户名或密码错误！");
		return user.getBusNo();
	}

	private User buildUser(String name, String password) {
		User u = new User();
		u.setBusNo(UniqueUtil.uuidFreshUpperCase());
		u.setName(name);
		u.setRegTime(new Date());
		String saltNo = buildSaltNo();
		u.setSaltNo(saltNo);
		u.setPassword(encryptPassword(password, saltNo));
		u.setCreateTime(new Date());
		u.setUpdateTime(new Date());
		return u;
	}

	private String buildSaltNo() {
		String letters = RandomStringUtils.randomAlphabetic(10);
		return StringUtils.upperCase(letters);
	}

	private String encryptPassword(String password, String saltNo) {
		return Md5Util.MD5(password + saltNo);
	}

	public User getUserInfo(String userNo) {
		return userDAO.selectOneByNo(userNo);
	}

}
