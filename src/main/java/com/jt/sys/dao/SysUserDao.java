package com.jt.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jt.sys.entity.SysUser;

public interface SysUserDao {
	/**
	 * 及西宁当前页数据的查询,并能够对查询结果进行封装
	 * @param startIndex 上一页的最后一条记录的位置
	 * @param PageSize 每页最多显示的记录数
	 * @param name 查询时输入的用户名
	 * @return 查询的当前用户信息
	 * FAQ?
	 * 1 方法中@param注解的作用是对属性进行解释 在多个参数时对应到sql语句时使用
	 * 2 @requestParam 是controller层在获取页面请求信息时,请求参数与定义的参数名称不一致
	 */
	List<SysUser> findPageObjects(
			@Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize,
			@Param("username") String username);
	
	/**
	 * @param name 在只有一个参数时可以不使用,但是由于name参数要用于动态sql中
	 * @return
	 */
	int getRowCount(@Param("username") String username);
	
	int validById(
			@Param("id") Integer id,
			@Param("valid") Integer valid,
			@Param("modifiedUser") String modifiedUser);
	
	int insertObject(SysUser user);

	SysUser findUserByUserName(String username);

	List<String> findUserPermissions(String username);
	
	// 查询所有的用户信息
	List<SysUser> findObjects();
}
