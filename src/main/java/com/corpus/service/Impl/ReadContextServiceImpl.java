package com.corpus.service.Impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.tftp.TFTPPacket;
import org.springframework.stereotype.Service;

import com.corpus.dao.CorpusDao;
import com.corpus.dao.FileDao;
import com.corpus.dao.FmtDao;
import com.corpus.dao.WaveDao;
import com.corpus.dao.WavetaggerDao;
import com.corpus.entity.Corpus;
import com.corpus.entity.CorpusFmt;
import com.corpus.entity.FileLabeled;
import com.corpus.entity.FtpConnect;
import com.corpus.entity.Time;
import com.corpus.entity.Wave;
import com.corpus.service.FTPService;
import com.corpus.service.ReadContextService;
import com.corpus.wave.service.PraatService;
import com.corpus.wave.service.Wavetagger;

@Service
public class ReadContextServiceImpl implements ReadContextService {
	
	@Resource 
	FTPService ftpService;
	
	@Resource
	Wavetagger wavetagger;
	
	@Resource
	PraatService praatService;
	
	@Resource
	WavetaggerDao wavetaggerDao;
	
	@Resource
	WaveDao waveDao;
	
	@Resource
	CorpusDao corpusDao;
	
	@Resource
	FmtDao fmtDao;
	
	@Resource
	FileDao fileDao;
	
	
	@Override
	public void getLabel(FTPClient ftpClientWave, FTPClient ftpClientLabel, int flag, int id, FtpConnect labelConnect, FtpConnect waveConnect, CorpusFmt corpusFmt) {
		
		switch (flag) {
			case 1:
				getWavetaggerListRecurrent(ftpClientLabel, ftpClientWave, ".", id, labelConnect, waveConnect, corpusFmt);
				break;
			case 0:
				getPraatListRecurrent(ftpClientLabel, ftpClientWave, ".", id, labelConnect, waveConnect, corpusFmt);
				getCorpusTime(id);
				break;
			case 2:
				getFileListRecurrent(ftpClientWave, ".", 0, id, waveConnect, 0, corpusFmt);
				break;
			default:break;
		}
		Corpus corpus = new Corpus();
		corpus.setFlag(1);
		corpus.setId(id);
		corpusDao.updateCorpusFlag(corpus);
	}
	
	@Override
	public void getWavetaggerListRecurrent(FTPClient ftpClientLabel, FTPClient ftpClientWave, String ftpPath, int id, FtpConnect labelConnect, FtpConnect waveConnect, CorpusFmt corpusFmt) {
		try {
			FTPFile[] ftpFiles = ftpClientLabel.listFiles(ftpPath);
			
			for(int i = 0; i < ftpFiles.length; i++){
				if(ftpFiles[i].isFile()){
					System.out.println(ftpFiles[i].getName());
					this.getWavetaggerContext(ftpClientLabel, ftpClientWave, ftpPath, ftpFiles[i].getName(), id, waveConnect, labelConnect, corpusFmt);
				
					ftpClientLabel.disconnect();
					ftpClientLabel = ftpService.getFtpClient(labelConnect);
					
					ftpClientWave.disconnect();
					ftpClientWave = ftpService.getFtpClient(waveConnect);
				}else if (ftpFiles[i].isDirectory()) {
					System.out.println("目录：" + ftpFiles[i].getName());
					this.getWavetaggerListRecurrent(ftpClientLabel, ftpClientWave, ftpPath + "/" + ftpFiles[i].getName(), id, labelConnect, waveConnect, corpusFmt);
				}
				System.out.println("------------------------------");
			}
		} catch (Exception e) {
			System.out.println("文件路径不存在");
			e.printStackTrace();
		}
	}
	
	@Override
	public void getWavetaggerContext(FTPClient ftpClientLabel, FTPClient ftpClientWave, String ftpPath, String fileName, int id, FtpConnect waveConnect, FtpConnect labelConnect, CorpusFmt corpusFmt) {
		InputStream in = null;
		try {
			in = ftpClientLabel.retrieveFileStream(fileName);  
			ftpClientLabel.completePendingCommand();
			System.out.println(ftpPath + "/" + fileName);
		} catch (FileNotFoundException e) {
			System.out.println("没有找到" + ftpPath + "/" + fileName + "文件");
			e.printStackTrace();
		} catch (SocketException e) {
			System.out.println("连接ftp失败");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("文件读取失败");
			e.printStackTrace();
		}
		
		if (in != null) {  
			/*StringBuffer resultBuffer = new StringBuffer();*/
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            wavetagger.getWavetaggerDetail(br, ftpClientWave, ftpPath, id, waveConnect, corpusFmt);
        }else{  
        	System.out.println("in为空，不能读取。");
        }
		
	}
	
	@Override
	public void getPraatListRecurrent(FTPClient ftpClientLabel, FTPClient ftpClientWave, String ftpPath, int id, FtpConnect labelConnect, FtpConnect waveConnect, CorpusFmt corpusFmt) {
		try {
			FTPFile[] ftpFiles = ftpClientLabel.listFiles(ftpPath);
			
			for(int i = 0; i < ftpFiles.length; i++){
				if(ftpFiles[i].isFile()){
					String name = ftpFiles[i].getName().substring(0, ftpFiles[i].getName().lastIndexOf("."));
					name = ftpPath + "/" + name + ".wav";
					if("./".equals(name.substring(0,2))){
						name = name.substring(2, name.length());
					}
					Time time = wavetagger.checkWave(ftpClientWave, name, id, corpusFmt);
					if(time.getEndtime() != 0){
						getPraatContext(ftpClientLabel, ftpClientWave, name.substring(0, name.length()-4), id, time.getLength());
						ftpClientLabel.disconnect();
						ftpClientLabel = ftpService.getFtpClient(labelConnect);
					}else{
						System.out.println("音频文件格式不正确");
					} 
					
					ftpClientWave.disconnect();
					ftpClientWave = ftpService.getFtpClient(waveConnect);
				}else if (ftpFiles[i].isDirectory()) {
					System.out.println("目录：" + ftpFiles[i].getName());
					getPraatListRecurrent(ftpClientLabel, ftpClientWave, ftpPath, id, labelConnect, waveConnect, corpusFmt);
				}
				System.out.println("------------------------------");
			}
		} catch (Exception e) {
			System.out.println("文件路径不存在");
			e.printStackTrace();
		}
	}
	
	@Override
	public void getFileListRecurrent(FTPClient ftpClient, String ftpPath, int flag, int id, FtpConnect waveConnect, int resultID, CorpusFmt corpusFmt) {
		try {
			ftpClient = ftpService.getFtpClient(waveConnect);
			FTPFile[] ftpFiles = ftpClient.listFiles(ftpPath);
			
			for(int i = 0; i < ftpFiles.length; i++){
				if(ftpFiles[i].isFile()){
					//if(!".".equals(ftpPath)){
					if(flag == 1){
						String filename = ftpPath + "/" + ftpFiles[i].getName();
						if("./".equals(filename.substring(0,2)))
							filename = filename.substring(2, filename.length());
						
						Time time = wavetagger.checkWave(ftpClient, filename, id, corpusFmt);
						if(time.getEndtime() != 0){
							//将音频文件的标注结果存入数据库
							FileLabeled fileLabeled = new FileLabeled();
							fileLabeled.setWave(filename);
							fileLabeled.setTime(time.getEndtime());
							fileLabeled.setCorpus(resultID);
							fileLabeled.setLength(time.getLength());
							fileDao.insert2file(fileLabeled);
							fileDao.updateTimeById(fileLabeled);
						}else{
							System.out.println(ftpPath + "格式不正确");
						}
						ftpClient.disconnect();
						ftpClient = ftpService.getFtpClient(waveConnect);
					}					
				}else if (ftpFiles[i].isDirectory()) {
					if(flag == 0){
						String name = ftpFiles[i].getName();
						System.out.println("这是一种标注结果，标注结果为：" + name);
						FileLabeled fileLabeled = new FileLabeled();
						fileLabeled.setCorpus(id);
						fileLabeled.setResult(name);
						fileDao.insert2fileresult(fileLabeled);
						resultID = fileDao.selectIdByResult(fileLabeled);
					}
					getFileListRecurrent(ftpClient, ftpPath + "/" + ftpFiles[i].getName(), 1, id, waveConnect, resultID, corpusFmt);
				}
			}
			System.out.println("------------------------------");
		} catch (Exception e) {
			System.out.println("文件路径不存在");
			e.printStackTrace();
		}
		
	}

	

	@Override
	public void getPraatContext(FTPClient ftpClientLabel, FTPClient ftpClientWave, String ftpPath, int id, long length) {
		
		InputStream in = null;
		try {
			System.out.println(ftpPath + ".textgrid");
			in = ftpClientLabel.retrieveFileStream(ftpPath + ".textgrid");  
		} catch (FileNotFoundException e) {
			System.out.println("没有找到" + ftpPath + "文件");
			e.printStackTrace();
		} catch (SocketException e) {
			System.out.println("连接ftp失败");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("文件读取失败");
			e.printStackTrace();
		}
		
		if (in != null) {  
			/*StringBuffer resultBuffer = new StringBuffer();*/
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            praatService.getPraatDetail(br, ftpClientWave, id, ftpPath, length);
        }else{
        	System.out.println("in为空，不能读取。");
        }
		
		try {
			ftpClientLabel.changeToParentDirectory();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("改变路径失败");
		}
	}

	@Override
	public void getCorpusTime(int id) {
		// TODO Auto-generated method stub
		Corpus corpus = new Corpus();
		corpus = corpusDao.selectAllById(id);
		Wave wave = new Wave();
		wave.setCorpus(id);
		double totalTime = 0;
		if(corpus.getLabelType() == 1){
			List<Double> time = wavetaggerDao.selectTimeByCorpus(id);
			for(int i = 0; i < time.size(); i++){
				totalTime += time.get(i);
			}
			corpus.setTime(totalTime);
			corpusDao.updateTimeById(corpus);
		}else{
			totalTime = 0;
			if(corpus.getContext() == 1){
				List<Double> time = waveDao.selectTimeById("select context from wave where corpus = " + wave.getCorpus());
				for(int i = 0; i < time.size(); i++){
					totalTime += time.get(i);
				}
				wave.setTime(totalTime);
				fmtDao.updateTimeById("context = " + wave.getTime() + " where corpus = " + wave.getCorpus());
			}
			if (corpus.getGender() == 1) {
				totalTime = 0;
				List<Double> time = waveDao.selectTimeById("select gender from wave where corpus = " + wave.getCorpus());
				for(int i = 0; i < time.size(); i++){
					totalTime += time.get(i);
				}
				wave.setTime(totalTime);
				fmtDao.updateTimeById("gender = " + wave.getTime() + " where corpus = " + wave.getCorpus());
			}
			if (corpus.getLanguage() == 1) {
				totalTime = 0;
				List<Double> time = waveDao.selectTimeById("select language from wave where corpus = " + wave.getCorpus());
				for(int i = 0; i < time.size(); i++){
					totalTime += time.get(i);
				}
				wave.setTime(totalTime);
				fmtDao.updateTimeById("language = " + wave.getTime() + " where corpus = " + wave.getCorpus());
			}
			if (corpus.getSpeaker() == 1) {
				totalTime = 0;
				List<Double> time = waveDao.selectTimeById("select speaker from wave where corpus = " + wave.getCorpus());
				for(int i = 0; i < time.size(); i++){
					totalTime += time.get(i);
				}
				wave.setTime(totalTime);
				fmtDao.updateTimeById("speaker = " + wave.getTime() + " where corpus = " + wave.getCorpus());
			}
			if (corpus.getEffective() == 1) {
				totalTime = 0;
				List<Double> time = waveDao.selectTimeById("select effective from wave where corpus = " + wave.getCorpus());
				for(int i = 0; i < time.size(); i++){
					totalTime += time.get(i);
				}
				wave.setTime(totalTime);
				fmtDao.updateTimeById("effective = " + wave.getTime() + " where corpus = " + wave.getCorpus());
			}
		}
	}
}
