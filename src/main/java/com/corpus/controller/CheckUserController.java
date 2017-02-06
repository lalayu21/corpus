package com.corpus.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.corpus.service.UserService;



@Controller
public class CheckUserController {
	@Resource
	UserService userservice;
	
	/*public void checkUsername(ModelMap map, @RequestParam("username") String username, @RequestParam(value="id",required=false) String id, HttpServletResponse response) throws IOException{
		User user = new User();
		user.setUsername("username");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(JSONObject.fromObject(user).toString());
		response.getWriter().flush();
	}*/

	@RequestMapping(value="/checkUsername",method = RequestMethod.GET)
	public void checkUsername(ModelMap map, @RequestParam("username") String username, @RequestParam(value="id",required=false) String id, HttpServletResponse response) throws IOException{
		String exist = "0";
		if(id == null){
			if(userservice.checkUsername(username) == 1){
				exist = "1";
			}
		}else{
			int temp = Integer.parseInt(id);
			if(userservice.checkUsernameById(username,temp) == 1)
				exist = "1";
		}
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(exist);
		response.getWriter().flush();
	}
	
	
}
