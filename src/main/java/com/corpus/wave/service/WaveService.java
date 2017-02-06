package com.corpus.wave.service;


import net.sf.json.JSONObject;

public interface WaveService {
	public JSONObject getDetail(String id, String labelType);

	public JSONObject getPraatDetail(String id, String labelType);
	
	//获取文件标注的结果
	public JSONObject getFileDetail(int id);
	
	//删除指定的音频数据和标注内容
	public JSONObject deleteWave(int id, String labelType);
	
	//获取语料库中的音频总条数
	public int getNumberInCorpus(int corpus, String labelType);
}
