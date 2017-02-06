package com.corpus.wave.service;

import java.io.BufferedReader;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;

import com.corpus.entity.PraatDetailSelect;
import com.corpus.entity.SelectWavelistPage;
import com.corpus.entity.WaveList;

public interface PraatService {
	public void getPraatDetail(BufferedReader br, FTPClient ftpClient, int id, String waveList, long length);
	
	public void getPraatWave(FTPClient ftpClient, String ftpPath, String fileName);
	
	public List<Integer> getIdByWaveList(int waveList, int type);
	
	public List<WaveList> getWaveList(SelectWavelistPage selectWavelistPage);
	
	public PraatDetailSelect getPraatLabeledDetail(int id, int labelType);
	
	public void changeLabeledPraatService(int id, String result);
}
