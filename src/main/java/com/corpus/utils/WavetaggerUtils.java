package com.corpus.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.corpus.entity.Corpus;
import com.corpus.entity.CorpusFmt;
import com.corpus.entity.FtpConnect;
import com.corpus.entity.Time;
import com.corpus.entity.Wave;
import com.corpus.entity.WavetaggerWave;

public class WavetaggerUtils {
	
	private FTPClient ftpClientLabel;
	private FTPClient ftpClientWave;
	private int id;
	private FtpConnect labelConnect;
	private FtpConnect waveConnect;
	private QueryRunner queryRunner;
	private CorpusFmt corpusFmt;
	
	public WavetaggerUtils(FTPClient ftpClientLabel, FTPClient ftpClientWave, int id, FtpConnect labelConnect, FtpConnect waveConnect, CorpusFmt corpusFmt, QueryRunner queryRunner){
		this.ftpClientLabel = ftpClientLabel;
		this.ftpClientWave = ftpClientWave;
		this.id = id;
		this.labelConnect = labelConnect;
		this.waveConnect = waveConnect;
		this.queryRunner = queryRunner;
	}
	
	public void getWavetaggerListRecurrent( String ftpPath) {
		try {
			FTPFile[] ftpFiles = ftpClientLabel.listFiles(ftpPath);
			
			for(int i = 0; i < ftpFiles.length; i++){
				if(ftpFiles[i].isFile()){
					System.out.println(ftpFiles[i].getName());
					this.getWavetaggerContext(ftpPath, ftpFiles[i].getName());
				
					FTPClientUtils ftpClientUtils = new FTPClientUtils();
					ftpClientLabel.disconnect();
					ftpClientLabel = ftpClientUtils.getFTPClient(labelConnect);
					
					ftpClientWave.disconnect();
					ftpClientWave = ftpClientUtils.getFTPClient(waveConnect);
				}else if (ftpFiles[i].isDirectory()) {
					System.out.println("目录：" + ftpFiles[i].getName());
					WavetaggerUtils wavetaggerUtils = new WavetaggerUtils(ftpClientLabel, ftpClientWave, id, labelConnect, waveConnect, corpusFmt, queryRunner);
					wavetaggerUtils.getWavetaggerListRecurrent(ftpPath + "/" + ftpFiles[i].getName());
				}
				System.out.println("------------------------------");
			}
		} catch (Exception e) {
			System.out.println("文件路径不存在");
			e.printStackTrace();
		}
	}

	public void getWavetaggerContext(String ftpPath, String fileName) {
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
	        this.getWavetaggerDetail(br, ftpPath);
	    }else{  
	    	System.out.println("in为空，不能读取。");
	    }
	}
	
	public void getWavetaggerDetail(BufferedReader br, String ftpPath) {
		// TODO Auto-generated method stub
		String data = null;
		try{
			while((data = br.readLine()) != null){
				System.out.println(data);
				
				String[] detail = data.split("=");
				
				WavetaggerWave wave = new WavetaggerWave();
				
				if (detail.length == 6) {
					
					String fileName = ftpPath + "/" + detail[0] + ".wav";
					if("./".equals(fileName.substring(0,2))){
						fileName = fileName.substring(2,fileName.length());
					}
					
					Wave wave2 = new Wave();
					wave2.setCorpus(id);
					wave2.setWave(fileName);
					//select count(id) from wavetagger where wave = #{wave} and corpus = #{corpus}
					try {
						if(queryRunner.query("select count(id) from wavetagger where wave = " + fileName + "and corpus = " + id, new BeanHandler<Integer>(Integer.class)) == 0){
							
							WaveUtils waveUtils = new WaveUtils(ftpClientWave, fileName, corpusFmt);
							Time time = waveUtils.checkWave();
							
							FTPClientUtils ftpClientUtils = new FTPClientUtils();
							
							ftpClientWave.disconnect();
							ftpClientWave = ftpClientUtils.getFTPClient(waveConnect);
							if(time == null){
								/*Corpus corpusIP = new Corpus();
								corpusIP.setId(id);
								corpusIP.setFileType(0);
								corpusIP = corpusDao.selectIp(corpusIP);
								RmtShellExecutor executor = new RmtShellExecutor(corpusIP.getIp(), corpusIP.getUsername(), corpusIP.getPassword());
								try {
									executor.exec("mv " + fileName + " " + fileName + "/../wave_delete");
								} catch (Exception e) {
									// TODO: handle exception
									System.out.println("移动失败");
								}*/
							}else{
								if(time.getEndtime() != 0){
									wave.setWave(fileName);
									wave.setContext(detail[2]);
									wave.setOther(detail[5]);
									
									if(detail[3] == null){
										wave.setStarttime(time.getStarttime() + "");
										wave.setEndtime(time.getEndtime() + "");
									}else{
										if(detail[3].contains(".")){
											wave.setStarttime(detail[3].substring(0, detail[3].indexOf(".")) + "." + detail[3].substring(detail[3].indexOf(".")+1, detail[3].length()));
										}else{
											wave.setStarttime(detail[3]);
										}
										if(detail[4].contains(".")){
											wave.setEndtime(detail[4].substring(0, detail[4].indexOf(".")) + "." + detail[4].substring(detail[4].indexOf(".")+1, detail[4].length()));
										}else{
											wave.setEndtime(detail[4]);
										}
									}
									
									double partTime = Double.parseDouble(wave.getEndtime()) - Double.parseDouble(wave.getStarttime());
									
									wave.setTime(partTime);
									wave.setCorpus(id);
									wave.setLength(time.getLength());
									
									double totalTime = queryRunner.query("select time from corpus where id = " + id, new BeanHandler<Double>(Double.class)) + partTime;
									
									Corpus corpus = new Corpus();
									corpus.setId(id);
									corpus.setTime(totalTime);
									
									queryRunner.update("update corpus set time = " + time + " where id = " + id);
									
									queryRunner.update("insert into wavetagger(wave, context, other, starttime, endtime, corpus, length, time values(?, ?, ?, ?, ?, ?, ?, ?)", wave.getWave(), wave.getContext(), wave.getOther(), wave.getStarttime(), wave.getEndtime(), wave.getCorpus(), wave.getLength(), wave.getTime());
								}else{
									System.out.println("音频文件不存在或者格式不正确");
								}
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("连接数据库出错，wavetagger create getWavetaggerDetail");
					}
				}else{
					System.out.println("内容格式不正确： " + data);
				}
			}
		}catch (IOException e) {  
        	System.out.println("文件读取错误。");
            e.printStackTrace();  
        }finally{
            /*try {  
                ftpClient.disconnect();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  */
        }
	}
	
}
