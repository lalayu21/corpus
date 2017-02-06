package com.corpus.wave.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.corpus.dao.FileDao;
import com.corpus.dao.PraatDao;
import com.corpus.dao.WaveDao;
import com.corpus.dao.WavetaggerDao;
import com.corpus.entity.FileLabeled;
import com.corpus.entity.PraatDetailSelect;
import com.corpus.entity.WavetaggerWave;

import net.sf.json.JSONObject;

@Service
public class WaveServiceImpl implements WaveService {
	
	@Resource
	Wavetagger wavetagger;
	
	@Resource
	PraatService praatService;
	
	@Resource
	WaveDao waveDao;
	
	@Resource
	FileDao fileDao;
	
	@Resource
	PraatDao praatDao;
	
	@Resource
	WavetaggerDao wavetaggerDao;

	@Override
	public JSONObject getDetail(String idString, String labelType) {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(idString);
		JSONObject jsonObject = new JSONObject();
		
		//wavetagger
		WavetaggerWave wavetaggerWave = wavetagger.getDetail(id);
		
		jsonObject = JSONObject.fromObject(wavetaggerWave);
		return jsonObject;
	}
	
	@Override
	public JSONObject getPraatDetail(String idString, String labelType) {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(idString);
		JSONObject jsonObject = new JSONObject();
		
		PraatDetailSelect temp = praatService.getPraatLabeledDetail(id, Integer.parseInt(labelType)-2);
		
		jsonObject = JSONObject.fromObject(temp);
		
		return jsonObject;
	}

	@Override
	public JSONObject deleteWave(int id, String labelType) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		switch (labelType) {
		case "0":
			//praat
			waveDao.deleteWavePraat(id);
			break;
		case "1":
			//wavetagger
			waveDao.deleteWaveWavetagger(id);
			break;
		case "2":
			//file
			waveDao.deleteWaveFile(id);
			break;
		default:
			System.out.println("labelType输入有误");
			jsonObject.put("error", "labelType输入有误");
			break;
		}
		return jsonObject;
	}

	@Override
	public JSONObject getFileDetail(int id) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		FileLabeled fileLabeled = fileDao.selectDetail(id);
		jsonObject = JSONObject.fromObject(fileLabeled);
		return jsonObject;
	}

	@Override
	public int getNumberInCorpus(int corpus, String labelType) {
		// TODO Auto-generated method stub
		int number = 0;
		switch (labelType) {
		case "0":
			number = praatDao.selectCountEveryCorpus(corpus);
			break;
		case "1":
			number = wavetaggerDao.selectCountEveryCorpus(corpus);
			break;
		case "2":
			number = fileDao.selectCountEveryCorpus(corpus);
			break;
		default:
			break;
		}
		return number;
	}
	
}
