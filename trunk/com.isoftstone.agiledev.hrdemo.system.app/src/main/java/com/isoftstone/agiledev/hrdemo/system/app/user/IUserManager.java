package com.isoftstone.agiledev.hrdemo.system.app.user;

import java.util.List;


public interface IUserManager {
	List<User> list(String name);
	void add(User user);
	void update(User user);
	void remove(int id);
	User findById(int id);
	int getTotal();
}
