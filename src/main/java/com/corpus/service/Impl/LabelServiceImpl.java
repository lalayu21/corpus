package com.corpus.service.Impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.corpus.dao.AttributesDao;
import com.corpus.dao.CorpusDao;
import com.corpus.dao.PraatDao;
import com.corpus.dao.TrainAndTestDao;
import com.corpus.dao.WaveDao;
import com.corpus.dao.WavetaggerDao;
import com.corpus.entity.AttributeValues;
import com.corpus.entity.Attributes;
import com.corpus.entity.AttributesAndValue;
import com.corpus.entity.Corpus;
import com.corpus.entity.Linux;
import com.corpus.entity.PraatDetailSelect;
import com.corpus.entity.RegTask;
import com.corpus.entity.TaskAttrs;
import com.corpus.entity.Tasks;
import com.corpus.entity.TrainSet;
import com.corpus.entity.Usage;
import com.corpus.entity.Wave;
import com.corpus.entity.WaveList;
import com.corpus.file.FileOperation;
import com.corpus.service.LabelService;
import com.corpus.ssh.RmtShellExecutor;
import com.corpus.thread.UsingTrainSetThread;
import com.sun.xml.internal.bind.v2.model.core.ID;

import net.sf.json.JSONObject;
import sun.security.krb5.internal.Ticket;

@Service
public class LabelServiceImpl implements LabelService {
	
	@Resource
	CorpusDao corpusDao;
	
	@Resource
	PraatDao praatDao;
	
	@Resource
	WaveDao waveDao;
	
	@Resource
	WavetaggerDao wavetaggerDao;
	
	@Resource
	TrainAndTestDao trainAndTestDao;
	
	@Resource
	AttributesDao attributesDao;

	@Override
	public Map<String, List<WaveList>> getTTSet(int id, int type, String per, String labelType, String name) {
		// TODO Auto-generated method stub
		//get count > get test number > produce random number as wave's id
		double time = 0;
		List<WaveList> count = new ArrayList<WaveList>();
		if(type == 1){
			//wavetagger
			count = corpusDao.selectCountByCorpusInWavetagger(id);
			time = corpusDao.selectTimeById(id);
		}else{
			//praat
			Wave wave = new Wave();
			wave.setCorpus(id);
			switch (labelType) {
			case "0":
				time = corpusDao.selectTimeFmtById("select context from corpusFmt where corpus = " + wave.getCorpus());
				labelType = "context";
				break;
			case "1":
				time = corpusDao.selectTimeFmtById("select gender from corpusFmt where corpus = " + wave.getCorpus());
				labelType = "gender";
				break;
			case "2":
				time = corpusDao.selectTimeFmtById("select language from corpusFmt where corpus = " + wave.getCorpus());
				labelType = "language";
				break;
			case "3":
				time = corpusDao.selectTimeFmtById("select speaker from corpusFmt where corpus = " + wave.getCorpus());
				labelType = "speaker";
				break;
			case "4":
				time = corpusDao.selectTimeFmtById("select effective from corpusFmt where corpus = " + wave.getCorpus());
				labelType = "effective";
				break;
			default:
				System.out.println("labelType出错");
				break;
			}
			count  = corpusDao.selectCountByCorpusInPraat(id);
		}
		
		Map<String, List<WaveList>> waveList = new HashMap<String, List<WaveList>>();
		waveList = getTestSet(count, per, labelType, time, id, type, name);
		
		TrainSet trainSet = new TrainSet();
		trainSet.setCorpusID(id);
		trainSet.setName(name);
		trainAndTestDao.updateSetFlag(trainAndTestDao.selectIdByCorpusAndName(trainSet));
		return waveList;
	}

	/* (non-Javadoc)
	 * @see com.corpus.service.LabelService#getTestSet(java.util.List, java.lang.String, java.lang.String, double, int)
	 */
	@Override
	public Map<String, List<WaveList>> getTestSet(List<WaveList> count, String per, String labelType, double totalTime, int id, int type, String name) {
		// TODO Auto-generated method stub
		float percent = Float.parseFloat(per);
		Map<String, List<WaveList>> waveList = new HashMap<String, List<WaveList>>();
		List<Integer> idList_train = new ArrayList<Integer>();
		List<Integer> idList_test = new ArrayList<Integer>();
		
		int num = count.size();
		Random random = new Random();
		
		if(num == 0){
			System.out.println("该语料库中没有数据");
		}else{
			/*int test = (int) Math.round(((float)percent)/100 + 0.5);*/
			
			double time1 = 0;
			
			boolean flag = true;
			double train = percent/100 * totalTime;
			Wave wave = new Wave();
			List<Integer> idList = new ArrayList<Integer>();
			while(flag){
				int number = random.nextInt(num);
				if(idList.contains(num)){
					continue;
				}
				
				idList.add(num);
				
				int waveID = count.get(number).getId();
				/*time += Float.parseFloat(count.get(number).getTime());*/
				idList_train.add(waveID);
				switch (type) {
				case 0:
					wave.setId(waveID);
					time1 += waveDao.selectTimeByTypeAndId("select " + labelType + " from wave where id = " + wave.getId());
					
					/*switch (labelType) {
					case "context":
						time1 += waveDao.selectTimeByTypeAndIdContext(wave);
						break;
					case "gender":
						time1 += waveDao.selectTimeByTypeAndIdGender(wave);
						break;
					case "language":
						time1 += waveDao.selectTimeByTypeAndIdLanguage(wave);
						break;
					case "speaker":
						time1 += waveDao.selectTimeByTypeAndIdSpeaker(wave);
						break;
					case "effective":
						time1 += waveDao.selectTimeByTypeAndIdEffective(wave);
						break;
					default:
						break;
					}*/
					break;
				case 1:
					time1 += wavetaggerDao.selectTimeById(waveID);
					break;
				case 2:
					wave.setId(waveID);
					time1 += waveDao.selectTimeByTypeAndId("select " + labelType + " from wave where id = " + wave.getId());

					/*switch (labelType) {
					case "context":
						time1 += waveDao.selectTimeByTypeAndIdContext(wave);
						break;
					case "gender":
						time1 += waveDao.selectTimeByTypeAndIdGender(wave);
						break;
					case "language":
						time1 += waveDao.selectTimeByTypeAndIdLanguage(wave);
						break;
					case "speaker":
						time1 += waveDao.selectTimeByTypeAndIdSpeaker(wave);
						break;
					case "effective":
						time1 += waveDao.selectTimeByTypeAndIdEffective(wave);
						break;
					default:
						break;
					}*/
					break;
				default:
					System.out.println("type不正确");
					break;
				}
				if(time1 > train)
					flag = false;
			}
			train = time1;
			double test = totalTime - train;
			//获取test
			for(int i = 0; i < num; i++){
				if(!idList_train.contains(count.get(i).getId())){
					idList_test.add(count.get(i).getId());
				}
			}
			
			TrainSet trainSet = new TrainSet();
			trainSet.setCorpusID(id);
			trainSet.setName(name);
			trainSet.setLabelType(labelType);
			trainSet.setType(type);
			trainSet.setTrain(train);
			trainSet.setTest(test);
			trainSet.setPercent(percent);
			
			trainAndTestDao.insert2setlist(trainSet);
			
			int setID = trainAndTestDao.selectIdByName(name);
			for(int i = 0; i < idList_train.size(); i++){
				trainSet.setWaveID(idList_train.get(i));
				trainSet.setId(setID);
				trainSet.setTypeFlag(0);
				trainAndTestDao.insert2setwavelist(trainSet);
			}
			for(int i = 0; i < idList_test.size(); i++){
				trainSet.setWaveID(idList_test.get(i));
				trainSet.setId(setID);
				trainSet.setTypeFlag(1);
				trainAndTestDao.insert2setwavelist(trainSet);
			}
			
			/*if(type == 0){
				List<WaveList> waveLists_train = new ArrayList<WaveList>();
				List<WaveList> waveLists_test = new ArrayList<WaveList>();
				for(int i = 0; i < idList_train.size(); i++){
					//将训练集id等信息insert到setwavelist中
					switch (labelType) {
					case "context":
						waveLists_train.add(praatDao.selectByTrainTestPraatContext(idList_train.get(i)));
						break;
					case "gender":
						waveLists_train.add(praatDao.selectByTrainTestPraatGender(idList_train.get(i)));
						break;
					case "language":
						waveLists_train.add(praatDao.selectByTrainTestPraatLanguage(idList_train.get(i)));
						break;
					case "speaker":
						waveLists_train.add(praatDao.selectByTrainTestPraatSpeaker(idList_train.get(i)));
						break;
					case "effective":
						waveLists_train.add(praatDao.selectByTrainTestPraatEffective(idList_train.get(i)));
						break;
					default:
						break;
					}
				}
				
				waveList.put("train", waveLists_train);
				
				for(int i = 0; i < idList_test.size(); i++){
					switch (labelType) {
					case "context":
						waveLists_test.add(praatDao.selectByTrainTestPraatContext(idList_test.get(i)));
						break;
					case "gender":
						waveLists_test.add(praatDao.selectByTrainTestPraatGender(idList_test.get(i)));
						break;
					case "language":
						waveLists_test.add(praatDao.selectByTrainTestPraatLanguage(idList_test.get(i)));
						break;
					case "speaker":
						waveLists_test.add(praatDao.selectByTrainTestPraatSpeaker(idList_test.get(i)));
						break;
					case "effective":
						waveLists_test.add(praatDao.selectByTrainTestPraatEffective(idList_test.get(i)));
						break;
					default:
						break;
					}
				}
				waveList.put("test", waveLists_test);
			}else{
				List<WaveList> waveLists_train = new ArrayList<WaveList>();
				List<WaveList> waveLists_test = new ArrayList<WaveList>();
				for(int i = 0; i < idList_train.size(); i++){
					waveLists_train.add(wavetaggerDao.selectByTrainTestWavetagger(idList_train.get(i)));
				}
				waveList.put("train", waveLists_train);
				
				for(int i = 0; i < idList_test.size(); i++){
					waveLists_test.add(wavetaggerDao.selectByTrainTestWavetagger(idList_test.get(i)));
				}
				waveList.put("test", waveLists_test);
			}*/
		}

		TrainSet trainSet = new TrainSet();
		trainSet.setCorpusID(id);
		trainSet.setName(name);
		trainAndTestDao.updateSetFlag(trainAndTestDao.selectIdByCorpusAndName(trainSet));
		
		return waveList;
	}

	@Override
	public Map<String, List<WaveList>> getTTSetByTime(int id, int type, float train, float test, String labelType, String name) {
		// TODO Auto-generated method stub
		List<WaveList> count = new ArrayList<WaveList>();
		double time = 0;
		if(type == 1){
			//wavetagger
			count = corpusDao.selectCountByCorpusInWavetagger(id);
			time = corpusDao.selectTimeById(id);
		}else{
			//praatWave wave = new Wave();
			/*Wave wave = new Wave();
			wave.setCorpus(id);*/
			switch (labelType) {
			case "0":
				labelType = "context";
				break;
			case "1":
				labelType = "gender";
				break;
			case "2":
				labelType = "language";
				break;
			case "3":
				labelType = "speaker";
				break;
			case "4":
				labelType = "effective";
				break;
			default:
				System.out.println("labelType出错");
				break;
			}
			count  = corpusDao.selectCountByCorpusInPraat(id);
		}
		
		Map<String, List<WaveList>> waveList = new HashMap<String, List<WaveList>>();
		waveList = getTestSetByTime(count, train, test, labelType, id, type, time, name);
		
		return waveList;
	}
	
	/*labelType
	 * 0:context
	 * 1:gender
	 * 2:language
	 * 3:speaker
	 * 4:effective*/

	@Override
	public Map<String, List<WaveList>> getTestSetByTime(List<WaveList> count, double train, double test, String labelType, int id, int type, double totalTime, String name) {
		// TODO Auto-generated method stub
		Map<String, List<WaveList>> waveList = new HashMap<String, List<WaveList>>();
		List<Integer> idList_train = new ArrayList<Integer>();
		List<Integer> idList_test = new ArrayList<Integer>();
		
		int num = count.size();
		Random random = new Random();
		
		//获取train
		boolean flag = true;
		double time = 0.0;
		Wave wave = new Wave();
		
		List<Integer> idList = new ArrayList<Integer>();
		while(flag){
			int number = random.nextInt(num);
			if(idList.contains(number))
				continue;
			
			idList.add(number);
			int waveID = count.get(number).getId();
			/*time += Float.parseFloat(count.get(number).getTime());*/
			idList_train.add(waveID);
			switch (type) {
			case 0:
				wave.setId(waveID);
				time += waveDao.selectTimeByTypeAndId("select " + labelType + " from wave where id = " + wave.getId());
				/*switch (labelType) {
				case "context":
					time += waveDao.selectTimeByTypeAndIdContext(wave);
					break;
				case "gender":
					time += waveDao.selectTimeByTypeAndIdGender(wave);
					break;
				case "language":
					time += waveDao.selectTimeByTypeAndIdLanguage(wave);
					break;
				case "speaker":
					time += waveDao.selectTimeByTypeAndIdSpeaker(wave);
					break;
				case "effective":
					time += waveDao.selectTimeByTypeAndIdEffective(wave);
					break;
				default:
					break;
				}*/
				break;
			case 1:
				time += wavetaggerDao.selectTimeById(waveID);
				break;
			case 2:
				wave.setId(waveID);
				time += waveDao.selectTimeByTypeAndId("select " + labelType + " from wave where id = " + wave.getId());
				/*switch (labelType) {
				case "context":
					time += waveDao.selectTimeByTypeAndIdContext(wave);
					break;
				case "gender":
					time += waveDao.selectTimeByTypeAndIdGender(wave);
					break;
				case "language":
					time += waveDao.selectTimeByTypeAndIdLanguage(wave);
					break;
				case "speaker":
					time += waveDao.selectTimeByTypeAndIdSpeaker(wave);
					break;
				case "effective":
					time += waveDao.selectTimeByTypeAndIdEffective(wave);
					break;
				default:
					break;
				}*/
				break;
			default:
				System.out.println("type不正确");
				break;
			}
			if(time > train)
				flag = false;
		}
		train = time;
		//获取test
		time = 0;
		Random random2 = new Random();
		
		flag = true;
		
		while(flag){
			int number = random2.nextInt(num);
			if(idList.contains(number))
				continue;
			
			idList.add(number);
				idList_test.add(count.get(number).getId());
				switch (type) {
				case 0:
					wave.setId(count.get(number).getId());
					wave.setName("labelType");
					time += waveDao.selectTimeByTypeAndId("select " + labelType + " from wave where id = " + wave.getId());
					/*switch (labelType) {
					case "context":
						time += waveDao.selectTimeByTypeAndIdContext(wave);
						break;
					case "gender":
						time += waveDao.selectTimeByTypeAndIdGender(wave);
						break;
					case "language":
						time += waveDao.selectTimeByTypeAndIdLanguage(wave);
						break;
					case "speaker":
						time += waveDao.selectTimeByTypeAndIdSpeaker(wave);
						break;
					case "effective":
						time += waveDao.selectTimeByTypeAndIdEffective(wave);
						break;
					default:
						break;
					}*/
					break;
				case 1:
					time += wavetaggerDao.selectTimeById(count.get(number).getId());
					break;
				case 2:
					wave.setId(count.get(number).getId());
					time += waveDao.selectTimeByTypeAndId("select " + labelType + " from wave where id = " + wave.getId());
					/*switch (labelType) {
					case "context":
						time += waveDao.selectTimeByTypeAndIdContext(wave);
						break;
					case "gender":
						time += waveDao.selectTimeByTypeAndIdGender(wave);
						break;
					case "language":
						time += waveDao.selectTimeByTypeAndIdLanguage(wave);
						break;
					case "speaker":
						time += waveDao.selectTimeByTypeAndIdSpeaker(wave);
						break;
					case "effective":
						time += waveDao.selectTimeByTypeAndIdEffective(wave);
						break;
					default:
						break;
					}*/
					break;
				default:
					System.out.println("type不正确");
					break;
				}
			}
			/*time += Float.parseFloat(temp.get(number).getTime());*/
			if(time >= test)
				flag = false;
		
		test = time;
		
		TrainSet trainSet = new TrainSet();
		trainSet.setCorpusID(id);
		trainSet.setName(name);
		trainSet.setLabelType(labelType);
		trainSet.setType(type);
		trainSet.setTrain(train);
		trainSet.setTest(test);
		
		trainAndTestDao.insert2setlist(trainSet);
		
		int setID = trainAndTestDao.selectIdByName(name);
		for(int i = 0; i < idList_train.size(); i++){
			trainSet.setWaveID(idList_train.get(i));
			trainSet.setId(setID);
			trainSet.setTypeFlag(0);
			trainAndTestDao.insert2setwavelist(trainSet);
		}
		for(int i = 0; i < idList_test.size(); i++){
			trainSet.setWaveID(idList_test.get(i));
			trainSet.setId(setID);
			trainSet.setTypeFlag(1);
			trainAndTestDao.insert2setwavelist(trainSet);
		}
		
		/*if(type == 0){
			List<WaveList> waveLists_train = new ArrayList<WaveList>();
			List<WaveList> waveLists_test = new ArrayList<WaveList>();
			for(int i = 0; i < idList_train.size(); i++){
				//将训练集id等信息insert到setwavelist中
				switch (labelType) {
				case "context":
					waveLists_train.add(praatDao.selectByTrainTestPraatContext(idList_train.get(i)));
					break;
				case "gender":
					waveLists_train.add(praatDao.selectByTrainTestPraatGender(idList_train.get(i)));
					break;
				case "language":
					waveLists_train.add(praatDao.selectByTrainTestPraatLanguage(idList_train.get(i)));
					break;
				case "speaker":
					waveLists_train.add(praatDao.selectByTrainTestPraatSpeaker(idList_train.get(i)));
					break;
				case "effective":
					waveLists_train.add(praatDao.selectByTrainTestPraatEffective(idList_train.get(i)));
					break;
				default:
					break;
				}
			}
			
			waveList.put("train", waveLists_train);
			
			for(int i = 0; i < idList_test.size(); i++){
				switch (labelType) {
				case "context":
					waveLists_test.add(praatDao.selectByTrainTestPraatContext(idList_test.get(i)));
					break;
				case "gender":
					waveLists_test.add(praatDao.selectByTrainTestPraatGender(idList_test.get(i)));
					break;
				case "language":
					waveLists_test.add(praatDao.selectByTrainTestPraatLanguage(idList_test.get(i)));
					break;
				case "speaker":
					waveLists_test.add(praatDao.selectByTrainTestPraatSpeaker(idList_test.get(i)));
					break;
				case "effective":
					waveLists_test.add(praatDao.selectByTrainTestPraatEffective(idList_test.get(i)));
					break;
				default:
					break;
				}
			}
			waveList.put("test", waveLists_test);
		}else{
			List<WaveList> waveLists_train = new ArrayList<WaveList>();
			List<WaveList> waveLists_test = new ArrayList<WaveList>();
			for(int i = 0; i < idList_train.size(); i++){
				waveLists_train.add(wavetaggerDao.selectByTrainTestWavetagger(idList_train.get(i)));
			}
			waveList.put("train", waveLists_train);
			
			for(int i = 0; i < idList_test.size(); i++){
				waveLists_test.add(wavetaggerDao.selectByTrainTestWavetagger(idList_test.get(i)));
			}
			waveList.put("test", waveLists_test);
		}*/
		
		return waveList;
	}

	@Override
	public List<TrainSet> getSetListService(int corpus) {
		// TODO Auto-generated method stub
		return trainAndTestDao.selectListByCorpus(corpus);
	}

	@Override
	public List<Usage> getUsage(int id) {
		// TODO Auto-generated method stub
		return trainAndTestDao.selectUsageBySetID(id);
	}

	@Override
	public JSONObject setUsage(JSONObject info) {
		
		JSONObject jsonObject = new JSONObject();
		// TODO Auto-generated method stub
		
		String path = info.getString("wavePath");
		String ip = info.getString("ip");
		String linuxUser = info.getString("linuxUser");
		String linuxPaw = info.getString("linuxPaw");

		Usage usage = new Usage();
		usage.setUser(info.getString("username"));
		usage.setDesp(info.getString("desp"));
		usage.setSetID(info.getInt("setID"));
		
		if(trainAndTestDao.selectUsageByUserAndSetid(usage) == null){
			
			try {
					/* 将ip，linuxUser, linuxPaw, wavePath, labelPath写入数据库
					 * 判断文件目录是否存在， 如果存在则将数据直接存放在该位置
					 * 不存在则创建新的目录*/
					//判断路径是否存在
					
					
					if(info.getInt("flag") == 0){
						long time = System.currentTimeMillis();
						usage.setIp(ip);
						usage.setLinuxUser(linuxUser);
						usage.setLinuxPaw(linuxPaw);
						usage.setWavePath(path + "/" + time);
						trainAndTestDao.insertUsage(usage);
						
						usage.setId(trainAndTestDao.selectIdByUserAndSetid(usage));
						trainAndTestDao.insert2ip(usage);
						
						//将数据复制到指定位置
						/*获取当前时间戳*/
						
						provideUsage(usage, time);
						trainAndTestDao.updateTrainSet(usage.getSetID());
						
					}else{
						usage.setResult(info.getString("wer"));
						trainAndTestDao.updateUsage(usage);
					}
					jsonObject.put("error", "");
			} catch (Exception e) {
				// TODO: handle exception
				jsonObject.put("error", "系统问题，请刷新重试");
				e.printStackTrace();
			}
		}else{
			jsonObject.put("error", "该用户已经使用过该训练集");
		}
		
		return jsonObject;
	}

	@Override
	public Usage getUsageById(int id) {
		// TODO Auto-generated method stub
		return trainAndTestDao.selectUsageById(id);
	}

	@Override
	public JSONObject provideUsage(Usage usage, long time) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		
		//将训练集和测试集中的音频列表复制到指定位置（应该是特征）
		//获取所属的语料库中的主目录wavePath\\labelPath
		Corpus corpus = trainAndTestDao.selectCorpusBySetID(usage.getSetID());
		
		
		//int labelType = corpusDao.selectAllById(corpus.getId()).getLabelType();
		int labelType = corpus.getLabelType();

		List<PraatDetailSelect> train = new ArrayList<PraatDetailSelect>();
		List<PraatDetailSelect> test = new ArrayList<PraatDetailSelect>();
		
		TrainSet trainSet = new TrainSet();
		trainSet.setId(usage.getSetID());
		
		corpus.setFileType(0);
		
		Corpus waveid = trainAndTestDao.selectUsageFromIp(usage.getId());
		Linux linux = new Linux();
		linux.setIp(waveid.getIp());
		linux.setUsername(waveid.getUsername());
		linux.setPassword(waveid.getPassword());
		linux.setPath(usage.getWavePath());

		switch (labelType) {
		case 0:
			trainSet.setTypeFlag(0);
			train = trainAndTestDao.selectWaveListBySetIDPraat(trainSet);
			trainSet.setTypeFlag(1);
			test = trainAndTestDao.selectWaveListBySetIDPraat(trainSet);
			
			//scp file root@ip:/path/
			//wave直接复制文件
			
			//FileOperation fileOperationtrain = new FileOperation(train);
			//time = fileOperationtrain.write("scpWave.sh", "scpLabel.sh", linux, usage, corpus.getWavePath(), 0, 0, time);
			
			//label先写在复制
			//将数据库中的标注结果写入文件并将文件放入指定位置
			//FileOperation fileOperationtest = new FileOperation(test);
			//fileOperationtest.write("scpWave.sh", "scpLabel.sh", linux, usage, corpus.getWavePath(), 0, 1, time);
			
			//fileOperationtest.deleteFile("scpWave.sh");
			//fileOperationtest.deleteFileDir("scpLabel");
			break;
		case 1:
			trainSet.setTypeFlag(0);
			train = trainAndTestDao.selectWaveListBySetIDWavetagger(trainSet);
			trainSet.setTypeFlag(1);
			test = trainAndTestDao.selectWaveListBySetIDWavetagger(trainSet);
			//scp file root@ip:/path/
			//wave直接复制文件
			
			/*FileOperation fileOperationtrainwavetagger = new FileOperation(train);
			time = fileOperationtrainwavetagger.write("scpWave.sh", "scpLabel.sh", linux, usage, corpus.getWavePath(), 1, 0, time);
			
			//label先写在复制
			//将数据库中的标注结果写入文件并将文件放入指定位置
			FileOperation fileOperationtestwavetagger = new FileOperation(test);
			fileOperationtestwavetagger.write("scpLabel.sh", "scpLabel.sh", linux, usage, corpus.getWavePath(), 1, 1, time);
			
			fileOperationtestwavetagger.deleteFile("scpWave.sh");
			fileOperationtestwavetagger.deleteFileDir("scpLabel");*/
			break;
		case 2:
			trainSet.setTypeFlag(0);
			train = trainAndTestDao.selectWaveListBySetIDFile(trainSet);
			trainSet.setTypeFlag(1);
			test = trainAndTestDao.selectWaveListBySetIDFile(trainSet);
			//scp file root@ip:/path/
			//wave直接复制文件
			
			/*FileOperation fileOperationtrainfile = new FileOperation(train);
			time = fileOperationtrainfile.write("scpWave", "scpLabel", linux, usage, usage.getWavePath(), 2, 0, time);
			
			//label先写在复制
			//将数据库中的标注结果写入文件并将文件放入指定位置
			FileOperation fileOperationtestfile = new FileOperation(test);
			fileOperationtestfile.write("scpLabel", "scpLabel", linux, usage, usage.getLabelPath(), 2, 1, time);
			
			fileOperationtestfile.deleteFile("scpWave.sh");
			fileOperationtestfile.deleteFileDir("scpLabel");*/
			break;
		default:
			break;
		}
		

		UsingTrainSetThread usingTrainSetThread = new UsingTrainSetThread(train, test, time, linux, usage, corpus, labelType, trainAndTestDao);
		usingTrainSetThread.start();
		
		return jsonObject;
	}

	@Override
	public List<AttributesAndValue> queryAllAttributes() {
		// TODO Auto-generated method stub
		List<AttributesAndValue> lista=new ArrayList<AttributesAndValue>();
		
		//先查询所有可作为分配依据的属性
		StringBuffer sb=new StringBuffer();
		sb.append("select * from attributes where distribased=1");
		List<Attributes> attr = attributesDao.queryAllAttributes(sb.toString());
		for (Attributes attributes : attr) {
			if(attributes.getAttrtype()==0||attributes.getAttrtype()==2){
			AttributesAndValue abv=new AttributesAndValue();
			abv.setAid(attributes.getId());
			abv.setAttrname(attributes.getAttrname());
			abv.setOprtype(attributes.getOprtype());
			List<AttributeValues> values = attributesDao.queryAllAttributeValues(attributes.getId());
			abv.setListv(values);
			
			if(values.size() != 0)
				lista.add(abv);
			}
		}
		return lista;
	}

	@Override
	public List<AttributeValues> queryAllAttributeValues(int id) {
		// TODO Auto-generated method stub
		List<AttributeValues> list = attributesDao.queryAllAttributeValues(id);
		return list;
	}

	@Override
	public List<Attributes> querylabelattr() {
		// TODO Auto-generated method stub
		List<Attributes> attributes = attributesDao.querylabelattr();
		return attributes;
	}

	@Override
	public int checkTaskname(String taskname) {
		// TODO Auto-generated method stub
		int num = attributesDao.queryTaskNum(taskname);
		return num;
	}

	@Override
	public int insertNewTask(RegTask regTask) {
		// TODO Auto-generated method stub
		int flag = 0;
		try {
			Tasks task=new Tasks();
			task.setFile_path(regTask.getFile_path());
			task.setTask_name(regTask.getTask_name());
			if(regTask.getTask_desp().equals("")||regTask.getTask_desp()==null){
				
				task.setTask_desp(null);
			}else{
				task.setTask_desp(regTask.getTask_desp());
				
			}
			task.setTask_status((byte) 1);
			task.setPause_status((byte) 1);
			Timestamp time = new Timestamp(System.currentTimeMillis());  
			task.setCreate_time(time);
			task.setUpdate_time(time);
			task.setGrade(regTask.getPriority());
			task.setObject_num(0);
			attributesDao.insertTask(task);
			Tasks tasks = attributesDao.selectTaskByName(regTask.getTask_name());
			//添加已知属性值	
			String string = regTask.getYizhishuxing();
			String[] taskyzsx = string.split("；");
			TaskAttrs tk=new TaskAttrs();
			for (int i = 0; i < taskyzsx.length; i++) {
				String[] split = taskyzsx[i].split("：");
				int j = Integer.parseInt(split[0]);
	 			tk.setAttr_id(j) ;
	 			int k = Integer.parseInt(split[1]);
				tk.setAttr_value_id(k) ;
				tk.setTask_id(tasks.getId());
				tk.setTask_type(0);
				attributesDao.insertAttributes(tk);
			}

			//添加标注属性
			String bzsx = regTask.getBiaozhushuxing();
			String[] strings = bzsx.split("；");
			TaskAttrs ts=new TaskAttrs();
			for (int j = 0; j < strings.length; j++) {
				String[] split = strings[j].split("：");
				String[] split2=null;
				if(split.length!=1){
					 split2 = split[1].split(":");
					 for(int k=0;k<split2.length;k++){
							ts.setTask_id(tasks.getId());
							
							int i = Integer.parseInt(split[0]);
							ts.setAttr_id(i);
							int l = Integer.parseInt(split2[k]);
							ts.setAttr_value_id(l);
							ts.setTask_type(1);
							attributesDao.insertAttributes(ts);
						
						}
					
				}else{
					 split2 = split[0].split(":");
					 for(int k=0;k<split2.length;k++){
							ts.setTask_id(tasks.getId());
							
							int i = Integer.parseInt(split[0]);
							ts.setAttr_id(i);						
							ts.setAttr_value_id(0);
							ts.setTask_type(1);
							attributesDao.insertAttributes(ts);
						
						}
				}
				
			
			}
			flag = 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

}
