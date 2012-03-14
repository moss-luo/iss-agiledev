package com.isoftstone.agiledev.manages;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.isoftstone.agiledev.dao.BaseDao;

public class BaseServiceImpl<T> implements BaseService<T> {

	@Resource
	protected BaseDao dao;
	private Map<String,Object> queryCondition = null;
	protected T t;

	private String packageName = null;
	/* (non-Javadoc)
	 * @see com.isoftstone.agiledev.manages.BaseService#list(java.util.Map, T)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> list(Map<String, Object> p,T t){
		this.queryCondition = p;
		this.t = t;
		return dao.list(p, this.getOperationCommand(t)+".list");
	}
	/* (non-Javadoc)
	 * @see com.isoftstone.agiledev.manages.BaseService#save(T)
	 */
	@Override
	public void save(T t){
		dao.save(t,this.getOperationCommand(t)+".save");
	}
	/* (non-Javadoc)
	 * @see com.isoftstone.agiledev.manages.BaseService#update(T)
	 */
	@Override
	public void update(T t){
		dao.update(t,this.getOperationCommand(t)+".update");
	}
	/* (non-Javadoc)
	 * @see com.isoftstone.agiledev.manages.BaseService#remove(java.io.Serializable, T)
	 */
	@Override
	public void remove(Serializable id,T t){
		for (String s : id.toString().split(",")) {
			dao.remove(s, this.getOperationCommand(t)+".remove");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.isoftstone.agiledev.manages.BaseService#getOperationCommand(T)
	 */
	@Override
	public String getOperationCommand(T t){
		String name = t.getClass().getName();
		name = name.substring(name.lastIndexOf(".")+1);
		System.err.println(this.packageName+"."+name+"Mapper");
		return this.packageName+"."+name+"Mapper";
	}
	@Override
	public int getTotal() {
		return (Integer) dao.single(queryCondition, this.getOperationCommand(this.t)+".total");
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean unique(Map<String, Object> p, T t) {
		List l = dao.list(p, this.getOperationCommand(t)+".unique");
		if(l.size()>0){
			return false;
		}
		return true;
	}
}
