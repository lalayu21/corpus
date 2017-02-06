package com.corpus.wave.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.corpus.dao.FileDao;
import com.corpus.entity.PraatDetailSelect;
import com.corpus.entity.SelectWavelistPage;
import com.corpus.entity.WaveList;
import com.corpus.service.RegexService;

@Service
public class FileServiceImpl implements FileService {

	@Resource
	RegexService regexService;
	
	@Resource
	FileDao fileDao;

	@Override
	public List<WaveList> getWaveList(SelectWavelistPage selectWavelistPage) {
		// TODO Auto-generated method stub
		return fileDao.selectWavelistByCorpus(selectWavelistPage);
	}

	@Override
	public List<PraatDetailSelect> getLabelResult(int corpus) {
		// TODO Auto-generated method stub
		//获取标注结果
		List<PraatDetailSelect> result = fileDao.selectLabelResultByCorpus(corpus);
		return result;
	}

}
