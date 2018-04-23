package com.jt.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.CheckBox;
import com.jt.common.vo.JsonResult;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysRole;
import com.jt.sys.service.SysRoleService;

@Controller
@RequestMapping("/role/")
public class SysRoleController {
	@Autowired
	private SysRoleService service;
	
	@RequestMapping("listUI")
	public String listUI(){
		return "sys/role_list";
	}
	
	@RequestMapping("doFindPageObjets")
	@ResponseBody
	public JsonResult doFindPageObjets(Integer pageCurrent,String name){
		 PageObject<SysRole> pObjects = 
				 service.findPageObjects(pageCurrent,name);
		 return new JsonResult("query ok",pObjects);
	}
	
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id){
		service.deleteOject(id);
		return new JsonResult("delete ok");
	}
	
	@RequestMapping("editUI")
	public String editUI(){
		return "sys/role_edit";
	}
	
	@ResponseBody
	@RequestMapping("insertObject")
	public JsonResult insertObject(SysRole role){
		int rows = service.insertObject(role);
		return new JsonResult("success save rows: "+rows);
	}
	
	@ResponseBody
	@RequestMapping("updateObject")
	public JsonResult updateObject(SysRole role){
		int rows = service.updateObject(role);
		return new JsonResult("success update rows: "+rows);
	}
	
	@RequestMapping("doFindObject")
	@ResponseBody
	public JsonResult doFindObject(){
		List<CheckBox> objects = service.findObjects();
		return new JsonResult(objects);
	}
}
