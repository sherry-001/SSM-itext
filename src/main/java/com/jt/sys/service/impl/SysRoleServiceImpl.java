package com.jt.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.exception.ServiceException;
import com.jt.common.vo.CheckBox;
import com.jt.common.vo.PageObject;
import com.jt.sys.dao.SysRoleDao;
import com.jt.sys.entity.SysRole;
import com.jt.sys.service.SysRoleService;

@Service
public class SysRoleServiceImpl implements SysRoleService{
	@Autowired
	private SysRoleDao dao; 
	
	@Override
	public PageObject<SysRole> findPageObjects(Integer pageCurrent, String name) {
		//1验证数据的合法性
		if(pageCurrent==null ||pageCurrent<1)
			throw new ServiceException("参数异常:pageCurrent="+pageCurrent);
		//2查询当前也的角色信息
		//计算页面的大小
		int pageSize=2;
		int startIndex =(pageCurrent-1)*pageSize;
		List<SysRole> records = dao.findPageObjects(startIndex, pageSize,name);
		//3查询总记录数
		int rowCount=dao.getRowCount(name);
		//4对数据进行封装
		PageObject<SysRole> pObject = 
				new PageObject<SysRole>();
		pObject.setRecords(records);
		pObject.setRowCount(rowCount);
		pObject.setPageCurrent(pageCurrent);
		pObject.setPageSize(pageSize);
		
		return pObject;
	}

	@Override
	public int deleteOject(Integer id) {
		//对参数进行合法验证
		if(id==null || id<1)
			throw new ServiceException("参数不合法: id="+id);
		int rows = 0;
		//执行具体业务
		try{
		rows = dao.deleteObject(id);
		}catch(Throwable e){
			//系统报警:给维护人员发信息
			e.printStackTrace();
			throw new ServiceException("服务端维护中");
		}
		if(rows==0)
			throw new ServiceException("数据可能已经不存在");
		return rows;
	}

	@Override
	public int insertObject(SysRole role) {
		//1 判定参数的合法性,判定名字存不存在
		//2 保存数据
		//3 返回结果
		System.out.println("保存数据"+role);
		if(role==null)
			throw new ServiceException("参数不合法: SysRole= "+role);
		if(role.getName()==null || role.getName()=="")
			throw new ServiceException("名字不能为空");
		// 如果磁盘满了,可能保存不成功
		int rows = dao.insertObject(role);
		return rows;
	}

	@Override
	public int updateObject(SysRole role) {
		//验证合法性
	
		if(role==null)
			throw new ServiceException("修改对象不能为空");
		if(role.getName()==null || role.getName()=="")
			throw new ServiceException("名字不能为空");
		//进行修改
		int rows = dao.updateObject(role);
		//验证结果
		if(rows==0)
			throw new ServiceException("数据可能已经不存在");
		//返回结果
		return rows;
	}

	@Override
	public List<CheckBox> findObjects() {
		return dao.findObjects();
	}
}
