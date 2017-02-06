package com.corpus.dao;

import java.util.List;

import com.corpus.entity.AttributeValues;
import com.corpus.entity.Attributes;
import com.corpus.entity.TaskAttrs;
import com.corpus.entity.Tasks;

public interface AttributesDao {
	//查询所有属性
	public List<Attributes> queryAllAttributes(String sql);
	
	//查询所有属性的属性值
	public List<AttributeValues> queryAllAttributeValues(int id);
	
	//获取所有代表著属性
	public List<Attributes> querylabelattr();
	
	//根据用户名查询task是否存在
	public int queryTaskNum(String taskname);
	
	//添加新任务
	public void insertTask(Tasks tasks);
	
	//根据任务名称获取任务信息
	public Tasks selectTaskByName(String name);
	
	//将新任务的已知或者待标注属性写入数据库
	public void insertAttributes(TaskAttrs taskAttrs);
	
	
}
