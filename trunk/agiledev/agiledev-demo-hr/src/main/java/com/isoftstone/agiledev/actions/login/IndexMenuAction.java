package com.isoftstone.agiledev.actions.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.isoftstone.agiledev.OperationResult;
import com.isoftstone.agiledev.actions.system.permision.Permision;
import com.isoftstone.agiledev.easyui.tree.Node;
import com.isoftstone.agiledev.easyui.tree.TreeData;
import com.isoftstone.agiledev.easyui.tree.TreeDataProvider;
import com.isoftstone.agiledev.ligerui.tree.TreeSupport;
import com.isoftstone.agiledev.manages.BaseService;
import com.isoftstone.agiledev.manages.system.permision.PermisionManager;
import com.opensymphony.xwork2.ModelDriven;

@Results({
	@Result(name = "result", type = "json", params = {"root", "nodes", "contentType", "text/html"}),
	@Result(name = "r", type = "json", params = {"root", "o", "contentType", "text/html"})
})
public class IndexMenuAction implements TreeDataProvider,ModelDriven<Permision> {
	private String id;
	private List<TreeSupport> nodes;
	@Resource
	private PermisionManager permisionManager=null;
	@Resource(name="baseService")
	private BaseService<Permision> baseService=null;
	private Permision permision = null;
	private OperationResult o = null;
	@Action("create")
	public String create(){
		baseService.save(permision);
		o = new OperationResult(true, "create success!");
		return "r";
	}
	@Action("remove")
	public String remove(){
		baseService.remove(id,new Permision());
		o = new OperationResult(true, "remove success!");
		return "r";
	}
	public String execute() {
		nodes = getNodes(id);
		return "result";
	}

	public OperationResult getO() {
		return o;
	}
	public void setO(OperationResult o) {
		this.o = o;
	}
	@Override
	public List<TreeSupport> getNodes(String parentId) {
		String version = (String) ServletActionContext.getRequest().getSession().getAttribute("version");
		TreeData treeData = new TreeData();
		List<Permision> ps = null;
		if("easyui".equals(version)){
			ps = permisionManager.findByPid(parentId==null?"0":parentId);
			for (Permision p : ps) {
				TreeSupport n = new Node();
				n.setId(p.getUid());
				n.setText(p.getPermisionName());
				n.setUrl(p.getUrl());
				n.hasChild(p.getHasChild()==null ||p.getHasChild()==0);
				treeData.nextNode(n);
			}
		}else{
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("version","ligerui");
			ps = baseService.list(param, new Permision());
			for (Permision p : ps) {
				if(p.getUid().equals("1"))continue;
				TreeSupport n = new com.isoftstone.agiledev.ligerui.tree.Node();
				n.setId(p.getUid());
				n.setParentId(p.getPid());
				n.setText(p.getPermisionName());
				n.setUrl(p.getUrl());
				treeData.nextNode(n);
			}
		}
		return treeData.getData();
//		List<Permision> ps = permisionManager.findByPid(parentId==null?"1":parentId);
//		TreeData treeData = new TreeData();
//		TreeSupport node = null;
//		for (Permision p : ps) {
//			treeData.nextNode((permision.getHasChild()==null ||permision.getHasChild()==0)
//								?new Node(permision.getUid(),"<a hh='"+permision.getUrl()+"'>"+permision.getPermisionName()+"</a>")
//								:new Node(permision.getUid(),"<a hh='"+permision.getUrl()+"'>"+permision.getPermisionName()+"</a>",null,State.closed));
//			if(version!=null){
//				node = new Node();
//				if("ligerui".equals(version))
//					node = new com.isoftstone.agiledev.ligerui.tree.Node();
//			}
//			node.hasChild(p.getHasChild()==null ||p.getHasChild()==0);
//			node.setText(p.getPermisionName());
//			node.setUrl(p.getUrl());
//			node.setId(p.getUid());
//			treeData.nextNode(node);
//		}
//		return treeData.getData();
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
	
	public List<TreeSupport> getNodes() {
		return nodes;
	}
	@Override
	public Permision getModel() {
		if(permision == null) permision = new Permision();
		return permision;
	}
}