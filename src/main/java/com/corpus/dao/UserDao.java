package com.corpus.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.corpus.entity.User;
import com.corpus.entity.UsernameAndId;

@Repository
public interface UserDao {
	
	public void save(User user);
	
	public User findById(int id);
	
	public User findByUsername(String username);
	
	public String findByUsernameAndId(UsernameAndId usernameAndId);
	
	public List<User> findAll();
	
	public void updateAccessById(User user);
	
	public List<User> findByAccess(int access);
	
	public void updateUser(User user);
	
}
