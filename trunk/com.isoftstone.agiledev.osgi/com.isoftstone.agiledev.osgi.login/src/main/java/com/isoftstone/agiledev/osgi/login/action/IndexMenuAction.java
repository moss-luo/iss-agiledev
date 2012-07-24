package com.isoftstone.agiledev.osgi.login.action;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import com.isoftstone.agiledev.osgi.core.web.ActionContext;
import com.isoftstone.agiledev.osgi.core.web.annotation.Action;
import com.isoftstone.agiledev.osgi.core.web.annotation.Result;
import com.isoftstone.agiledev.osgi.core.web.annotation.Results;
import com.isoftstone.agiledev.osgi.core.web.navigate.TreeDataProvider;
import com.isoftstone.agiledev.osgi.core.web.navigate.TreeSupport;


@Action(packageName="login",path="index-menu")
@Results({
	@Result(name = "result", type = "json", params = {"root", "nodes"})
})
public class IndexMenuAction implements com.isoftstone.agiledev.osgi.core.web.Action {
	
	private List<TreeSupport> nodes = null;
	private String id=null;
	
	public String execute(){
		BundleContext context = ActionContext.getBundleContext();
		
		try {
			nodes = new ArrayList<TreeSupport>();
			String filter = null;
			if(id!=null){
				filter="(serviceName="+id+")";
			}
			ServiceReference[] sfs = context.getServiceReferences(TreeDataProvider.class.getName(), filter);
			for (ServiceReference sf : sfs) {
				TreeDataProvider provider = (TreeDataProvider)context.getService(sf);
				List<TreeSupport> temp = provider.getNodes(id==null?"0":id);
				nodes.addAll(temp);
			}
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
		
		return "result";
	}

	public List<TreeSupport> getNodes() {
		return nodes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}