package com.corpus.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.corpus.entity.User;
import com.corpus.service.UserService;
import com.millery.utils.DataSourceContextHolder;

@Controller
public class LoginController {
	
	@Resource
	UserService userservice;
	
	
	@RequestMapping(value="/toReg")
	public String register(ModelMap map,HttpServletRequest request){
		DataSourceContextHolder.setDbType("dataSource");
		request.getSession().setAttribute("message", "");
		return "/user/register";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(ModelMap map, @RequestParam String username, @RequestParam String password,
			HttpServletRequest request, HttpServletResponse response){
		System.out.println(username);
		map.put("message", "");

		DataSourceContextHolder.setDbType("dataSource");
		//int message = userservice.checkLogin(username, password, request);
		int message = 0;
		if(message == 0){
			return "/welcome/welcome";
		}else if (message == 1) {
			map.put("message", "用户名或密码为空");
			return "";
		}else{
			map.put("message", "用户名或密码不正确");
			return "";
		}
	}
	
	@RequestMapping(value = "/welcome")
	public String welcome(){
		return "/welcome/welcome";
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String registUser(ModelMap map, @ModelAttribute("pojo") User user){

		DataSourceContextHolder.setDbType("dataSource");
		map.put("register", "");
		
		
		int message = userservice.checkUser(user);
		if(message == 0){
			user.setAccess(2);
			userservice.saveUser(user);
			return "/regSuccess";
		}else{
			map.put("register", message);
			return "/user/register";
		}
	}
	
	@RequestMapping(value = "/changeUserInfo", method = RequestMethod.GET)
	public void changeUserInfo(ModelMap map, User user, HttpServletResponse response, HttpServletRequest request) throws IOException{
		/*int message = userservice.checkUser(user);*/
		
		user.setTruename(new String(user.getTruename().getBytes("ISO-8859-1"),"UTF-8"));

		DataSourceContextHolder.setDbType("dataSource");
		int message = 0;
		String exist = "0";
		if(message == 0){
			System.out.println(user.getTruename());
			userservice.updateUserById(user);
			
		}else{
			exist = "数据信息不完整";
		}
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(exist);
		response.getWriter().flush();
		
	}
	
	
	//用户管理
	@RequestMapping("/user/verifyUser")
	public String verifyUser(ModelMap map){
		DataSourceContextHolder.setDbType("dataSource");
		List<User> users = userservice.queryByAccess(2);
		map.put("user", users);
		return "/user/admin/verifyUser";
	}
	
	@RequestMapping("/user/queryUser")
	public String queryUser(ModelMap map){
		DataSourceContextHolder.setDbType("dataSource");
		List<User> users = userservice.getAllUser();
		users.get(0);
		map.put("user", userservice.getAllUser());
		return "/user/admin/queryUser";
	}
	
	//审核用户是否通过
	@RequestMapping(value = "/user/verifyUserPass", method = RequestMethod.GET)
	public void verifyUserPass(ModelMap map, @RequestParam("id") Integer id,@RequestParam("access") Integer access, HttpServletResponse response) throws IOException{
		String returnParam = "0";
		DataSourceContextHolder.setDbType("dataSource");
		if(id == null || access == null){
			returnParam = "1";
		}else{
			User user = userservice.queryById(id);
			if(user == null){
				returnParam = "2";
			}else{
				userservice.updateAccessById(id,access);
			}
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(returnParam);
		response.getWriter().flush();
	}
	
	@RequestMapping(value="/user/changeInfo", method=RequestMethod.GET)
	public String changeInfo(ModelMap map, @RequestParam("id") Integer id){
		System.out.println(id);
		DataSourceContextHolder.setDbType("dataSource");
		if(id == null){
			return "/user/admin/queryUser";
		}else{
			User user = userservice.queryById(id);
			if(user == null){
				return "/user/admin/queryUser";
			}else{
				map.put("user", user);
				System.out.println(user.getUsername());
				return "/user/admin/changeInfo";
			}
		}
	}
}
