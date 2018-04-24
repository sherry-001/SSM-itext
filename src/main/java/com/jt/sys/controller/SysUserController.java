package com.jt.sys.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.jt.common.vo.JsonResult;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;

@Controller
@RequestMapping("/user/")
public class SysUserController {
	@Autowired
	private SysUserService service;
	
	@RequestMapping("listUI")
	public String listUI(){
		return "sys/user_list";
	}
	
	@ResponseBody
	@RequestMapping("doFindPageObjects")
	public JsonResult doFindPageObjects(String username,Integer pageCurrent){
		PageObject<SysUser> pObject = service.findPageObjects(pageCurrent, username);
		return new JsonResult("query ok",pObject);	
	}
	
	@ResponseBody
	@RequestMapping("doValidById")
	public JsonResult doValidById(Integer id,Integer valid,String modifiedUser){
		int rows = service.validById(id, valid, modifiedUser);
		return new JsonResult("success valid, rows= "+rows);
	}
	
	@RequestMapping("editUI")
	public String editUI(){
		return "sys/user_edit";
	}
	
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysUser user,String roleIds){
		int rows = service.saveObject(user, roleIds);
		return new JsonResult("save OK");
	}
	
	@RequestMapping("doDownload")
	@ResponseBody
	public JsonResult doDownload(){
		return new JsonResult("downloading");
	}
	
	@RequestMapping("/export")
	@ResponseBody
	public JsonResult doExportUser(HttpServletResponse response,String fileName) throws Exception{
		System.out.println(fileName);
		response.setContentType("application/binary;charset=UTF-8");
		/*String fileName="userInfo"+new String(
				new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(
						new Date()).getBytes(), "utf-8");*/
		response.setHeader("Content-disposition", "attachment; filename=" + 
						new String(fileName.getBytes("utf-8"),"iso-8859-1") + ".xlsx");
		ServletOutputStream out = response.getOutputStream();
		Workbook workbook = service.findObjects();
	    workbook.write(out);
	    return new JsonResult("export success");
	}
	
	@RequestMapping("/exportPDF")
	@ResponseBody
	public ModelAndView doExportPdfUser(HttpServletResponse response,String fileName)throws Exception{
		/*String fileName="userInfo"+new String(
				new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(
						new Date()).getBytes(), "utf-8");*/
		response.setHeader("Content-disposition", "attachment; filename=" + 
						new String(fileName.getBytes("utf-8"),"iso-8859-1") + ".pdf");
		View view = service.findObjectsPdf();
		ModelAndView mv = new ModelAndView(view);
		return mv;
	}
}
