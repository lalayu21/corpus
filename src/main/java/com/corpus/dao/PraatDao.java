package com.corpus.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.corpus.entity.ChangeLabeled;
import com.corpus.entity.GetIdList;
import com.corpus.entity.PraatDetailSelect;
import com.corpus.entity.PraatWave;
import com.corpus.entity.WaveList;
import com.corpus.entity.WaveTable;

@Repository
public interface PraatDao {
	public void insertWave(WaveTable waveTable);
	
	public int selectIdByWave(String wave);
	
	public void insertPraat(PraatWave praat);
	
	public List<Integer> selectIdByWaveList(GetIdList getIdList);
	
	public List<WaveList> selectByCorpus(String sql);
	
	//获取时长等
	public WaveList selectByTrainTestPraat(String param);
	/*public WaveList selectByTrainTestPraatContext(int id);
	public WaveList selectByTrainTestPraatGender(int id);
	public WaveList selectByTrainTestPraatLanguage(int id);
	public WaveList selectByTrainTestPraatSpeaker(int id);
	public WaveList selectByTrainTestPraatEffective(int id);*/
	
	public PraatDetailSelect selectLabelTypeById(String param);
	
	/*public PraatDetailSelect selectGenderById(int id);
	
	public PraatDetailSelect selectLanguageById(int id);
	
	public PraatDetailSelect selectPersonById(int id);
	
	public PraatDetailSelect selectEffectiveById(int id);*/
	
	public void updateLabelType(String param);

	/*public void updateGender(int id);

	public void updatePerson(int id);

	public void updatelanguange(int id);

	public void updateEffective(int id);*/
	
	public void updateLabeledPraat(ChangeLabeled changeLabeled);
	
	public void updateTimeById(WaveList waveList);
	
	//获取语料库音频条数
	public int selectCountEveryCorpus(int corpus);
	

}
