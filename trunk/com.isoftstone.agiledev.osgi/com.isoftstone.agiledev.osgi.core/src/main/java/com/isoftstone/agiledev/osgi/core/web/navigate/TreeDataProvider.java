package com.isoftstone.agiledev.osgi.core.web.navigate;

import java.util.List;

public interface TreeDataProvider {
	List<TreeSupport> getNodes(String parentId);
}
