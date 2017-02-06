package com.corpus.service;

import java.util.List;
import java.util.Map;

import com.corpus.entity.AttributeValues;
import com.corpus.entity.Attributes;
import com.corpus.entity.AttributesAndValue;
import com.corpus.entity.RegTask;
import com.corpus.entity.TrainSet;
import com.corpus.entity.Usage;
import com.corpus.entity.WaveList;

import net.sf.json.JSONObject;

public interface LabelService {
	//生成训练集合测试集
	public Map<String, List<WaveList>> getTTSet(int id, int type, String per, String labelType, String name);
	public Map<String, List<WaveList>> getTTSetByTime(int id, int type, float train, float test, String labelType, String name);
	
	public Map<String, List<WaveList>> getTestSet(List<WaveList> count, String per, String labelType, double time, int id, int type, String name);
	public Map<String, List<WaveList>> getTestSetByTime(List<WaveList> count, double train, double test, String labelType, int id, int type, double totalTime, String name);
	
	//获取set list
	public List<TrainSet> getSetListService(int corpus);
	
	//获取训练集的使用情况
	public List<Usage> getUsage(int id);
	
	//为某一个训练集添加新的使用信息
	public JSONObject setUsage(JSONObject info);
	
	//获取某一id的使用情况
	public Usage getUsageById(int id);
	
	//将数据提供给用户
	public JSONObject provideUsage(Usage usage, long time);
	
	//获取所有标注任务的属性
	public List<AttributesAndValue> queryAllAttributes();
	
	//获取属性对应的属性值
	public List<AttributeValues> queryAllAttributeValues(int id);
	
	//获取所有的待标注属性
	public List<Attributes> querylabelattr();
	
	//判断任务名称是否存在
	public int checkTaskname(String taskname);
	
	//添加新任务
	public int insertNewTask(RegTask regTask);
	
}
