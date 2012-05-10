package com.isoftstone.agiledev.easyui.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.isoftstone.agiledev.ligerui.tree.TreeSupport;

public class TreeData {
	private List<TreeSupport> topLevelNodes;
	private Stack<TreeSupport> ancestors;
	private TreeSupport current;
	
	public TreeData() {
		ancestors = new Stack<TreeSupport>();
		topLevelNodes = new ArrayList<TreeSupport>();
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
	
	public TreeData nextNode(TreeSupport node) {
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
	
	public List<TreeSupport> getData() {
		return topLevelNodes;
	}
	@Override
	public String toString() {
		if (topLevelNodes.size() == 0)
			return "";
		
		StringBuilder sb = new StringBuilder();
		Integer currentLevel = 0;
		for (TreeSupport node : topLevelNodes) {
			print(node, sb, currentLevel);
		}
		
		if (sb.charAt(sb.length() - 1) == '-') {
			sb.deleteCharAt(sb.length() - 1);
		}
		
		return sb.toString();
	}

	private void print(TreeSupport node, StringBuilder sb, Integer currentLevel) {
		if (node.getId() != null) {
			sb.append(String.format("node(level: %d, id: %s)", currentLevel, node.getId()));
		} else if (node.getText() != null) {
			sb.append(String.format("node(level: %d, text: %s)", currentLevel, node.getText()));
		} else {
			sb.append(String.format("node(level: %d, anonymous)", currentLevel));
		}
		sb.append("-");
		
		currentLevel = currentLevel.intValue() + 1;
		for (TreeSupport child : node.getChildren()) {
			print(child, sb, currentLevel);
		}
	}
}
