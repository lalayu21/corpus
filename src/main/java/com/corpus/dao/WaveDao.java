package com.corpus.dao;


import java.util.List;

import com.corpus.entity.Wave;

public interface WaveDao {
	
	/*将praat标注中的音频每条总时长存入数据库wave表中*/
	public void updateTimeById(String param);
	/*public void updateTimeByIdGender(Wave wave);
	public void updateTimeByIdLanguage(Wave wave);
	public void updateTimeByIdSpeaker(Wave wave);
	public void updateTimeByIdEffective(Wave wave);*/
	//获取id为某一值的所有音频的时间
	public List<Double> selectTimeById(String param);
	/*public List<Double> selectTimeByIdGender(Wave wave);
	public List<Double> selectTimeByIdLanguage(Wave wave);
	public List<Double> selectTimeByIdSpeaker(Wave wave);
	public List<Double> selectTimeByIdEffective(Wave wave);*/
	//根据waveID获取时间
	public double selectTimeByTypeAndId(String param);
	/*public double selectTimeByTypeAndIdGender(Wave wave);
	public double selectTimeByTypeAndIdLanguage(Wave wave);
	public double selectTimeByTypeAndIdSpeaker(Wave wave);
	public double selectTimeByTypeAndIdEffective(Wave wave);*/
	//根据corpusID和wave获取id
	public int selectIdByCorpusAndWave(Wave wave);
	//根据corpusID和wave获取id的个数
	public int selectCountByCorpusAndWave(Wave wave);
	
	//删除指定音频数据
	public void deleteWavePraat(int id);
	public void deleteWaveWavetagger(int id);
	public void deleteWaveFile(int id);
	
}
