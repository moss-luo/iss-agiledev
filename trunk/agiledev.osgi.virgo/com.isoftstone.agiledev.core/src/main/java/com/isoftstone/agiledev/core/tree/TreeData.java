package com.isoftstone.agiledev.core.tree;

import java.util.List;

import com.isoftstone.agiledev.core.tree.Node;

public class TreeData {
	private List<Node> nodes;
	
	public TreeData(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
}
