package com.isoftstone.agiledev.hrdemo.system.app.user;

import java.util.List;

public interface IUserManager {
	List<User> list(String name, int start, int end, String orderBy);
	void add(User user);
	void update(User user);
	void remove(int id);
	void remove(int[] ids);
	User findById(int id);
	int getTotal(String name);
}
