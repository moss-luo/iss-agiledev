package com.isoftstone.agiledev.manages.system.role;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.agiledev.actions.system.role.Role;
import com.isoftstone.agiledev.dao.BaseDao;
import com.isoftstone.agiledev.manages.BaseServiceImpl;
@Transactional
public class RoleManagerImpl extends BaseServiceImpl<Role>{

	@Resource
	private BaseDao dao = null;
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> list(Map<String, Object> queryCondition,Role r) {
		this.t = r;
		List<Role> list = dao.list(queryCondition, "com.isoftstone.agiledev.hr.mapper.RoleMapper.selectList");
		for (Role role : list) {
			Map<String,Object> p = new HashMap<String,Object>();
			p.put("roleId", role.getUid());
			List<String> permisions = dao.list(p, "com.isoftstone.agiledev.hr.mapper.RoleMapper.selectMiddleList");
			String[] permisionIds=new String[permisions.size()];
			permisionIds = permisions.toArray(permisionIds);
			
			role.setPermisionId(permisionIds);
		}
		return list;
	}

	@Override
	public void save(Role role) {
		dao.save(role,"com.isoftstone.agiledev.hr.mapper.RoleMapper.saveRole");
		Map<String,String> p = null;
		Integer roleId = (Integer) dao.single(null, "com.isoftstone.agiledev.hr.mapper.RoleMapper.selectNextId");
		if(role.getPermisionId()==null)return;
		for (String s : role.getPermisionId()) {
			p = new HashMap<String,String>();
			p.put("roleId", roleId+"");
			p.put("pId", s);
			dao.save(p,"com.isoftstone.agiledev.hr.mapper.RoleMapper.insertMiddle");
		}
	}

	@Override
	public void update(Role role) {
		dao.update(role,"com.isoftstone.agiledev.hr.mapper.RoleMapper.updateRole");
		dao.remove(role.getUid(), "com.isoftstone.agiledev.hr.mapper.RoleMapper.removeMiddle");
		Map<String,String> p = null;
		for (String s : role.getPermisionId()) {
			p = new HashMap<String,String>();
			p.put("roleId", role.getUid()+"");
			p.put("pId", s);
			dao.save(p,"com.isoftstone.agiledev.hr.mapper.RoleMapper.insertMiddle");
		}
	}
	@Override
	public void remove(Serializable id, Role t) {
		//删除自己
		super.remove(id, t);
		//删除角色权限关联表
		dao.remove(id, "com.isoftstone.agiledev.hr.mapper.RoleMapper.removeMiddle");
		//删除用户角色关联
		dao.update(t, "com.isoftstone.agiledev.hr.mapper.RoleMapper.updateUserRole");
	}

}
