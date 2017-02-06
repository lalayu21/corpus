package com.corpus.entity;

import java.sql.Timestamp;

/**
 * 任务表
 * @author Lenovo
 *
 */
public class Tasks {
	private Integer id;//任务id
	
	private String task_name;//任务名称
	
	private String file_path;//任务文件所在路径
	
	private String task_desp;//任务描述
	
	private Integer object_num;//目标总数
	
	private byte task_status;//任务状态  1-待导入2-待标注 3-标注中 4-标注完成 5-暂停
	
	private byte pause_status;//暂停状态  1-运行中2-等待暂停 3-已暂停 
	
	private byte grade;//任务等级
	
	private byte export_status;//导出状态
	
	private Timestamp create_time;//创建时间

	private Timestamp update_time;//更新时间
	//扩展参数
	private Integer Batch_num;//批次总数
	
	private double complete;//完成比例
	
	private double effective_value;//已完成时长
	
	private String desp;//任务描述
	
	

	public Integer getBatch_num() {
		return Batch_num;
	}

	public void setBatch_num(Integer batch_num) {
		Batch_num = batch_num;
	}

	public double getComplete() {
		return complete;
	}

	public void setComplete(double complete) {
		this.complete = complete;
	}

	public double getEffective_value() {
		return effective_value;
	}

	public void setEffective_value(double effective_value) {
		this.effective_value = effective_value;
	}

	public String getDesp() {
		return desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

	public byte getExport_status() {
		return export_status;
	}

	public void setExport_status(byte export_status) {
		this.export_status = export_status;
	}

	public byte getGrade() {
		return grade;
	}

	public void setGrade(byte grade) {
		this.grade = grade;
	}

	public byte getPause_status() {
		return pause_status;
	}

	public void setPause_status(byte pause_status) {
		this.pause_status = pause_status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getTask_desp() {
		return task_desp;
	}

	public void setTask_desp(String task_desp) {
		this.task_desp = task_desp;
	}

	public Integer getObject_num() {
		return object_num;
	}

	public void setObject_num(Integer object_num) {
		this.object_num = object_num;
	}

	public byte getTask_status() {
		return task_status;
	}

	public void setTask_status(byte task_status) {
		this.task_status = task_status;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public Timestamp getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	
}
