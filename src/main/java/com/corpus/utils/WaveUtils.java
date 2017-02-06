package com.corpus.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javax.management.Query;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.net.ftp.FTPClient;

import com.corpus.entity.Corpus;
import com.corpus.entity.CorpusFmt;
import com.corpus.entity.Time;
import com.corpus.entity.Wave;

public class WaveUtils {
	private FTPClient ftpClient;
	private String ftpPath;
	private CorpusFmt corpusFmt;
	private QueryRunner queryRunner;
	
	public WaveUtils(FTPClient ftpClient, String ftpPath, CorpusFmt corpusFmt){
		this.ftpClient = ftpClient;
		this.ftpPath = ftpPath;
		this.corpusFmt = corpusFmt;
	}
	
	public WaveUtils(QueryRunner queryRunner){
		this.queryRunner = queryRunner;
	}
	
	public Time checkWave(){
		Time time = new Time();
		time.setStarttime(0);
		time.setEndtime(0);
		InputStream in = null;
		try {
			in = ftpClient.retrieveFileStream(ftpPath);
		} catch (FileNotFoundException e) {
			System.out.println("没有找到" + ftpPath + "文件");
			return time;
			//e.printStackTrace();
		} catch (SocketException e) {
			System.out.println("连接ftp失败");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("文件读取失败");
			e.printStackTrace();
		}
		
		
		if(in == null){
			System.out.println(ftpPath + "  该文件为空");
			return time;
		}else{
			//将inputstream转成byte
			BufferedInputStream bufin = new BufferedInputStream(in);
	        int buffSize = 0;
	        try {
	        	while (buffSize == 0) {
					buffSize = in.available();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	        
	        ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);
	        
	        byte[] temp = new byte[buffSize];  
	        int size = 0;  
	        
	        try {
				while ((size = bufin.read(temp)) != -1) {  
				    out.write(temp, 0, size);  
				}
		        bufin.close();  
			} catch (IOException e) {
				e.printStackTrace();
			}  
	        //content中存放的是音频文件的二进制格式
	        byte[] content = out.toByteArray(); 
	        System.out.println(content.length);
	        boolean flag = true;
	        //判断文件的格式是否正确
	        
	        //CorpusFmt corpusFmt = corpusDao.selectFmtById(corpus);
	        
	        //根据用户设置的属性判断音频文件是否有头
	        if(corpusFmt.getHead() == 1){
	        	//根据读取文件头，判断音频文件是否有头
	        	if((content[0]&0xff) == 0x52 && (content[1]&0xff) == 0x49 && (content[2]&0xff) == 0x46 && (content[3]&0xff) == 0x46){
	        		//有头，判断用户设置的格式与文件头中的格式是否一致
		        	System.out.println("有头");
		        	//判断音频格式是否为wavefmt
		        	if((content[8]&0xff) == 0x57 && (content[9]&0xff) == 0x41 && (content[10]&0xff) == 0x56 && (content[11]&0xff) == 0x45 && (content[12]&0xff) == 0x66 && (content[13]&0xff) == 0x6d && (content[14]&0xff) == 0x74 && (content[15]&0xff) == 0x20){
		        		System.out.println("the type id wavefmt");
		        	}else
		        		flag = false;
		        	//判断声道数
			        if((content[22]&0xff) == 0x01 && (content[23]&0xff) == 0x00){
			        	System.out.println("声道数为1");
			        	if(corpusFmt.getChannel() != 1)
			        		flag = false;
			        }else if ((content[22]&0xff) == 0x02 && (content[23]&0xff) == 0x00) {
						System.out.println("声道数为2");
						if(corpusFmt.getChannel() != 2)
							flag = false;
					}else{
						System.out.println("声道数未知");
						flag = false;
					}
			      //获取采样率
			        long sample = (long) ((content[24]&0xff) + (content[25]&0xff) * Math.pow(2, 8) + (content[26]&0xff) * Math.pow(2, 16) + (content[27]&0xff) * Math.pow(2, 24));
			        if(sample == 8000){
			        	System.out.println("采样频率为8k");
			        	if(corpusFmt.getSample() != 0)
			        		flag = false;
			        }else if (sample == 16000) {
						System.out.println("采样频率为16k");
						if(corpusFmt.getSample() != 1)
							flag = false;
					}else{
						System.out.println("采样频率为" + sample);
						flag = false;
					}
			      //获取时长
			        long length = (long) ((content[4]&0xff) + (content[5]&0xff) * Math.pow(2, 8) + (content[6]&0xff) * Math.pow(2, 16) + (content[7]&0xff) * Math.pow(2, 24));
					time.setLength(length);
					
			        long sec = (long) ((content[28]&0xff) + (content[29]&0xff) * Math.pow(2, 8) + (content[30]&0xff) * Math.pow(2, 16) + (content[31]&0xff) *Math.pow(2, 24));
			        double duration = (double)length / (double)sec;
			        System.out.println(duration);
					time.setEndtime(duration);
					
					//获取量化数
			        if((content[16]&0xff) == 0x10 && (content[17]&0xff) ==  0x00 && (content[18]&0xff) == 0x00 && (content[19]&0xff) == 0x00){
			        	System.out.println("头为线性pcm");
			        	if(corpusFmt.getCode() != 0)
			        		flag = false;
			        	else {
			        		//获取量化数
				        	int bitpersamples = (int) ((content[34]&0xff) + (content[35]&0xff) * Math.pow(2, 8));
				        	System.out.println("量化数为" + bitpersamples);
						}
			        }else if ((content[16]&0xff) == 0x12 && (content[17]&0xff) ==  0x00 && (content[18]&0xff) == 0x00 && (content[19]&0xff) == 0x00) {
						System.out.println("头为alaw或者ulaw");
						if(corpusFmt.getCode() == 0)
							flag = false;
						else {
							//获取量化数
							int bitpersamples = (int) ((content[34]&0xff) + (content[35]&0xff) * Math.pow(2, 8) + (content[36]&0xff) * Math.pow(2, 16) + (content[37]&0xff) * Math.pow(2, 24));
				        	System.out.println("量化数为" + bitpersamples);
						}
					}else {
						flag = false;
					}
		        }else{//无头，将用户设置的格式写入文件头
		        	flag = false;
		        }
	        }else{
	        	int headLength = 0;
	        	byte[] waveHead = null;
	        	if(corpusFmt.getCode() == 0){
	        		headLength = 44;
	        		waveHead = new byte[44];
	        	}else{
	        		headLength = 58;
	        		waveHead = new byte[58];
	        	}
	        	//文件头标识REFF(content[0]&0xff) == 0x52 && (content[1]&0xff) == 0x49 && (content[2]&0xff) == 0x46 && (content[3]&0xff) == 0x46
	        	byte[] headTag = {0x52, 0x49, 0x46, 0x46};
	        	System.arraycopy(headTag, 0, waveHead, 0, 4);
	        	//文件长度:
	        	long waveSize = content.length + headLength - 8;
	        	byte[] waveLength = {(byte) (waveSize%(Math.pow(2, 8))), (byte) (waveSize%(Math.pow(2, 16))/(Math.pow(2, 8))), (byte) (waveSize%Math.pow(2, 24)/Math.pow(2, 16)), (byte) (waveSize/Math.pow(2, 24))};
	        	System.arraycopy(waveLength, 0, waveHead, 4, 4);
	        	
	        	//wavefmt(content[8]&0xff) == 0x57 && (content[9]&0xff) == 0x41 && (content[10]&0xff) == 0x56 && (content[11]&0xff) == 0x45 && (content[12]&0xff) == 0x66 && (content[13]&0xff) == 0x6d && (content[14]&0xff) == 0x74 && (content[15]&0xff) == 0x20
	        	byte[] wavefmt = {0x57, 0x41, 0x56, 0x45, 0x66, 0x6d, 0x74, 0x20};
	        	System.arraycopy(wavefmt, 0, waveHead, 8, 8);
	        	
	        	//判断是线性pcm还是压缩
	        	if(corpusFmt.getCode() == 0){
	        		byte[] pcm = {0x10, 0x00, 0x00, 0x00, 0x10, 0x00};
	        		System.arraycopy(pcm, 0, waveHead, 16, 6);
	        	}else if (corpusFmt.getCode() == 1) {
	        		byte[] pcm = {0x12, 0x00, 0x00, 0x00, 0x06, 0x00};
	        		System.arraycopy(pcm, 0, waveHead, 16, 6);
				}else if (corpusFmt.getCode() == 2) {
	        		byte[] pcm = {0x12, 0x00, 0x00, 0x00, 0x07, 0x00};
	        		System.arraycopy(pcm, 0, waveHead, 16, 6);
				}
	        	
	        	//通道数
	        	int channel_int = 0;
	        	if(corpusFmt.getChannel() == 1){
	        		byte[] channel = {0x01, 0x00};
	        		channel_int = 1;
	        		System.arraycopy(channel, 0, waveHead, 22, 2);
	        	}else if (corpusFmt.getChannel() == 1){
	        		byte[] channel = {0x02, 0x00};
	        		channel_int = 2;
	        		System.arraycopy(channel, 0, waveHead, 22, 2);
	        	}
	        	
	        	//采样率
	        	long sampletemp = 0;
	        	if(corpusFmt.getSample() == 0){
	        		sampletemp = 8000;
	        	}else {
					sampletemp = 16000;
				}
	        	byte[] sample = {(byte) (sampletemp%Math.pow(2, 8)), (byte) (sampletemp%Math.pow(2, 16)/Math.pow(2, 8)), (byte) (sampletemp%Math.pow(2, 24)/Math.pow(2, 16)), (byte) (sampletemp/Math.pow(2, 24))};
	        	System.arraycopy(sample, 0, waveHead, 24, 4);
	        	
	        	//量化数
	        	byte[] bitpersamples = null;
	        	int bitpersamples_int = 0;
	        	if(corpusFmt.getCode() == 0 && corpusFmt.getBitpersamples() == 0){
	        		bitpersamples = new byte[2];
	        		bitpersamples_int = 8;
	        		byte[] bittemp = {0x10, 0x00};
	        		System.arraycopy(bittemp, 0, bitpersamples, 0, 2);
	        	}else if (corpusFmt.getCode() == 0 && corpusFmt.getBitpersamples() == 1) {
	        		bitpersamples = new byte[2];
	        		bitpersamples_int = 16;
	        		byte[] bittemp = {0x00, 0x01};
	        		System.arraycopy(bittemp, 0, bitpersamples, 0, 2);
				}else if (corpusFmt.getCode() != 0 && corpusFmt.getBitpersamples() == 0) {
	        		bitpersamples = new byte[4];
	        		bitpersamples_int = 8;
	        		byte[] bittemp = {0x10, 0x00, 0x00, 0x00};
	        		System.arraycopy(bittemp, 0, bitpersamples, 0, 4);
				}else if (corpusFmt.getCode() != 0 && corpusFmt.getBitpersamples() == 1) {
	        		bitpersamples = new byte[4];
	        		bitpersamples_int = 16;
	        		byte[] bittemp = {0x00, 0x01, 0x00, 0x00};
	        		System.arraycopy(bittemp, 0, bitpersamples, 0, 4);
				}
	        	
	        	//采样一次所占字节数
	        	int bitspersample_int = channel_int * bitpersamples_int / 8;
	        	byte[] bitspersample = {(byte) bitspersample_int, 0x00};
	        	
	        	//每秒播放的字节数
	        	long bitspersecond_int = bitspersample_int * sampletemp;
	        	byte[] bitspersecond = {(byte) (bitspersecond_int%Math.pow(2, 8)), (byte) (bitspersecond_int%Math.pow(2, 16)/Math.pow(2, 8)), (byte) (bitspersecond_int%Math.pow(2, 24)/Math.pow(2, 16)), (byte) (bitspersecond_int/Math.pow(2, 24))};
	        	System.arraycopy(bitspersecond, 0, waveHead, 28, 4);
	        	
	        	System.arraycopy(bitspersample, 0, waveHead, 32, 2);
	        	
	        	byte[] data = {0x44, 0x41, 0x54, 0x41};
	        	if(corpusFmt.getCode() == 0){
	        		System.arraycopy(bitpersamples, 0, waveHead, 34, 2);
	        		System.arraycopy(data, 0, waveHead, 36, 4);
	        		System.arraycopy(temp, 0, waveHead, 40, 4);
	        	}else{
	        		System.arraycopy(bitpersamples, 0, waveHead, 34, 4);
	        		byte[] fact = {0x46, 0x41, 0x43, 0x54};
	        		System.arraycopy(bitpersamples, 0, waveHead, 38, 4);
	        		//0400000000530700
	        		byte[] tagfixed = {0x04, 0x00, 0x00, 0x00, 0x00, 0x53, 0x07, 0x00};
	        		System.arraycopy(bitpersamples, 0, waveHead, 42, 8);
	        		System.arraycopy(data, 0, waveHead, 50, 4);
	        		System.arraycopy(temp, 0, waveHead, 54, 4);
	        	}	
	        	
	        	//写入文件
	        	byte[] finalWave = new byte[buffSize + headLength];
	        	System.arraycopy(waveHead, 0, finalWave, 0, waveHead.length);
	        	System.arraycopy(content, 0, finalWave, waveHead.length, buffSize);
	        	//OutputStream os = new FileOutputStream(ftpClient.retrieveFileStream(ftpPath));
	        	try {
		        	BufferedOutputStream oStream = new BufferedOutputStream(ftpClient.appendFileStream(ftpPath));
		        	oStream.write(finalWave, 0, finalWave.length);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("写入文件失败");
					e.printStackTrace();
				}
	        }
	        if(flag){
	        	System.out.println(ftpPath + "格式正确，开始导入");
				time.setStarttime(0);
	        }else{
	        	time = null;
	        }
			
			return time;
		}
	}
	
	public void getCorpusTime(int id){
		Corpus corpus = new Corpus();
		
		String sql_corpus = "select name, type, labelType, wavePath, labelPath, desp, context, gender, speaker, language, effective, from corpus where id = " + id;
		try {
			corpus = queryRunner.query(sql_corpus, new BeanHandler<Corpus>(Corpus.class));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("corpus = queryRunner.query(sql_corpus, new BeanHandler<Corpus>(Corpus.class))失败");
		}
		
		Wave wave = new Wave();
		wave.setCorpus(id);
		double totalTime = 0;
		
		if(corpus.getLabelType() == 1){
			
			String sql_time = "select time from wavetagger where corpus = " + id;
			List<Double> time = new ArrayList<Double>();
			try {
				time = queryRunner.query(sql_time, new BeanListHandler<Double>(Double.class));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("time = queryRunner.query(sql_time, new BeanListHandler<Double>(Double.class))失败");
			}
			
			//List<Double> time = wavetaggerDao.selectTimeByCorpus(id);
			for(int i = 0; i < time.size(); i++){
				totalTime += time.get(i);
			}
			corpus.setTime(totalTime);
			
			String sql_updateTimeById = "update corpus set time = ? where id = ?";
			try {
				queryRunner.update(sql_updateTimeById, totalTime, id);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("queryRunner.update(sql_updateTimeById, totalTime, id);失败");
			}
			
		}else{
			totalTime = 0;
			String sql_selectTimeById = "";
			String sql_updateFmtTime = "";
			if(corpus.getContext() == 1){
				sql_selectTimeById = "select context from wave where corpus = " + id;
				List<Double> time = new ArrayList<Double>();
				try {
					time = queryRunner.query(sql_selectTimeById, new BeanListHandler<Double>(Double.class));
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("time = queryRunner.query(sql_selectTimeById, new BeanListHandler<Double>(Double.class))失败");
				}
				for(int i = 0; i < time.size(); i++){
					totalTime += time.get(i);
				}
				sql_updateFmtTime = "update corpusFmt set context = ? where corpus = ?";
				
				try {
					queryRunner.update(sql_updateFmtTime, totalTime, id);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("queryRunner.update(sql_updateFmtTime, totalTime, id);失败");
				}
			}
			if (corpus.getGender() == 1) {
				totalTime = 0;
				sql_selectTimeById = "select gender from wave where corpus = " + id;
				List<Double> time = new ArrayList<Double>();
				try {
					time = queryRunner.query(sql_selectTimeById, new BeanListHandler<Double>(Double.class));
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("time = queryRunner.query(sql_selectTimeById, new BeanListHandler<Double>(Double.class));失败");
				}
				for(int i = 0; i < time.size(); i++){
					totalTime += time.get(i);
				}
				sql_updateFmtTime = "update corpusFmt set gender = ? where corpus = ?";
				
				try {
					queryRunner.update(sql_updateFmtTime, totalTime, id);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("queryRunner.update(sql_updateFmtTime, totalTime, id);失败");
				}
			}
			if (corpus.getLanguage() == 1) {
				totalTime = 0;
				sql_selectTimeById = "select language from wave where corpus = " + id;
				List<Double> time = new ArrayList<Double>();
				try {
					time = queryRunner.query(sql_selectTimeById, new BeanListHandler<Double>(Double.class));
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("time = queryRunner.query(sql_selectTimeById, new BeanListHandler<Double>(Double.class));失败");
				}
				for(int i = 0; i < time.size(); i++){
					totalTime += time.get(i);
				}
				sql_updateFmtTime = "update corpusFmt set language = ? where corpus = ?";
				
				try {
					time = queryRunner.query(sql_selectTimeById, new BeanListHandler<Double>(Double.class));
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("queryRunner.update(sql_updateFmtTime, totalTime, id);失败");
				}
			}
			if (corpus.getSpeaker() == 1) {
				totalTime = 0;
				sql_selectTimeById = "select speaker from wave where corpus = " + id;
				List<Double> time = new ArrayList<Double>();
				try {
					time = queryRunner.query(sql_selectTimeById, new BeanListHandler<Double>(Double.class));
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("time = queryRunner.query(sql_selectTimeById, new BeanListHandler<Double>(Double.class))失败");
				}
				for(int i = 0; i < time.size(); i++){
					totalTime += time.get(i);
				}
				sql_updateFmtTime = "update corpusFmt set sp = ? where corpus = ?";
				
				try {
					queryRunner.update(sql_updateFmtTime, totalTime, id);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("queryRunner.update(sql_updateFmtTime, totalTime, id);失败");
				}
			}
			if (corpus.getEffective() == 1) {
				totalTime = 0;
				sql_selectTimeById = "select effective from wave where corpus = " + id;
				List<Double> time = new ArrayList<Double>();
				try {
					time = queryRunner.query(sql_selectTimeById, new BeanListHandler<Double>(Double.class));
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("time = queryRunner.query(sql_selectTimeById, new BeanListHandler<Double>(Double.class))失败");
				}
				for(int i = 0; i < time.size(); i++){
					totalTime += time.get(i);
				}
				sql_updateFmtTime = "update corpusFmt set effective = ? where corpus = ?";
				
				try {
					queryRunner.update(sql_updateFmtTime, totalTime, id);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("queryRunner.update(sql_updateFmtTime, totalTime, id);失败");
				}
			}
		}
	}
}
