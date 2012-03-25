package com.isoftstone.agiledev.actions.login;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.isoftstone.agiledev.actions.system.permision.Permision;
import com.isoftstone.agiledev.ajax.easyui.tree.Node;
import com.isoftstone.agiledev.ajax.easyui.tree.Node.State;
import com.isoftstone.agiledev.ajax.easyui.tree.TreeData;
import com.isoftstone.agiledev.ajax.easyui.tree.TreeDataProvider;
import com.isoftstone.agiledev.manages.system.permision.PermisionManager;

@Results(
	@Result(name = "result", type = "json", params = {"root", "nodes", "contentType", "text/html"})
)
public class IndexMenuAction implements TreeDataProvider {
	private String id;
	private List<Node> nodes;
	@Resource
	private PermisionManager permisionManager=null;
	public String execute() {
		nodes = getNodes(id);
		return "result";
	}

	@Override
	public List<Node> getNodes(String parentId) {
		List<Permision> ps = permisionManager.findByPid(parentId==null?"0":parentId);
		TreeData treeData = new TreeData();
		for (Permision permision : ps) {
			treeData.nextNode((permision.getHasChild()==null ||permision.getHasChild()==0)
								?new Node(permision.getUid(),"<a hh='"+permision.getUrl()+"'>"+permision.getPermisionName()+"</a>")
								:new Node(permision.getUid(),"<a hh='"+permision.getUrl()+"'>"+permision.getPermisionName()+"</a>",null,State.closed));
		}
		return treeData.getData();
//		if (parentId == null) {
//			return new TreeData().nextNode(new Node("0", "系统管理", State.closed))
//								.nextNode(new Node("1", "分析图表", State.closed))
//								.nextNode(new Node("2", "数据维护", State.closed))
//								.nextNode(new Node("3", "基础数据", State.closed))
//								.nextNode(new Node("4", "统计报表", State.closed))
//								.getData();
//		} else if (parentId.equals("0")) {
//			return new TreeData().nextNode(new Node("0-1", "<a hh='system/user.html'>用户管理</a>"))
//								.nextNode(new Node("0-2", "<a hh='system/module.html'>模块管理</a>"))
//								.nextNode(new Node("0-3", "<a hh='system/role.html'>角色管理</a>"))
//								.getData();
//		} else if (parentId.equals("1")) {
//			return new TreeData().nextNode(new Node("1-1", "部门培训分析"))
//								.nextNode(new Node("1-2", "职级培训分析"))
//								.getData();
//		} else if (parentId.equals("2")) {
//			return new TreeData().nextNode(new Node("2-1", "培训数据"))
//								.getData();
//		} else if(parentId.equals("3")){
//			return new TreeData().nextNode(new Node("3-1", "<a hh='basedata/department.html'>部门管理</a>"))
//								.nextNode(new Node("3-2", "培训类别"))
//								.nextNode(new Node("3-3", "职员管理"))
//								.nextNode(new Node("3-4", "培训项目"))
//								.nextNode(new Node("3-5", "<a hh='basedata/level.html'>职级管理</a>"))
//								.getData();
//		} else if(parentId.equals("4")){
//			return new TreeData().nextNode(new Node("4-1", "部门培训统计"))
//								.nextNode(new Node("4-2", "职级培训统计"))
//								.getData();
//		}
//		else {
//			return null;
//		}
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
}