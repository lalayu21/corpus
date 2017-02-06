package com.corpus.Interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.corpus.dao.UserDao;
import com.corpus.entity.User;

public class LoginInterceptor2 implements HandlerInterceptor {

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
		
		HttpSession session = request.getSession();
		
		String usernameSession = (String) session.getAttribute("username");
		
		if(usernameSession == null || usernameSession.equals("") || userDao.findByUsername(usernameSession) == null){
			response.sendRedirect("../index.jsp");
			return false;
		}
		
		session.setMaxInactiveInterval(30*60);
		return true;
	}
}
