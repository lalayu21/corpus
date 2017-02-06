package com.corpus.entity;

import java.sql.Timestamp;

public class Corpus {
	private int id;
	//名称
	private String name;
	//生熟语料
	private int type;
	//标注工具
	private int labelType;
	//音频位置
	private String wavePath;
	//标注结果位置
	private String labelPath;
	
	private String desp;
	
	private int context;
	
	private int gender;
	
	private int language;
	
	private int speaker;
	
	private int effective;
	
	private double time;
	
	private String ip;
	
	private String username;
	
	private String password;
	
	private int fileType;
	
	private int flag;
	
	private Timestamp createtime;
	
	private int corpus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLabelType() {
		return labelType;
	}

	public void setLabelType(int labelType) {
		this.labelType = labelType;
	}

	public String getWavePath() {
		return wavePath;
	}

	public void setWavePath(String wavePath) {
		this.wavePath = wavePath;
	}

	public String getLabelPath() {
		return labelPath;
	}

	public void setLabelPath(String labelPath) {
		this.labelPath = labelPath;
	}

	public String getDesp() {
		return desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

	public int getContext() {
		return context;
	}

	public void setContext(int context) {
		this.context = context;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}

	public int getSpeaker() {
		return speaker;
	}

	public void setSpeaker(int speaker) {
		this.speaker = speaker;
	}

	public int getEffective() {
		return effective;
	}

	public void setEffective(int effective) {
		this.effective = effective;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public int getCorpus() {
		return corpus;
	}

	public void setCorpus(int corpus) {
		this.corpus = corpus;
	}
	
}
