package com.corpus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {
	@RequestMapping(value = "logout")
	public void logout(HttpServletResponse response, HttpServletRequest request){
		HttpSession httpSession = request.getSession();
		httpSession.invalidate();
		
		try {
			response.sendRedirect("index.jsp");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
