package com.corpus.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.corpus.entity.Corpus;
import com.corpus.entity.CorpusFmt;
import com.corpus.entity.WaveList;

@Repository
public interface CorpusDao {
	
	/*熟语料导入*/
	public void insertYes(Corpus corpus);

	/*生语料导入*/
	public void insertNo(Corpus corpus);
	
	/*根据语料库名称查找*/
	public String selectNameByName(String name);
	public String selectNameById(int id);
	
	/*查询所有*/
	public List<Corpus> selectAll();
	public List<Corpus> selectCorpusByType(int flag);
	public List<Corpus> selectCorpusByLabelType(String param);
	/*public List<Corpus> selectCorpusByGender();
	public List<Corpus> selectCorpusByPerson();
	public List<Corpus> selectCorpusByLanguage();
	public List<Corpus> selectCorpusByEffective();*/
	
	public List<Corpus> selectCorpusByInput(String param);
	/*public List<Corpus> selectCorpusByContextInput(String input);
	public List<Corpus> selectCorpusByGenderInput(String input);
	public List<Corpus> selectCorpusByPersonInput(String input);
	public List<Corpus> selectCorpusByLanguageInput(String input);
	public List<Corpus> selectCorpusByEffectiveInput(String input);*/
	
	/*根据id查语料库除id之外的其他参数*/
	public Corpus selectAllById(int id);
	public double selectTimeById(int id);
	public void updateTimeById(Corpus corpus);
	
	//查询记录数
	public List<WaveList> selectCountByCorpusInWavetagger(int id);
	public List<WaveList> selectCountByCorpusInPraat(int id);
	
	public int selectIdByName(String name);
	
	//根据id查询corpus中音频的格式
	public CorpusFmt selectFmtById(int corpus);
	
	//根据id查询praat标注的某一项的总时间
	public double selectTimeFmtById(String param);
	/*public double selectTimeFmtByIdGender(Wave wave);
	public double selectTimeFmtByIdSpeaker(Wave wave);
	public double selectTimeFmtByIdLanguage(Wave wave);
	public double selectTimeFmtByIdEffective(Wave wave);*/
	
	//根据id查找wavePath
	public String selectWavePathById(int id);
	
	//将语料库中的音频和标注文件的路径插入ip表中
	public void insert2ip(Corpus corpus);
	
	//从ip表中查询ip、username、password
	public Corpus selectIp(Corpus corpus);
	
	//更新语料库的完成状态
	public void updateCorpusFlag(Corpus corpus);
}
