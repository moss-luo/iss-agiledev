package com.isoftstone.agiledev.manages.system.permision;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.isoftstone.agiledev.actions.system.permision.Permision;
import com.isoftstone.agiledev.manages.BaseServiceImpl;
@Service
public class PermisionManagerImpl extends BaseServiceImpl<Permision> implements PermisionManager{

	
	@Override
	public void remove(Serializable id, Permision t) {
		super.remove(id, t);
		dao.remove(id, "com.isoftstone.agiledev.hr.mapper.PermisionMapper.removeMiddle");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Permision> findByPid(String pid) {
		Map<String,Object> p = new HashMap<String,Object>();
		p.put("id", pid);
		return dao.list(p, "com.isoftstone.agiledev.hr.mapper.PermisionMapper.findByPid");
	}

}
