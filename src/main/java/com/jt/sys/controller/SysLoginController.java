package com.jt.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.sys.service.SysUserService;

@Controller
@RequestMapping("/")
public class SysLoginController {
	
	@Autowired
	private SysUserService userService;
	
	@RequestMapping("loginUI")
	public String loginUI(){
		return "login";
	}
	
	@RequestMapping("doLogin")
	@ResponseBody
	public JsonResult doLogin(String username,String password){
		userService.login(username, password);
		return new JsonResult("login OK");
	}
}
