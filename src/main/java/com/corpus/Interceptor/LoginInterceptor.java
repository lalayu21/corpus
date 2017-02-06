package com.corpus.Interceptor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils.Null;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.corpus.dao.UserDao;
import com.corpus.entity.User;

public class LoginInterceptor implements HandlerInterceptor {

	@Resource
	UserDao userDao;
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		
		String username = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");
		
		HttpSession session = request.getSession();
		
		String usernameSession = (String) session.getAttribute("username");
		
		System.out.println(username + password);
		
		if(username == null || "".equals(username) || password == null || "".equals(password)){
			System.out.println("1");
			if(usernameSession == null || usernameSession.equals("") || userDao.findByUsername(usernameSession) == null){
				System.out.println("2");
				request.getSession().setAttribute("message", "用户名或密码为空");
				response.sendRedirect("index.jsp");
				return false;
			}
		}else{
			System.out.println("3");
			User user = new User();
			if(username != null){
				System.out.println("4");
				user = userDao.findByUsername(username);
			}
			if(user != null && password.equals(user.getPassword())){
				System.out.println("5");
				session.setAttribute("username", username);
			}else if(usernameSession == null || userDao.findByUsername(usernameSession) == null){
				System.out.println("6");
				request.getSession().setAttribute("message", "用户名或密码不正确");
				response.sendRedirect("index.jsp");
				return false;
			}
		}
		session.setMaxInactiveInterval(30*60);
		return true;
	}
}
