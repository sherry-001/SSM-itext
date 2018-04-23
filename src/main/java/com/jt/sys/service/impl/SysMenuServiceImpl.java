package com.jt.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jt.common.exception.ServiceException;
import com.jt.sys.dao.SysMenuDao;
import com.jt.sys.dao.SysRoleMenuDao;
import com.jt.sys.entity.Node;
import com.jt.sys.entity.SysMenu;
import com.jt.sys.service.SysMenuService;

@Service
public class SysMenuServiceImpl implements SysMenuService{
	@Autowired
	private SysMenuDao menuDao;
	@Autowired
	private SysRoleMenuDao roleMenuDao;
	

	@Override
	public List<Map<String, Object>> findObjects() {
		List<Map<String, Object>> list = menuDao.findObjects();
		return list;
	}

	@Override
	public int deleteObject(Integer id) {
		if(id==null || id<1)
			throw new ServiceException("参数不合法: id= "+id);
		int nums = menuDao.getChildCount(id);
		if(nums>0)
			throw new ServiceException("该菜单存在子菜单,请先删除子菜单");
		int rows = menuDao.deleteObject(id);
		//删除中间表都是在具体的业务删除,不创建中间表的业务类
		roleMenuDao.deleteObjectsByMenuId(id);
		if(rows==0)
			throw new ServiceException("该数据可能已经不存在");
		return rows;
	}

	@Override
	public List<Node> findZtreeMenuNodes() {
		List<Node> nodes = menuDao.findZtreeMenuNodes();
		return nodes;
	}

	@Override
	public int saveObject(SysMenu menu) {
		 if(menu==null)
			 throw new ServiceException("保存对象不能为空");
		 if(StringUtils.isEmpty(menu.getName()))
			 throw new ServiceException("菜单名不能为空");
		 int rows;
		 try {
			rows =menuDao.insertObject(menu);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	public Map<String, Object> findObjectById(Integer id) {
		if(id==null || id<1)
			throw new ServiceException("参数不合法: id= "+id);
		Map<String, Object> objects = menuDao.findObjectById(id);
		if(null==objects)
			throw new ServiceException("该菜单可能已不存在");
		return objects;
	}

	@Override
	public int updateObject(SysMenu menu) {
		 if(menu==null)
			 throw new ServiceException("保存对象不能为空");
		 if(StringUtils.isEmpty(menu.getName()))
			 throw new ServiceException("菜单名不能为空");
		 int rows;
		 try {
			rows =menuDao.updateObject(menu);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}
}
