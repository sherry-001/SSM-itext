package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.common.vo.CheckBox;
import com.jt.sys.entity.SysRole;

public interface SysRoleDao {
	
	/**根据参数id执行删除操作*/
	int deleteObject(Integer id);
	
	/**获取当前页的校色信息*/
	List<SysRole> findPageObjects(
			@Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize,
			@Param("name") String name);
	
	/**获取记录总数,这里需要指定参数的name,因为在mapper中的sql语句中需要使用name值做判断*/
	int getRowCount(@Param("name") String name);
	/**保存角色信息
	 * @param role
	 * @return
	 */
	int insertObject(SysRole role);
	/**修改角色信息
	 * @param role
	 * @return
	 */
	int updateObject(SysRole role);
	
	/**
	 * 获取所有的角色的名称
	 * @return
	 */
	List<CheckBox> findObjects();
	
	
}
