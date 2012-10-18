package com.isoftstone.agiledev.web.springmvc.easyui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.isoftstone.agiledev.core.tree.Node;
import com.isoftstone.agiledev.core.tree.TreeData;
import com.isoftstone.agiledev.web.easyui.EasyUITreeNode;
import com.isoftstone.agiledev.web.springmvc.DataOutputAdaptor;

public class EasyUITreeDataOutputAdaptor implements DataOutputAdaptor {

	private String type;
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public EasyUITreeDataOutputAdaptor() {
		this.type = "com.isoftstone.agiledev.core.tree.TreeData";
	}
	
	@Override
	public void output(HttpServletRequest request,HttpServletResponse response, Object obj) {
		try {
			JsonGenerator generator =
					objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
			SerializationConfig config = objectMapper.getSerializationConfig();
			config.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
			Object o = this.filterTreeData(obj,request.getContextPath());

			objectMapper.writeValue(generator, o, config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public boolean check(Object obj) {
		return obj instanceof TreeData;
	}

	private Object filterTreeData(Object treeData,String contextPath) {
		if(!check(treeData))throw new RuntimeException("format error:"+treeData);
		return convertToEasyUITreeData((TreeData)treeData,contextPath);
	}

	
	protected List<EasyUITreeNode> convertToEasyUITreeData(TreeData treeData,String contextPath) {
		List<Node> treeNodes = treeData.getNodes();
		List<EasyUITreeNode> easyUITreeNodes = new ArrayList<EasyUITreeNode>();
		for (Node node : treeNodes) {
			easyUITreeNodes.add(convertToEasyUITreeNode(node,contextPath));
		}
		
		return easyUITreeNodes;
	}
	
	protected EasyUITreeNode convertToEasyUITreeNode(Node node,String contextPath) {
		EasyUITreeNode euNode = new EasyUITreeNode();
		euNode.setId(node.getId());
		if (node.getText() != null && node.getUrl() != null) {
			euNode.setText(getHref(node, contextPath));
		} else {
			euNode.setText(node.getText());
		}
		
		euNode.setState(node.hasChild() ? "closed" : "open");
		if (node.getChildren() != null && node.getChildren().size() > 0) {
			if (euNode.getChildren() == null) {
				euNode.setChildren(new ArrayList<EasyUITreeNode>());
			}
			
			for (Node child : node.getChildren()) {
				euNode.getChildren().add(convertToEasyUITreeNode(child, contextPath));
			}
		}
		
		return euNode;
	}
	
	


	private String getHref(Node node, String contextPath) {
		StringBuilder builder = new StringBuilder();
		builder.append("<a hh='").append(contextPath).append('/').
			append(node.getUrl()).append("'>").append(node.getText()).
				append("</a>");
		
		return builder.toString();
	}
	
}
