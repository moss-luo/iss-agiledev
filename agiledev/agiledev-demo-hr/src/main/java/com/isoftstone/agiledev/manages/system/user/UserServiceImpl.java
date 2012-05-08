package com.isoftstone.agiledev.manages.system.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.isoftstone.agiledev.actions.system.user.User;
import com.isoftstone.agiledev.manages.BaseServiceImpl;

public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{

	/* (non-Javadoc)
	 * @see com.isoftstone.agiledev.manages.system.user.UserService#login(com.isoftstone.agiledev.actions.system.user.User)
	 */
	@Override
	public User login(User u){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userid", u.getUserId());
		params.put("password", u.getPassword());
		List<?> list=dao.list(params, "com.isoftstone.agiledev.hr.mapper.UserMapper.login");
	    if(list.size()==0){
	    	return null;
	    }
		return (User) list.get(0);
	}
}
