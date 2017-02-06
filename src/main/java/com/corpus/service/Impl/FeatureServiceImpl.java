package com.corpus.service.Impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.corpus.dao.FeatureDao;
import com.corpus.entity.Feature;
import com.corpus.file.FileOperation;
import com.corpus.service.FeatureService;

import net.sf.json.JSONObject;

@Service
public class FeatureServiceImpl implements FeatureService {
	
	@Resource
	FeatureDao featureDao;

	@Override
	public JSONObject insert2feature(JSONObject info) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		
		Feature feature = new Feature();
		
		try {
			feature.setCorpusID(info.getInt("corpusID"));
			feature.setMfcc(info.getInt("mfcc"));
			feature.setPlp(info.getInt("plp"));
			feature.setFbank(info.getInt("fbank"));
			feature.setMelBins(info.getInt("melBins"));
			feature.setEnergy(info.getInt("energy"));
			feature.setCeps(info.getInt("ceps"));
			feature.setLowFreq(info.getInt("lowFreq"));
			feature.setHighFreq(info.getInt("highFreq"));
			feature.setPath(info.getString("path"));
		} catch (Exception e) {
			// TODO: handle exception
			jsonObject.put("error", "输入信息有误");
			e.printStackTrace();
		}
		
		try {
			featureDao.insert2feature(feature);
			
			FileOperation fileOperation = new FileOperation(feature);
			
			int featureType = 0;
			if(feature.getMfcc() == 1){
				featureType = 1;
			}else if (feature.getPlp() == 1) {
				featureType = 2;
			}else {
				featureType = 3;
			}
			fileOperation.write("featureConfig", featureType);
			
		} catch (Exception e) {
			// TODO: handle exception
			jsonObject.put("error", "插入数据库失败");
			e.printStackTrace();
		}
		
		return jsonObject;
	}
}
