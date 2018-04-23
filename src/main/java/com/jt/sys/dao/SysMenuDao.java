package com.jt.sys.dao;

import java.util.List;
import java.util.Map;

import com.jt.sys.entity.Node;
import com.jt.sys.entity.SysMenu;

public interface SysMenuDao {
	
	List<Map<String, Object>> findObjects();
	
	int getChildCount(Integer id);
	
	int deleteObject (Integer id);
	
	List<Node> findZtreeMenuNodes();
	
	int insertObject(SysMenu menu);
	
	Map<String, Object> findObjectById(Integer id);
	
	int updateObject(SysMenu menu);
}
