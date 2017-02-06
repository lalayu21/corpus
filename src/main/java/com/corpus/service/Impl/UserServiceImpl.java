package com.corpus.service.Impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpus.dao.UserDao;
import com.corpus.entity.User;
import com.corpus.entity.UsernameAndId;
import com.corpus.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	UserDao userDao;
	
	
	public User queryById(int id) {
		User user = new User();
		user = userDao.findById(id);
		return user;
	}


	@Override
	@Transactional
	public void saveUser(User user) {
		userDao.save(user);
	}


	@Override
	public int checkUser(User user) {
		// TODO Auto-generated method stub
		if(user.getUsername() == null || "".equals(user.getUsername())){
			return 1;
		}else if (user.getPassword() == null || "".equals(user.getPassword())) {
			return 2;
		}else if (user.getEmail() == null || "".equals(user.getEmail())) {
			return 3;
		}else if (user.getIdCode() == null || "".equals(user.getIdCode())) {
			return 4;
		}else if (user.getTel() == null || "".equals(user.getTel())) {
			return 5;
		}else if (user.getTruename() == null || "".equals(user.getTruename())) {
			return 6;
		}else{
			if(userDao.findByUsername(user.getUsername()) != null)
				return 7;
		}
		return 0;
	}


	@Override
	public int checkLogin(String username, String password, HttpServletRequest request) {
		
		if(username == null || "".equals(username) || password == null || "".equals(password)){
			return 1;
		}else{
			User user = userDao.findByUsername(username);
			if(user == null || !password.equals(user.getPassword())){
				return 2;
			}else{
				String outTime = String.valueOf(System.currentTimeMillis() + 30 * 60);
				Map<String, String> userMessage = new HashMap<String, String>();
				userMessage.put("username", username);
				userMessage.put("outTime", outTime);
				userMessage.put("admin", "0");
				request.getSession().setAttribute("USER", userMessage);
				return 0;
			}
		}
	}


	@Override
	public int checkUsername(String username) {
		if(userDao.findByUsername(username) == null)
			return 0;
		else
			return 1;
	}


	@Override
	public List<User> getAllUser() {
		List<User> user = userDao.findAll();
		return user;
	}


	@Override
	@Transactional
	public void updateAccessById(int id, int access) {
		User user = new User();
		user.setAccess(access);
		user.setId(id);
		userDao.updateAccessById(user);
	}


	@Override
	public List<User> queryByAccess(int access) {
		// TODO Auto-generated method stub
		return userDao.findByAccess(access);
	}


	@Override
	public int checkUsernameById(String username, int id) {
		// TODO Auto-generated method stub
		UsernameAndId user = new UsernameAndId();
		user.setUsername(username);
		user.setId(id);
		
		if(userDao.findByUsernameAndId(user) == null)
			return 0;
		else
			return 1;
	}


	@Override
	public void updateUserById(User user) {
		userDao.updateUser(user);
	}

}
