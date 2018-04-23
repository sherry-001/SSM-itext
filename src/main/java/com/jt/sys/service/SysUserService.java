package com.jt.sys.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysUser;

public interface SysUserService {
	
	/**
	 * 根据页码获取当前页数据
	 * @param pageCurrent 当前页面的数 
	 * @param name 查询的输入参数
	 * @return
	 */
	PageObject<SysUser> findPageObjects(Integer pageCurrent,String name);
	
	int validById( Integer id, Integer valid, String modifiedUser);
	
	int saveObject(SysUser user,String roleIds);
	
	/**
	 * 用户登录
	 */
	void login(String username,String password);
	
	Workbook  findObjects() throws Exception;
}
