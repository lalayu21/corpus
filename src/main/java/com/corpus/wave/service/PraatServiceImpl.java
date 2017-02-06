package com.corpus.wave.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

import com.corpus.dao.PraatDao;
import com.corpus.dao.WaveDao;
import com.corpus.entity.ChangeLabeled;
import com.corpus.entity.GetIdList;
import com.corpus.entity.PraatDetailSelect;
import com.corpus.entity.PraatWave;
import com.corpus.entity.SelectWavelistPage;
import com.corpus.entity.Wave;
import com.corpus.entity.WaveList;
import com.corpus.entity.WaveTable;
import com.corpus.service.RegexService;

@Service
public class PraatServiceImpl implements PraatService {
	

	@Resource
	RegexService regexService;
	
	@Resource
	PraatDao praatDao;
	
	@Resource
	WaveDao waveDao;

	@Override
	public void getPraatDetail(BufferedReader br, FTPClient ftpClient, int id, String waveList, long length) {
		System.out.println(waveList);
		String data = null;
		
		int j = 0;
		try {

			WaveTable waveTable = new WaveTable();
			waveTable.setWaveList(waveList);
			waveTable.setCorpus(id);
			waveTable.setLength(length);
			praatDao.insertWave(waveTable);

			Wave wave1 = new Wave();
			wave1.setCorpus(id);
			wave1.setWave(waveList);
			
			int count = waveDao.selectCountByCorpusAndWave(wave1);
			
			if(count == 1){
				int waveId = waveDao.selectIdByCorpusAndWave(wave1);
				
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
						
						String result = regexService.sizeRegex(temp);
						if(result == null){
							result = regexService.nameRegex(temp);
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
				        		min = regexService.minRegex(data);
				        		
				        		
				        		data = br.readLine();
				        		data = data.replaceAll("\\s+", "");
				        		max = regexService.maxRegex(data);
				        		
				        		data = br.readLine();
				        		text = regexService.textRegex(data);

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
				        			
				        			praatDao.insertPraat(praat);
				        		}
				        	}
				        	
				        	wave.setTime(waveTime);
				        	
				        	praatDao.updateLabelType(name + " = 1 where id = " + id);
				        	waveDao.updateTimeById(name + " = " + wave.getTime() + " where id = " + wave.getId());
				        	
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

	@Override
	public void getPraatWave(FTPClient ftpClient, String ftpPath, String fileName) {
		// TODO Auto-generated method stub
		try {
			InputStream in = null;
			in = ftpClient.retrieveFileStream( ftpPath + "/" + fileName);
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
	}

	@Override
	public List<Integer> getIdByWaveList(int waveList, int type) {
		GetIdList temp = new GetIdList();
		temp.setWave(waveList);
		temp.setLabelType(type);
		return praatDao.selectIdByWaveList(temp);
	}

	@Override
	public List<WaveList> getWaveList(SelectWavelistPage selectWavelistPage) {
		// TODO Auto-generated method stub
		String sql = "";
		switch (selectWavelistPage.getType()) {
		case 2:
			sql = "select id, wave from wave where corpus = " + selectWavelistPage.getCorpus() + " and context > 0 limit " + selectWavelistPage.getStart() + ", " + selectWavelistPage.getEnd();
			break;

		case 3:
			sql = "select id, wave from wave where corpus = " + selectWavelistPage.getCorpus() + " and gender > 0 limit " + selectWavelistPage.getStart() + ", " + selectWavelistPage.getEnd();
			break;

		case 4:
			sql = "select id, wave from wave where corpus = " + selectWavelistPage.getCorpus() + " and speaker > 0 limit " + selectWavelistPage.getStart() + ", " + selectWavelistPage.getEnd();
			break;

		case 5:
			sql = "select id, wave from wave where corpus = " + selectWavelistPage.getCorpus() + " and language > 0 limit " + selectWavelistPage.getStart() + ", " + selectWavelistPage.getEnd();
			break;

		case 6:
			sql = "select id, wave from wave where corpus = " + selectWavelistPage.getCorpus() + " and effective > 0 limit " + selectWavelistPage.getStart() + ", " + selectWavelistPage.getEnd();
			break;
		default:
			break;
		}
		return praatDao.selectByCorpus(sql);
	}

	@Override
	public PraatDetailSelect getPraatLabeledDetail(int id, int labelType) {
		PraatDetailSelect temp = new PraatDetailSelect();
		temp = praatDao.selectLabelTypeById(id + " and labelType = " + labelType);
		/*switch (labelType) {
		case 0:
			temp = praatDao.selectLabelTypeById(id + " and labelType = " + labelType);
			break;
		case 1:
			temp = praatDao.selectGenderById(id);
			break;
		case 2:
			temp = praatDao.selectPersonById(id);
			break;
		case 3:
			temp = praatDao.selectLanguageById(id);
			break;
		case 4:
			temp = praatDao.selectEffectiveById(id);
		default:
			break;
		}*/
		return temp;
	}

	@Override
	public void changeLabeledPraatService(int id, String result) {
		// TODO Auto-generated method stub
		ChangeLabeled temp = new ChangeLabeled();
		temp.setId(id);
		temp.setResult(result);
		praatDao.updateLabeledPraat(temp);
	}

}
