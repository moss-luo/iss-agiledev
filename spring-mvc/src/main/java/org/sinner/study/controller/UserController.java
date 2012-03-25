package org.sinner.study.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.sinner.study.view.SummerProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/user")
@SessionAttributes("loginUser")
public class UserController implements SummerProvider {

	@RequestMapping("/create")
	public String create(@ModelAttribute("user") User u,ModelMap mm,HttpSession session){
		List<User> list = (List<User>) session.getAttribute("users");
		list.add(u);
		session.setAttribute("users", list);
		mm.addAttribute("result",new Result(true,"create success"));
		return "json";
	}
	@RequestMapping("/list")
	public String testList(ModelMap mm,HttpSession session){
		if(session.getAttribute("users")!=null){
			mm.addAttribute("datagrid-json",session.getAttribute("users"));
			return "datagridjson";
		}
		List<User> users = new ArrayList<User>();
		users.add(new User("1","zhangsan","zhangsan"));
		users.add(new User("2","lisi","lisi"));
		users.add(new User("3","wangwu","wangwu"));
//		users.add((User) mm.get("loginUser"));
		mm.addAttribute("datagrid-json", users);
		session.setAttribute("users", users);
		return "datagridjson";
	}

	@RequestMapping("login")
	public String login(User loginUser){
		if(loginUser.getName()!=null && loginUser.getPassword()!=null){
			if(loginUser.getName().equals("admin") && loginUser.getPassword().equals("admin")){
				return "redirect:/user/forward.do?name=success";
			}
		}
		return "redirect:/user/forward.do?name=login";
	}
	@RequestMapping("forward")
	public ModelAndView forward(ModelAndView mv,@RequestParam String name){
		mv.setViewName(name);
		return mv;
	}
	public int getTotal() {
		return 100;
	}
}
