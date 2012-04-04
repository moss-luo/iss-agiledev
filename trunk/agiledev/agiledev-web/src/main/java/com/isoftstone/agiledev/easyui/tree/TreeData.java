package com.isoftstone.agiledev.easyui.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TreeData {
	private List<Node> topLevelNodes;
	private Stack<Node> ancestors;
	private Node current;
	
	public TreeData() {
		ancestors = new Stack<Node>();
		topLevelNodes = new ArrayList<Node>();
	}
	
	public TreeData hierarchyUp() {
		if (!ancestors.isEmpty())
			ancestors.pop();
		
		return this;
	}
	
	public TreeData hierarchyDown() {
		if (current == null)
			return null;
		
		if (ancestors.isEmpty() || current != ancestors.peek()) {
			ancestors.push(current);
		}
		
		return this;
	}
	
	public TreeData nextNode(Node node) {
		if (ancestors.isEmpty()) {
			topLevelNodes.add(node);
		} else {
			if (!ancestors.isEmpty()) {
				ancestors.peek().getChildren().add(node);
			}
		}
		
		current = node;
		return this;
	}
	
	public List<Node> getData() {
		return topLevelNodes;
	}
	@Override
	public String toString() {
		if (topLevelNodes.size() == 0)
			return "";
		
		StringBuilder sb = new StringBuilder();
		Integer currentLevel = 0;
		for (Node node : topLevelNodes) {
			print(node, sb, currentLevel);
		}
		
		if (sb.charAt(sb.length() - 1) == '-') {
			sb.deleteCharAt(sb.length() - 1);
		}
		
		return sb.toString();
	}

	private void print(Node node, StringBuilder sb, Integer currentLevel) {
		if (node.getId() != null) {
			sb.append(String.format("node(level: %d, id: %s)", currentLevel, node.getId()));
		} else if (node.getText() != null) {
			sb.append(String.format("node(level: %d, text: %s)", currentLevel, node.getText()));
		} else {
			sb.append(String.format("node(level: %d, anonymous)", currentLevel));
		}
		sb.append("-");
		
		currentLevel = currentLevel.intValue() + 1;
		for (Node child : node.getChildren()) {
			print(child, sb, currentLevel);
		}
	}
}
