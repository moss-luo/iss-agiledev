package com.isoftstone.agiledev.hrdemo.system.web.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isoftstone.agiledev.core.OperationPrompt;
import com.isoftstone.agiledev.core.datagrid.GridData;
import com.isoftstone.agiledev.hrdemo.system.app.user.IUserManager;
import com.isoftstone.agiledev.hrdemo.system.app.user.User;

@Controller
public class UserMgtController {
	@Resource
	private IUserManager userManager;
	
	@RequestMapping
	public String showUsers() {
		return "redirect:/system/test.html";
	}

	@RequestMapping
	public GridData list(User user){
		return new GridData(4,userManager.list(user));
	}
	@RequestMapping
	public OperationPrompt add(User user){
		OperationPrompt r = null;
		try {
			this.userManager.add(user);
			r = new OperationPrompt("添加成功", true);
		} catch (Exception e) {
			r = new OperationPrompt("添加失败", false);
			e.printStackTrace();
		}
		return r;
	}
	@RequestMapping
	public OperationPrompt update(User user){
		OperationPrompt r = null;
		try {
			this.userManager.update(user);
			r = new OperationPrompt("修改成功", true);
		} catch (Exception e) {
			r = new OperationPrompt("修改失败", false);
			e.printStackTrace();
		}
		return r;
	}
	@RequestMapping
	public OperationPrompt remove(String id){
		OperationPrompt r = null;
		try {
			this.userManager.remove(id);
			r = new OperationPrompt("删除成功", true);
		} catch (Exception e) {
			r = new OperationPrompt("删除失败", false);
			e.printStackTrace();
		}
		return r;
	}
}
