package com.corpus.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.corpus.entity.Corpus;
import com.corpus.entity.CorpusFmt;
import com.corpus.entity.FtpConnect;
import com.corpus.entity.PraatWave;
import com.corpus.entity.Time;
import com.corpus.entity.Wave;
import com.mchange.v2.async.StrandedTaskReporting;

public class PraatUtils {
	private FTPClient ftpClientLabel;
	private FTPClient ftpClientWave;
	private int id;
	private FtpConnect labelConnect;
	private FtpConnect waveConnect;
	private CorpusFmt corpusFmt;
	private QueryRunner queryRunner;
	
	public PraatUtils(FTPClient ftpClientLabel, FTPClient ftpClientWave, int id, FtpConnect labelConnect, FtpConnect waveConnect, CorpusFmt corpusFmt, QueryRunner queryRunner){
		this.corpusFmt = corpusFmt;
		this.ftpClientLabel = ftpClientLabel;
		this.ftpClientWave = ftpClientWave;
		this.id = id;
		this.labelConnect = labelConnect;
		this.waveConnect = waveConnect;
		this.queryRunner = queryRunner;
	}
	
	public void getPraatListRecurrent(String ftpPath){
		try {
			FTPFile[] ftpFiles = ftpClientLabel.listFiles(ftpPath);
			
			for(int i = 0; i < ftpFiles.length; i++){
				if(ftpFiles[i].isFile()){
					String name = ftpFiles[i].getName().substring(0, ftpFiles[i].getName().lastIndexOf("."));
					name = ftpPath + "/" + name + ".wav";
					if("./".equals(name.substring(0,2))){
						name = name.substring(2, name.length());
					}
					FTPClientUtils ftpClientUtils = new FTPClientUtils();
					WaveUtils waveUtils = new WaveUtils(ftpClientWave, ftpPath, corpusFmt);
					Time time = waveUtils.checkWave();
					if(time.getEndtime() != 0){
						getPraatContext(name.substring(0, name.length()-4), time.getLength());
						ftpClientLabel.disconnect();
						ftpClientLabel = ftpClientUtils.getFTPClient(labelConnect);
					}else{
						System.out.println("音频文件格式不正确");
					}
					
					ftpClientWave.disconnect();
					ftpClientWave = ftpClientUtils.getFTPClient(waveConnect);
				}else if (ftpFiles[i].isDirectory()) {
					System.out.println("目录：" + ftpFiles[i].getName());
					getPraatListRecurrent(ftpPath + "/" + ftpFiles[i].getName());
				}
				System.out.println("------------------------------");
			}
		} catch (Exception e) {
			System.out.println("文件路径不存在");
			e.printStackTrace();
		}
		
		
	}
	
	public void getPraatContext(String ftpPath, long length) {
		
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
            getPraatDetail(br, ftpPath, length);
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
	
	//获取细节
	public void getPraatDetail(BufferedReader br, String waveList, long length) {
		System.out.println(waveList);
		String data = null;
		
		int j = 0;
		try {
			String sql = "insert into wave(wave, corpus, lenght) values(?, ?, ?)";
			queryRunner.update(sql, waveList, id, length);

			
			//select count(id) from wave where corpus = #{corpus} and wave = #{wave}
			String sql_count = "select count(id) from wave where corpus = ? and wave = ?";
			//这里可能会报错
			int count = queryRunner.query(sql_count,new BeanHandler<Integer>(Integer.class), id, waveList);
			
			if(count == 1){
				//select id from wave where corpus = #{corpus} and wave = #{wave}
				String sql_waveId = "select id from wave where corpus = ? and wave = ?";
				int waveId = queryRunner.query(sql_waveId, new BeanHandler<Integer>(Integer.class), id, waveList);
				
				int item = 0, interval = 0;
				String name = "";
				data = br.readLine();
				
				
				if(!data.equals("File type = \"ooTextFile\"")){
					System.out.println(waveList + "格式不正确");
				}else{
					while((data = br.readLine()) != null){

						PraatWave praat = new PraatWave();  
						System.out.println(data);
						
						String temp = data.replaceAll("\\s+", "");
						
						RegexUtils regexUtils = new RegexUtils();
						
						String result = regexUtils.sizeRegex(temp);
						if(result == null){
							result = regexUtils.nameRegex(temp);
							if (result != null) {
								name = result;if("context".equals(name)){
			        				praat.setLabelType(0);
			        			}else if ("person".equals(name)) {
			        				praat.setLabelType(2);
								}else if ("gender".equals(name)) {
			        				praat.setLabelType(1);
								}else if ("effective".equals(name)) {
			        				praat.setLabelType(4);
								}else if ("language".equals(name)) {
			        				praat.setLabelType(3);
								}
							}
						}else if (item == 0) {
							item = Integer.parseInt(result);
						}else {
			        		double waveTime = 0;
		        			Wave wave = new Wave();
		        			wave.setId(waveId);      			

							interval = Integer.parseInt(result);
							
							String min = "";
							String max = "";
							String text = "";
				        	
				        	for(int i = 0; i < interval; i++){
				        		j = i;
				        		
				        		data = br.readLine();
				        		data = br.readLine();
				        		
				        		data = data.replaceAll("\\s+", "");
				        		min = regexUtils.minRegex(data);
				        		
				        		
				        		data = br.readLine();
				        		data = data.replaceAll("\\s+", "");
				        		max = regexUtils.maxRegex(data);
				        		
				        		data = br.readLine();
				        		text = regexUtils.textRegex(data);

				        		text = text.replaceAll("\\s+", "");
				        		//将数据写入数据库:是否将context、性别、说话人等区分成多个表
				        		if(text != null && !"".equals(text)){
				        			
				        			praat.setStarttime(min);
				        			praat.setEndtime(max);
				        			praat.setResult(text);
				        			praat.setWaveList(waveId);
				        			
				        			//将wave总时间存入数据库wave表中
				        			try {
				        				waveTime += Double.parseDouble(max) - Double.parseDouble(min);
									} catch (Exception e) {
										System.out.println(j + "行数据出错");
									}
				        			
				        			String sql_insertPraat = "insert into praat(wave, result, starttime, endtime, labelType) values(?, ?, ?, ?, ?)";
				        			queryRunner.update(sql_insertPraat, waveId, text, min, max, praat.getLabelType());
				        		}
				        	}
				        	
				        	wave.setTime(waveTime);
				        	String sql_updateLabelType = "update corpus set name = 1 where id = " + id;
				        	queryRunner.update(sql_updateLabelType);
				        	
				        	String sql_updateTimeById = "update wave set " + name + " = " + waveTime + " where id = " + waveId;
				        	queryRunner.update(sql_updateTimeById);
				        	
			        		/*if("context".equals(name)){
		        				praatDao.updateContext(id);
		        				waveDao.updateTimeByIdContext(wave);
		        			}else if ("person".equals(name)) {
		        				praatDao.updatePerson(id);
		        				waveDao.updateTimeByIdSpeaker(wave);
							}else if ("gender".equals(name)) {
		        				praatDao.updateGender(id);
		        				waveDao.updateTimeByIdGender(wave);
							}else if ("effective".equals(name)) {
		        				praatDao.updateEffective(id);
		        				waveDao.updateTimeByIdEffective(wave);
							}else if ("language".equals(name)) {
		        				praatDao.updatelanguange(id);
		        				waveDao.updateTimeByIdLanguage(wave);
							}*/
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("第" + j + "行数据出错");
		}
	}
	
	
}
