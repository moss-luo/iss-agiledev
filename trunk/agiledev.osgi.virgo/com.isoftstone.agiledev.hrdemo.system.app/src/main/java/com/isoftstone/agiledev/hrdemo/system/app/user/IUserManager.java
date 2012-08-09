package com.isoftstone.agiledev.hrdemo.system.app.user;

import java.util.List;


public interface IUserManager {
	List<User> list(User user);
	void add(User user);
	void update(User user);
	void remove(String id);
}
