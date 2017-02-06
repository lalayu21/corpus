package com.corpus.service;

import org.apache.commons.net.ftp.FTPClient;

import com.corpus.entity.CorpusFmt;
import com.corpus.entity.FtpConnect;

public interface ReadContextService {
	
	public void getLabel(FTPClient ftpClientWave, FTPClient ftpClientLabel, int flag, int id, FtpConnect labelConnect, FtpConnect waveConnect, CorpusFmt corpusFmt);
	
	public void getPraatContext(FTPClient ftpClientLabel, FTPClient ftpClientWave, String ftpPath, int id, long length);

	public void getWavetaggerContext(FTPClient ftpClientLabel, FTPClient ftpClientWave, String ftpPath, String fileName, int id, FtpConnect waveConnect, FtpConnect labelConnect, CorpusFmt corpusFmt);
	
	//递归获取文件列表
	public void getWavetaggerListRecurrent(FTPClient ftpClientLabel, FTPClient ftpClientWave, String ftpPath, int id, FtpConnect labelConnect, FtpConnect waveConnect, CorpusFmt corpusFmt);

	public void getPraatListRecurrent(FTPClient ftpClientLabel, FTPClient ftpClientWave, String ftpPath, int id, FtpConnect labelConnect, FtpConnect waveConnect, CorpusFmt corpusFmt);

	public void getFileListRecurrent(FTPClient ftpClient, String ftpPath, int flag, int id, FtpConnect labelConnect, int resultID, CorpusFmt corpusFmt);
	
	//获取所有音频的时长总和
	public void getCorpusTime(int id);
}
