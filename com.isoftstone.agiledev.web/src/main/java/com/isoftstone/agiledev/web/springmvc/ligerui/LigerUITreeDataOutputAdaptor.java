package com.isoftstone.agiledev.web.springmvc.ligerui;

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
import com.isoftstone.agiledev.web.ligerui.LigerUITreeNode;
import com.isoftstone.agiledev.web.springmvc.DataOutputAdaptor;

public class LigerUITreeDataOutputAdaptor implements DataOutputAdaptor {
	
	private String type;
	private ObjectMapper objectMapper = new ObjectMapper();

	public LigerUITreeDataOutputAdaptor() {
		this.type="com.isoftstone.agiledev.core.tree.TreeData";
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
	
	private Object filterTreeData(Object treeData,String contextPath){
		if(!check(treeData))throw new RuntimeException("format ligerui treeData error:"+treeData);
		return convertToLigerUITreeNode((TreeData)treeData,contextPath);
	}
	
	private List<LigerUITreeNode> convertToLigerUITreeNode(TreeData treeData,String contextPath){
		List<Node> nodes = treeData.getNodes();
		List<LigerUITreeNode> ligerNodes = new ArrayList<LigerUITreeNode>();
		
		for(Node node:nodes){
			ligerNodes.add(convertToLigerUITreeNode(node, contextPath));
		}
		
		return ligerNodes;
	}
	
	private LigerUITreeNode convertToLigerUITreeNode(Node node,String contextPath){
		StringBuilder builder = new StringBuilder();
		builder.append(contextPath).append("/").append(node.getUrl());
		
		LigerUITreeNode ligerNode = new LigerUITreeNode();
		ligerNode.setText(node.getText());
		if(!node.getHasChild()){
			ligerNode.setUrl(builder.toString());
		}
		ligerNode.setIsexpend(node.getHasChild());
		
		if(node.getChildren()!=null && node.getChildren().size()>0){
			if(ligerNode.getChildren() == null)ligerNode.setChildren(new ArrayList<LigerUITreeNode>());
			
			for(Node childNode:node.getChildren()){
				ligerNode.getChildren().add(convertToLigerUITreeNode(childNode, contextPath));
			}
		}
		
		return ligerNode;	
	}

	@Override
	public boolean check(Object o) {
		return o instanceof TreeData;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getType() {
		return this.type;
	}

	
}
