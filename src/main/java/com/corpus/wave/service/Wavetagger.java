package com.corpus.wave.service;

import java.io.BufferedReader;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;

import com.corpus.entity.CorpusFmt;
import com.corpus.entity.FtpConnect;
import com.corpus.entity.SelectWavelistPage;
import com.corpus.entity.Time;
import com.corpus.entity.WaveList;
import com.corpus.entity.WavetaggerWave;
import com.sun.xml.internal.org.jvnet.fastinfoset.VocabularyApplicationData;

public interface Wavetagger {
	public List<WaveList> getName(SelectWavelistPage selectWavelistPage);
	
	public WavetaggerWave getDetail(int id);
	
	public void getWavetaggerDetail(BufferedReader br, FTPClient ftpClient, String ftpPath, int id, FtpConnect waveConnect, CorpusFmt corpusFmt);
	
	public Time checkWave(FTPClient ftpClient, String ftpPath, int corpus, CorpusFmt corpusFmt);

	public void changeLabeledWavetaggerService(int id, String result, String other);
	
}
