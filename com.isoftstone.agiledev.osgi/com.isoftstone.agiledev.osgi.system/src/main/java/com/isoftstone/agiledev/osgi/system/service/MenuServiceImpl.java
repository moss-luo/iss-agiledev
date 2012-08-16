package com.isoftstone.agiledev.osgi.system.service;

import java.util.List;

import javax.annotation.Resource;

import com.isoftstone.agiledev.osgi.core.service.Service;
import com.isoftstone.agiledev.osgi.core.web.navigate.TreeDataProvider;
import com.isoftstone.agiledev.osgi.core.web.navigate.TreeSupport;
import com.isoftstone.agiledev.osgi.db.dao.IBaseDao;
@Service(name="1")
public class MenuServiceImpl implements TreeDataProvider{

	@Resource
	private IBaseDao dao = null;

	@SuppressWarnings("unchecked")
	@Override
	public List<TreeSupport> getNodes(String parentId) {
		if(parentId.equals("0"))
			return dao.list("com.isoftstone.agiledev.osgi.system.domain.SystemTreeMapper.queryRootMenu", null);
		return dao.list("com.isoftstone.agiledev.osgi.system.domain.SystemTreeMapper.queryMenu", parentId);
	}

}
