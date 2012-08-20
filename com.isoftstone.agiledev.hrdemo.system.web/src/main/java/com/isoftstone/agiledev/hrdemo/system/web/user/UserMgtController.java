package com.isoftstone.agiledev.hrdemo.system.web.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.isoftstone.agiledev.core.OperationPrompt;
import com.isoftstone.agiledev.core.init.InitializeModel;
import com.isoftstone.agiledev.core.init.Initializeable;
import com.isoftstone.agiledev.core.query.QueryByMapExecutor;
import com.isoftstone.agiledev.core.query.QueryParameters;
import com.isoftstone.agiledev.core.query.QueryResult;
import com.isoftstone.agiledev.core.query.QueryTemplate;
import com.isoftstone.agiledev.core.query.SummaryProvider;
import com.isoftstone.agiledev.hrdemo.system.app.user.IUserManager;
import com.isoftstone.agiledev.hrdemo.system.app.user.User;

@Controller
public class UserMgtController implements Initializeable, SummaryProvider {
	@Resource
	private IUserManager userManager;
	
	@Resource
	private QueryTemplate queryTemplate;

	@Override
	@RequestMapping
	public InitializeModel init() {
		return new InitializeModel(new Class[]{User.class});
	}
	
	@RequestMapping
	public QueryResult<User> list(@RequestParam(value="name", required=false) final String name,
				QueryParameters queryParameters){
		return queryTemplate.queryByMap(this, queryParameters, new QueryByMapExecutor<User>() {
			@Override
			public List<User> execute(Map<String, Object> parameters) {
				if (name != null) {
					parameters.put("name", name);
				}
				
				return userManager.list(parameters);
			}
		});
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
	public OperationPrompt remove(@RequestParam(value="id", required=false) int[] ids){
		try {
			this.userManager.remove(ids);
			
			return new OperationPrompt("删除成功", true);
		} catch (Exception e) {
			e.printStackTrace();
			return new OperationPrompt("删除失败", false);
		}

	}

	@Override
	public int getTotal() {
		return userManager.getTotal();
	}

}
