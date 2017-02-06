package com.corpus.thread;

import org.apache.commons.net.ftp.FTPClient;

import com.corpus.entity.CorpusFmt;
import com.corpus.entity.FtpConnect;
import com.corpus.service.ReadContextService;


public class LeadinCorpusThread extends Thread {
	private FTPClient ftpClientWave;
	private FTPClient ftpClientLabel;
	private int labelType;
	private int id;
	private FtpConnect labelConnect;
	private FtpConnect waveConnect;
	private CorpusFmt corpusFmt;
	private ReadContextService readContextService;
	
	
	public LeadinCorpusThread(FTPClient ftpClientWave, FTPClient ftpClientLabel, int labelType, int id, FtpConnect labelConnect, FtpConnect waveConnect, CorpusFmt corpusFmt, ReadContextService readContextService) {
		// TODO Auto-generated constructor stub
		this.ftpClientLabel = ftpClientLabel;
		this.ftpClientWave = ftpClientWave;
		this.labelType = labelType;
		this.id = id;
		this.labelConnect = labelConnect;
		this.waveConnect = waveConnect;
		this.corpusFmt = corpusFmt;
		this.readContextService = readContextService;
	}
	
	public void run(){
		readContextService.getLabel(ftpClientWave, ftpClientLabel, labelType, id, labelConnect, waveConnect, corpusFmt);
	}
}
