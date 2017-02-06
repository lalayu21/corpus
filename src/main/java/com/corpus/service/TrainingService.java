package com.corpus.service;

import net.sf.json.JSONArray;

public interface TrainingService {
	
	//生成训练集和测试集
	public String getSet(JSONArray jsonArray);
	
	//只生成不导出
	
	//生成+导出
}
