package com.isoftstone.agiledev.ajax.easyui.tree;

import java.util.List;

public interface TreeDataProvider {
	List<Node> getNodes(String parentId);
}
