package com.corpus.entity;
/**
 * 任务属性关系表
 * @author Lenovo
 *
 */
public class TaskAttrs {
	private Integer task_id;//任务id
	
	private Integer attr_id;//属性id
	
	private Integer attr_value_id;//属性值id
	
	private Integer task_type;//任务类型

	
	public Integer getTask_type() {
		return task_type;
	}

	public void setTask_type(Integer task_type) {
		this.task_type = task_type;
	}

	public Integer getAttr_id() {
		return attr_id;
	}

	public void setAttr_id(Integer attr_id) {
		this.attr_id = attr_id;
	}

	public Integer getTask_id() {
		return task_id;
	}

	public void setTask_id(Integer task_id) {
		this.task_id = task_id;
	}

	public Integer getAttr_value_id() {
		return attr_value_id;
	}

	public void setAttr_value_id(Integer attr_value_id) {
		this.attr_value_id = attr_value_id;
	}
	

}
