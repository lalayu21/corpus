package com.corpus.wave.service;

import java.util.List;

import com.corpus.entity.PraatDetailSelect;
import com.corpus.entity.SelectWavelistPage;
import com.corpus.entity.WaveList;

public interface FileService {
	
	//public void getFileWave(FTPClient ftpClient, String ftpPath, String fileName);
	//获取file标注中的音频文件
	public List<WaveList> getWaveList(SelectWavelistPage selectWavelistPage);
	//获取指定语料库的标注结果
	public List<PraatDetailSelect> getLabelResult(int corpus);
	
}
