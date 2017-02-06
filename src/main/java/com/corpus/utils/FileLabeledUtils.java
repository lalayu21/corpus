package com.corpus.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.ibatis.reflection.wrapper.BaseWrapper;

import com.corpus.entity.CorpusFmt;
import com.corpus.entity.FileLabeled;
import com.corpus.entity.FtpConnect;
import com.corpus.entity.Time;

public class FileLabeledUtils {
	private FTPClient ftpClient;
	private int flag;
	private int id;
	private FtpConnect waveConnect;
	private int resultID;
	private CorpusFmt corpusFmt;
	private QueryRunner queryRunner;
	
	public FileLabeledUtils(FTPClient ftpClient, int flag, int id, FtpConnect waveConnect, int resultID, CorpusFmt corpusFmt, QueryRunner queryRunner){
		this.ftpClient = ftpClient;
		this.flag = flag;
		this.id = id;
		this.waveConnect = waveConnect;
		this.resultID = resultID;
		this.corpusFmt = corpusFmt;
		this.queryRunner = queryRunner;
	}
	
	
	public void getFileListRecurrent(String ftpPath) {
		try {
			FTPClientUtils ftpClientUtils = new FTPClientUtils();
			ftpClient = ftpClientUtils.getFTPClient(waveConnect);
			
			FTPFile[] ftpFiles = ftpClient.listFiles(ftpPath);
			
			for(int i = 0; i < ftpFiles.length; i++){
				if(ftpFiles[i].isFile()){
					//if(!".".equals(ftpPath)){
					if(flag == 1){
						String filename = ftpPath + "/" + ftpFiles[i].getName();
						if("./".equals(filename.substring(0,2)))
							filename = filename.substring(2, filename.length());
						
						WaveUtils waveUtils = new WaveUtils(ftpClient, filename, corpusFmt);
						Time time = waveUtils.checkWave();
						if(time.getEndtime() != 0){
							//将音频文件的标注结果存入数据库
							/*FileLabeled fileLabeled = new FileLabeled();
							fileLabeled.setWave(filename);
							fileLabeled.setTime(time.getEndtime());
							fileLabeled.setCorpus(resultID);
							fileLabeled.setLength(time.getLength());*/
							try {
								queryRunner.update("insert into file(wave, resultID, time, length values(?, ?, ?, ?)", filename,resultID, time.getEndtime(), time.getLength());
								queryRunner.update("update fileresult set time = time + ? where id = ?", time.getEndtime(), resultID);
							} catch (Exception e) {
								// TODO: handle exception
								System.out.println("error: insert into file and update time in fileresult");
							}
							
						}else{
							System.out.println(ftpPath + "格式不正确");
						}
						ftpClient.disconnect();
						ftpClient = ftpClientUtils.getFTPClient(waveConnect);
					}					
				}else if (ftpFiles[i].isDirectory()) {
					if(flag == 0){
						String name = ftpFiles[i].getName();
						System.out.println("这是一种标注结果，标注结果为：" + name);
						
						try {
							queryRunner.update("insert into fileresult(result, corpus) values(?, ?)", name, id);
							resultID = queryRunner.query("select id from fileresult where result = ? and corpus = ?", new BeanHandler<Integer>(Integer.class), name, id);
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println("error : insert into fileresult and select id from fileresult");
						}
					}
					FileLabeledUtils fileLabeledUtils = new FileLabeledUtils(ftpClient, 1, id, waveConnect, resultID, corpusFmt, queryRunner);
					fileLabeledUtils.getFileListRecurrent(ftpPath + "/" + ftpFiles[i].getName());
				}
			}
			System.out.println("------------------------------");
		} catch (Exception e) {
			System.out.println("文件路径不存在");
			e.printStackTrace();
		}
		
	}
}
