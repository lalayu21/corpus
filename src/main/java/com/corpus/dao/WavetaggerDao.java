package com.corpus.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.corpus.entity.ChangeLabeled;
import com.corpus.entity.SelectWavelistPage;
import com.corpus.entity.Wave;
import com.corpus.entity.WaveList;
import com.corpus.entity.WavetaggerWave;

@Repository
public interface WavetaggerDao {
	public void insertWave(WavetaggerWave wave);
	
	public List<WaveList> select(SelectWavelistPage selectWavelistPage);
	
	public WavetaggerWave selectDetail(int id);

	public void updateLabeledWavetagger(ChangeLabeled changeLabeled);
	
	public WaveList selectByTrainTestWavetagger(int id);
	
	//获取corpus为某一值的所有音频文件的时长
	public List<Double> selectTimeByCorpus(int corpus);
	
	//获取id为某一值的time
	public double selectTimeById(int id);
	
	//根据音频位置获取id
	public int selectCountByWavePathAndCorpus(Wave wave);

	//获取语料库音频条数
	public int selectCountEveryCorpus(int corpus);
}
