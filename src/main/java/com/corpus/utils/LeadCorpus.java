package com.corpus.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.net.ftp.FTPClient;

import com.corpus.entity.Corpus;
import com.corpus.entity.CorpusFmt;
import com.corpus.entity.FtpConnect;
import com.sun.org.apache.xml.internal.serializer.utils.SystemIDResolver;

public class LeadCorpus {
	private int id;
	private QueryRunner queryRunner;
	
	private FTPClient ftpClientWave = new FTPClient();
	private FTPClient ftpClientLabel = new FTPClient();
	private int labelType = 0;
	private FtpConnect labelConnect = new FtpConnect();
	private FtpConnect waveConnect = new FtpConnect();
	private CorpusFmt corpusFmt = new CorpusFmt();

	
	/* 获取需要的参数值*/
	private void getParamters(){
		Corpus corpus = new Corpus();
		
		String sql_corpus = "select labelType, wavePath, labelPath from corpus where id = " + id;
		String sql_fmt = "select code, head, channel, sample, bitpersamples from corpusfmt where corpus = " + id;
		String sql_ip = "select ip, username, password from ip where corpus = ? and fileType = ?";
		
		try {
			corpus = queryRunner.query(sql_corpus, new BeanHandler<Corpus>(Corpus.class));
			this.labelConnect = queryRunner.query(sql_ip, new BeanHandler<FtpConnect>(FtpConnect.class), id, 1);
			this.waveConnect = queryRunner.query(sql_ip, new BeanHandler<FtpConnect>(FtpConnect.class), id, 0);
			this.corpusFmt = queryRunner.query(sql_fmt, new BeanHandler<CorpusFmt>(CorpusFmt.class));
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(this.labelConnect == null){
			System.out.println("null");
		}
		System.out.println(this.labelConnect.getIp());
		System.out.println(this.labelConnect.getUsername());
		this.labelType = corpus.getLabelType();
		//String labelPath = corpus.getLabelPath();
		//System.out.println(labelPath);
		
		//this.labelConnect.setPath(labelPath);
		this.labelConnect.setPort(21);
		
		this.waveConnect.setPath(corpus.getWavePath());
		this.waveConnect.setPort(21);
		
		FTPClientUtils ftpClientUtils = new FTPClientUtils();
		this.ftpClientLabel = ftpClientUtils.getFTPClient(labelConnect);
		this.ftpClientWave = ftpClientUtils.getFTPClient(waveConnect);
	}
	
	public LeadCorpus(int id, QueryRunner queryRunner){
		this.id = id;
		this.queryRunner = queryRunner;
		
		this.getParamters();
	}
	
	//导入数据
	public void start(){
		switch (labelType) {
		case 1:
			WavetaggerUtils wavetaggerUtils = new WavetaggerUtils(ftpClientLabel, ftpClientWave, id, labelConnect, waveConnect, corpusFmt, queryRunner);
			wavetaggerUtils.getWavetaggerListRecurrent(".");
			break;
		case 0:
			PraatUtils praatUtils = new PraatUtils(ftpClientLabel, ftpClientWave, id, labelConnect, waveConnect, corpusFmt, queryRunner);
			praatUtils.getPraatListRecurrent(".");
			WaveUtils waveUtils = new WaveUtils(queryRunner);
			waveUtils.getCorpusTime(id);
			break;
		case 2:
			FileLabeledUtils fileLabeledUtils = new FileLabeledUtils(ftpClientWave, 0, id, waveConnect, 0, corpusFmt, queryRunner);
			fileLabeledUtils.getFileListRecurrent(".");
			break;
		default:break;
		}
		Corpus corpus = new Corpus();
		corpus.setFlag(1);
		corpus.setId(id);
	
		String sql = "update corpus set flag = 1 where id = " + id;
		try {
			queryRunner.update(sql);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error: update corpus flag");
		}
	}
}
