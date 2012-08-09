package com.isoftstone.agiledev.core.tree;

import java.util.List;

public interface TreeDataProvider {
	List<Node> getNodes(String parentId);
}
