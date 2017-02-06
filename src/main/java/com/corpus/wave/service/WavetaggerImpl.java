package com.corpus.wave.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.FastArrayList;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

import com.corpus.dao.CorpusDao;
import com.corpus.dao.WavetaggerDao;
import com.corpus.entity.ChangeLabeled;
import com.corpus.entity.Corpus;
import com.corpus.entity.CorpusFmt;
import com.corpus.entity.FtpConnect;
import com.corpus.entity.SelectWavelistPage;
import com.corpus.entity.Time;
import com.corpus.entity.Wave;
import com.corpus.entity.WaveList;
import com.corpus.entity.WavetaggerWave;
import com.corpus.service.FTPService;
import com.corpus.service.RegexService;
import com.corpus.ssh.RmtShellExecutor;

import javassist.bytecode.ByteArray;
import sun.util.logging.resources.logging;

@Service
public class WavetaggerImpl implements Wavetagger {
	
	@Resource
	WavetaggerDao wavetaggerDao;

	@Resource
	RegexService regexService;
	
	@Resource
	CorpusDao corpusDao;
	
	@Resource
	FTPService ftpService;

	@Override
	public List<WaveList> getName(SelectWavelistPage selectWavelistPage) {
		// TODO Auto-generated method stub
		return wavetaggerDao.select(selectWavelistPage);
	}

	@Override
	public WavetaggerWave getDetail(int id) {
		// TODO Auto-generated method stub
		return wavetaggerDao.selectDetail(id);
	}

	@Override
	public void getWavetaggerDetail(BufferedReader br, FTPClient ftpClient, String ftpPath, int id, FtpConnect waveConnect, CorpusFmt corpusFmt) {
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
					if(wavetaggerDao.selectCountByWavePathAndCorpus(wave2) == 0){
						Time time = checkWave(ftpClient, fileName, id, corpusFmt);
						
						ftpClient.disconnect();
						ftpClient = ftpService.getFtpClient(waveConnect);
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
								
								double totalTime = corpusDao.selectTimeById(id) + partTime;
								
								Corpus corpus = new Corpus();
								corpus.setId(id);
								corpus.setTime(totalTime);
								
								corpusDao.updateTimeById(corpus);
								
								wavetaggerDao.insertWave(wave);
							}else{
								System.out.println("音频文件不存在或者格式不正确");
							}
						}
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
	

	@Override
	public Time checkWave(FTPClient ftpClient, String ftpPath, int corpus, CorpusFmt corpusFmt) {
		// TODO Auto-generated method stub

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
	

	@Override
	public void changeLabeledWavetaggerService(int id, String result, String other) {
		// TODO Auto-generated method stub
		ChangeLabeled temp = new ChangeLabeled();
		temp.setId(id);
		temp.setResult(result);
		temp.setOther(other);
		wavetaggerDao.updateLabeledWavetagger(temp);
	}
}
