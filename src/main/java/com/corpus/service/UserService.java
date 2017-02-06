package com.corpus.service;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.corpus.entity.User;

public interface UserService {
	
	/* 函数名：     public int checkLogin(string username, string password, HttpServletRequest request)
	 * 返回类型：int;  0：登录成功；1：用户名或密码为空；2：用户名或密码不匹配
	 * 功能：          判断用户名和密码是否和数据库中的注册用户匹配，匹配成功，将用户名、权限、访问后台的最新时间写到session，跳转到欢迎页面
	 * 		      匹配失败返回message，跳转到登录页面
	 * */
	public int checkLogin(String username, String password, HttpServletRequest request);
	
	//根据id查表
	public User queryById(int id);
	
	public List<User> queryByAccess(int access);
	
	//插入完整信息
	public void saveUser(User user);
	
	//判断用户注册时输入的信息是否为空
	public int checkUser(User user);
	
	/* 函数名： public string checkUsername(string username)
	 * 返回类型：int；1：代表不存在该用户，2：代表用户已存在
	 * 功能：判断用户名是否存在
	 * */
	public int checkUsername(String username);
	
	public int checkUsernameById(String username, int id);
	
	/* 函数名：public List<User> getAllUser()
	 * 返回值：list
	 * 功能：获取数据库中的所有用户
	 * */
	public List<User> getAllUser();
	
	/* 函数名：public void updateAccessById(int id)
	 * 返回值：无
	 * 功能：更新数据库中序号为id的用户的权限*/
	public void updateAccessById(int id, int access);
	
	public void updateUserById(User user);
	
}
