package com.isoftstone.agiledev.manages.system.permision;

import java.util.List;

import com.isoftstone.agiledev.actions.system.permision.Permision;
import com.isoftstone.agiledev.manages.BaseService;

public interface PermisionManager extends BaseService<Permision> {

	List<Permision> findByPid(String pid);
}
