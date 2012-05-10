package com.isoftstone.agiledev.easyui.tree;

import java.util.List;

import com.isoftstone.agiledev.ligerui.tree.TreeSupport;

public interface TreeDataProvider {
	List<TreeSupport> getNodes(String parentId);
}
