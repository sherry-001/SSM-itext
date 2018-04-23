package com.jt.sys.dao;

import org.apache.ibatis.annotations.Param;

public interface SysUserRoleDao {
	
	int insertObject(
			@Param("userId") Integer userId,
			@Param("roleIds") String[] roleIds);
}
