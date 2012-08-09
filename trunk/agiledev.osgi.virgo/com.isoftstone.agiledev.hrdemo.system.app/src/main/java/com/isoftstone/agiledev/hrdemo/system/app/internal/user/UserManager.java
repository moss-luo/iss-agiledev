package com.isoftstone.agiledev.hrdemo.system.app.internal.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.isoftstone.agiledev.hrdemo.system.app.user.IUserManager;
import com.isoftstone.agiledev.hrdemo.system.app.user.User;
@Service
public class UserManager implements IUserManager {

	private static List<User> users = null;
	private static int id;
	static{
		users = new ArrayList<User>();
		users.add(new User("1", "zhangsan", "123"));
		users.add(new User("2", "lisi", "123"));
		users.add(new User("3", "wangwu", "123"));
		users.add(new User("4", "zhaoliu", "123"));
		id=5;
	}
	
	@Override
	public List<User> list(User user) {
		if(user==null || user.getName()==null || "".equals(user.getName()))return users;
		List<User> temp = new ArrayList<User>();
		for (User u : users) {
			if(u.getName().contains(user.getName()))
				temp.add(u);
		}
		return temp;
	}

	@Override
	public void add(User user) {
		user.setId((++id)+"");
		users.add(user);
	}

	@Override
	public void update(User user) {
		for (User u : users) {
			if(u.getId().equals(user.getId())){
				u.setName(user.getName());
				u.setPassword(user.getPassword());
			}
		}
	}

	@Override
	public void remove(String id) {
		String[] ids = id.split(",");
		List<User> temp = new ArrayList<User>();
		for (String s : ids) {
			for (User u : users) {
				if(u.getId().equals(s)){
					temp.add(u);
				}
			}
		}
		users.removeAll(temp);
	}
}
