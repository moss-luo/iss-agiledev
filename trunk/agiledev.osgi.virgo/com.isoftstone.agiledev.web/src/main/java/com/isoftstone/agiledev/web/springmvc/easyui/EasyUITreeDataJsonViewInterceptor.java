package com.isoftstone.agiledev.web.springmvc.easyui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.isoftstone.agiledev.core.tree.TreeData;
import com.isoftstone.agiledev.web.easyui.EasyUITreeNode;

public class EasyUITreeDataJsonViewInterceptor extends HandlerInterceptorAdapter {
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		if (modelAndView != null) {
			Map<String, Object> model = modelAndView.getModel();
			Map<String, Object> allTreeData = new HashMap<String, Object>();
			for (Entry<String, Object> entry : model.entrySet()) {
				if (entry.getValue() instanceof TreeData) {
					List<EasyUITreeNode> nativeTreeData = convertToEasyUITreeData(
							(TreeData)entry.getValue());
					allTreeData.put(entry.getKey(), nativeTreeData);
				}
			}
			
			if (model.size() == 1 && allTreeData.size() == 1) {
				model.clear();
			}
		}
		
		super.postHandle(request, response, handler, modelAndView);
	}

	private List<EasyUITreeNode> convertToEasyUITreeData(TreeData treeData) {
		// TODO Auto-generated method stub
		return null;
	}
}
