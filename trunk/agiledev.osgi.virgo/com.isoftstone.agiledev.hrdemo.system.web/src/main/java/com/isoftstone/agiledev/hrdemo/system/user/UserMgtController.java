package com.isoftstone.agiledev.hrdemo.system.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isoftstone.agiledev.hrdemo.system.app.user.IUserManager;

@Controller
public class UserMgtController {
	@Resource
	private IUserManager userManager;
	
	@RequestMapping
	public String showUsers() {
		return "redirect:/system/test.html";
	}
}
