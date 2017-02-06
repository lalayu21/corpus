package com.corpus.dao;

import com.corpus.entity.CorpusFmt;
import com.corpus.entity.Wave;

public interface FmtDao {
	//更新时间
	public void updateTimeById(String param);
	/*public void updateTimeByIdGender(Wave wave);
	public void updateTimeByIdLanguage(Wave wave);
	public void updateTimeByIdEffective(Wave wave);
	public void updateTimeByIdSpeaker(Wave wave);*/
	
	//插入语料库格式
	public void insertFmtByCorpus(CorpusFmt corpusFmt);
}
