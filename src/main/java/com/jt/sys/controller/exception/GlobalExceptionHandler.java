package com.jt.sys.controller.exception;


import org.apache.shiro.authc.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.exception.ServiceException;
import com.jt.common.vo.JsonResult;

@ControllerAdvice
public class GlobalExceptionHandler {

		@ExceptionHandler(ServiceException.class)
		@ResponseBody
		public JsonResult doServiceException(ServiceException e){
			e.printStackTrace();
			JsonResult result = new JsonResult(e);
			return result;
		}
		
		@ExceptionHandler(AuthenticationException.class)
		@ResponseBody
		public JsonResult doAuthenticationException(AuthenticationException e){
			e.printStackTrace();
			JsonResult result = new JsonResult(e);
			/*System.out.println("************"+result);*/
			return result;
		}
}


