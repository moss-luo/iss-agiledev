package com.isoftstone.agiledev.hrdemo.system.web.user;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.isoftstone.agiledev.core.OperationPrompt;
import com.isoftstone.agiledev.core.init.InitializeModel;
import com.isoftstone.agiledev.core.init.Initializeable;
import com.isoftstone.agiledev.core.query.QueryExecutor;
import com.isoftstone.agiledev.core.query.QueryParameters;
import com.isoftstone.agiledev.core.query.QueryResult;
import com.isoftstone.agiledev.core.query.QueryTemplate;
import com.isoftstone.agiledev.hrdemo.system.app.user.IUserManager;
import com.isoftstone.agiledev.hrdemo.system.app.user.User;

@Controller
public class UserMgtController implements Initializeable {
	@Resource
	private IUserManager userManager;

	@Override
	@RequestMapping
	public InitializeModel init() {
		return new InitializeModel(new Class[]{User.class});
	}
	
	@RequestMapping
	public QueryResult<User> list(@RequestParam(value="name", required=false) final String name,
				QueryParameters queryParameters){
		return QueryTemplate.query(queryParameters, new QueryExecutor<User>() {
			@Override
			public List<User> getResult(int start, int end, String orderBy) {
				return userManager.list(name, start, end, orderBy);
			}

			@Override
			public int getTotal() {
				return userManager.getTotal(name);
			}
			
		});
	}
	
	@RequestMapping
	public OperationPrompt add(@Valid User user){
		System.out.print("++++++++=====in add method====++++++++++");
		OperationPrompt operPrompt = null;
		try {
			this.userManager.add(user);
			operPrompt = new OperationPrompt("添加成功", true);
		} catch (Exception e) {
			operPrompt = new OperationPrompt("添加失败", false);
			e.printStackTrace();
		}
		
		return operPrompt;
	}
	
	@RequestMapping
	public OperationPrompt update(User user){
		System.out.print("++++++++=====in update method====++++++++++");

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
	public OperationPrompt remove(@RequestParam(value="id", required=false) int[] ids){
		System.out.print("++++++++=====in remove method====++++++++++");

		try {
			this.userManager.remove(ids);
			
			return new OperationPrompt("删除成功", true);
		} catch (Exception e) {
			e.printStackTrace();
			return new OperationPrompt("删除失败", false);
		}

	}

}
