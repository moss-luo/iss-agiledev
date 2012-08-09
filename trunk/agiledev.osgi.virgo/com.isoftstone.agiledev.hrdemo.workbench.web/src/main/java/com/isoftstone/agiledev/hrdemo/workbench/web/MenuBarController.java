package com.isoftstone.agiledev.hrdemo.workbench.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.isoftstone.agiledev.core.tree.Node;
import com.isoftstone.agiledev.core.tree.TreeData;
import com.isoftstone.agiledev.core.tree.TreeDataProvider;
import com.isoftstone.agiledev.hrdemo.workbench.app.IMenuManager;
import com.isoftstone.agiledev.hrdemo.workbench.app.Menu;

@Controller
public class MenuBarController implements TreeDataProvider {
	private List<Node> nodes;
	
	@Resource
	private IMenuManager menuManager;
	
	@RequestMapping
	public TreeData get(@RequestParam(value="id", required=false) String id) {
		return new TreeData(getNodes(id));
	}
	
	public List<Node> getNodes(String parentId) {
		List<Node> nodes = convertToNodes(menuManager.getMenus(parentId));
		
		if (parentId == null) {
			List<Node> otherNodes = Arrays.asList(new Node[] {
					new Node("1", "分析图表", true),
					new Node("2", "数据维护", true),
					new Node("3", "基础数据", true),
					new Node("4", "统计报表", true)
			});
			nodes.addAll(otherNodes);
			return nodes;
		} else if (parentId.equals("0")) {
			return Arrays.asList(new Node[] {
					new Node("0-1", "<a hh='system/user.html'>用户管理</a>"),
					new Node("0-2", "<a hh='system/module.html'>模块管理</a>"),
					new Node("0-3", "<a hh='system/role.html'>角色管理</a>")
			});
		} else if (parentId.equals("1")) {
			return Arrays.asList(new Node[] {
					new Node("1-1", "部门培训分析"),
					new Node("1-2", "职级培训分析")
			});
		} else if (parentId.equals("2")) {
			return Arrays.asList(new Node[] {
					new Node("2-1", "培训数据")
			});
		} else if(parentId.equals("3")){
			return Arrays.asList(new Node[] {
					new Node("3-1", "<a hh='basedata/department.html'>部门管理</a>"),
					new Node("3-2", "培训类别"),
					new Node("3-3", "职员管理"),
					new Node("3-4", "培训项目"),
					new Node("3-5", "<a hh='basedata/level.html'>职级管理</a>")
			});

		} else if(parentId.equals("4")){
			return Arrays.asList(new Node[] {
					new Node("4-2", "职级培训统计")	
			});
		} else {
			return nodes;
		}
	}
	
	private List<Node> convertToNodes(List<Menu> menus) {
		List<Node> nodes = new ArrayList<Node>();
		if (menus == null || menus.size() == 0)
			return nodes;
		
		for (Menu menu : menus) {
			Node node = new Node(menu.getId(), menu.getText());
			node.setUrl(menu.getAction());
			node.setHasChild(menu.getHasChild());
			
			nodes.add(node);
		}
		
		return nodes;
	}

	public List<Node> getNodes() {
		return nodes;
	}
}
