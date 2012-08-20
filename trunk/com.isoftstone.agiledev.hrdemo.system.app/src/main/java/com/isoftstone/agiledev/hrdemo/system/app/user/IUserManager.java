package com.isoftstone.agiledev.hrdemo.system.app.user;

import java.util.List;
import java.util.Map;


public interface IUserManager {
	List<User> list(Map<String, Object> queryParameters);
	void add(User user);
	void update(User user);
	void remove(int id);
	void remove(int[] ids);
	User findById(int id);
	int getTotal();
}
