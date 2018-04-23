package com.jt.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.sys.entity.Node;
import com.jt.sys.entity.SysMenu;
import com.jt.sys.service.SysMenuService;

@Controller
@RequestMapping("/menu/")
public class SysMenuController {
	@Autowired
	private SysMenuService menuService;
	
	@RequestMapping("listUI")
	public String listUI(){
		return "sys/menu_list";
	}
	
	@ResponseBody
	@RequestMapping("doFindObject")
	public JsonResult doFindObject(){
		List<Map<String, Object>> list = menuService.findObjects();
		return new JsonResult("query ok", list);
	}
	
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id){
		int rows = menuService.deleteObject(id);
		return new JsonResult("delete ok: rows = "+rows);
	}
	
	@RequestMapping("editUI")
	public String editUI(){
		return "sys/menu_edit";
	}
	
	@RequestMapping("doFindZtreeMenuNodes")
	@ResponseBody
	public JsonResult doFindZtreeMenuNodes(){
		List<Node> nodes = menuService.findZtreeMenuNodes();
		return new JsonResult(nodes);
	}
	
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysMenu menu){
		int rows = menuService.saveObject(menu);
		return new JsonResult("save ok: rows = "+rows);
	}
	
	@RequestMapping("findObjectById")
	@ResponseBody
	public JsonResult findObjectById(Integer id){
		Map<String, Object> map = menuService.findObjectById(id);
		return new JsonResult("query ok", map);
	}
	
	
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysMenu menu){
		int rows = menuService.updateObject(menu);
		return new JsonResult("update ok, rows = "+rows);
	}
}
