package com.isoftstone.agiledev.easyui.tree;

import java.util.List;

public interface TreeDataProvider {
	List<Node> getNodes(String parentId);
}
