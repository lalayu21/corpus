package com.corpus.entity;
/**
 * 注册任务扩展类
 * @author Lenovo
 *
 */
public class RegTask {
	
	private String id;

	private String task_name;//任务名称
	
	private String file_path;//任务路径
	
	private String task_desp;//任务描述
	
	private byte priority;//任务等级
	
	private String yizhishuxing;//已知属性
	
	private String biaozhushuxing;//标注属性
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte getPriority() {
		return priority;
	}

	public void setPriority(byte priority) {
		this.priority = priority;
	}

	public String getTask_desp() {
		return task_desp;
	}

	public void setTask_desp(String task_desp) {
		this.task_desp = task_desp;
	}

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getYizhishuxing() {
		return yizhishuxing;
	}

	public void setYizhishuxing(String yizhishuxing) {
		this.yizhishuxing = yizhishuxing;
	}


	public String getBiaozhushuxing() {
		return biaozhushuxing;
	}

	public void setBiaozhushuxing(String biaozhushuxing) {
		this.biaozhushuxing = biaozhushuxing;
	}

}
