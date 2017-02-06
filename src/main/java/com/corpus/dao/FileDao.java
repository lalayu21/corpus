package com.corpus.dao;

import java.util.List;

import com.corpus.entity.FileLabeled;
import com.corpus.entity.PraatDetailSelect;
import com.corpus.entity.SelectWavelistPage;
import com.corpus.entity.WaveList;

public interface FileDao {
	//将标注结果写入数据库fileresult表中
	public void insert2fileresult(FileLabeled fileLabeled);
	
	//将文件标注结果插入数据库:名称、时长、标注内容
	public void insert2file(FileLabeled fileLabeled);
	
	//根据标注结果获取标注结果id
	public int selectIdByResult(FileLabeled fileLabeled);
	
	//更新fileresult中的时长
	public void updateTimeById(FileLabeled fileLabeled);
	
	//根据语料库id获取file标注音频列表
	public List<WaveList> selectWavelistByCorpus(SelectWavelistPage selectWavelistPage);

	//获取某一id对应的音频数据
	public FileLabeled selectDetail(int id);

	//获取语料库音频条数
	public int selectCountEveryCorpus(int corpus);
	
	//获取指定语料库中的所有标注结果
	public List<PraatDetailSelect> selectLabelResultByCorpus(int corpus);
}
