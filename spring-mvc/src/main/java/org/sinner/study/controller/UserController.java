package org.sinner.study.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/user")
@SessionAttributes("loginUser")
public class UserController {

	@RequestMapping("/json")
	public String test(ModelMap mm){
		List<User> users = new ArrayList<User>();
		users.add(new User("1","zhangsan","zhangsan"));
		users.add(new User("2","lisi","lisi"));
		users.add(new User("3","wangwu","wangwu"));
		mm.addAttribute("result", users);
		return "result";
	}
	@RequestMapping("/result")
	public String create(@ModelAttribute("user") User u,ModelMap mm){
		mm.addAttribute("result",new Result(true,"create success!"));
		return "result";
	}
	@RequestMapping("/list")
	public String testList(ModelMap mm){
		List<User> users = new ArrayList<User>();
		users.add(new User("1","zhangsan","zhangsan"));
		users.add(new User("2","lisi","lisi"));
		users.add(new User("3","wangwu","wangwu"));
		users.add((User) mm.get("loginUser"));
		mm.addAttribute("datagrid-json", users);
		return "datagridjson";
	}

	@RequestMapping("login")
	public String login(@ModelAttribute("loginUser") User user){
		return "redirect:/user/list";
	}
	@RequestMapping("tologin")
	public ModelAndView login(ModelAndView mv){
		mv.setViewName("login");
		return mv;
	}
	@RequestMapping("create")
	public ModelAndView create(@ModelAttribute("loginUser") User user,ModelAndView mv){
		mv.setViewName("success");
		return mv;
	}
	
	@RequestMapping("register")
	public ModelAndView register(ModelAndView mv){
		mv.setViewName("register");
		return mv;
	}
}
