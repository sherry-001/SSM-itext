package com.jt.sys.service;

import java.util.List;

import com.jt.common.vo.CheckBox;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysRole;

public interface SysRoleService {
	
	/** 定义一个方法,实现分页查询,查询出来的信息封装到分页的对象中,
	 * 其中泛型规定
	* 本方法中要分页查询角色信息,并查询角色总记录数据
    * @param pageCurrent 当表要查询的当前页的页码值
    * @return 封装当前实体数据以及分页信息
    */

	PageObject<SysRole> findPageObjects(Integer pageCurrent, String name);
	
	int deleteOject(Integer id);
	
	int insertObject(SysRole role);
	/**
	 * 修改角色信息
	 * @return
	 */
	int updateObject(SysRole role);
	
	List<CheckBox> findObjects();
}
