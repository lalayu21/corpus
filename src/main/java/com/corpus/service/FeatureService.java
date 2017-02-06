package com.corpus.service;


import net.sf.json.JSONObject;

public interface FeatureService {
	//将特征信息插入数据库
	public JSONObject insert2feature(JSONObject info);
	
}
