package com.corpus.entity;

import java.util.Date;

public class User {
	
	private int id;
	
	private String username;
	
	private String password;
	
	private String truename;
	
	private int gender;
	
	private String idCode;
	
	private String tel;
	
	private String email;
	
	private int access;
	
	private Date applytime;
	
	private Date committime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAccess() {
		return access;
	}

	public void setAccess(int access) {
		this.access = access;
	}
	
	public Date getApplytime() {
		return applytime;
	}

	public void setApplytime(Date applyTime) {
		this.applytime = applyTime;
	}

	public Date getCommittime() {
		return committime;
	}

	public void setCommittime(Date commitTime) {
		this.committime = commitTime;
	}

	public String toString(){
		return "User[id = "+id+" , username = " + username + " , password = " + password + "]";
	}
	
}
