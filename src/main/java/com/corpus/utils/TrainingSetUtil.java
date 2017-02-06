package com.corpus.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.corpus.entity.TrainSet;
import com.corpus.entity.Wave;
import com.corpus.entity.WaveList;

public class TrainingSetUtil {
	private int id;
	private int type;
	private double train;
	private double test;
	private String labelType;
	private String name;
	private float per;
	private QueryRunner queryRunner;
	private int corpus;
	
	public TrainingSetUtil(int id, int corpus, int type, float per, double train, double test, String labelType, String name, QueryRunner queryRunner) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.type = type;
		this.train = train;
		this.test = test;
		this.labelType = labelType;
		this.name = name;
		this.per = per;
		this.queryRunner = queryRunner;
		this.corpus = corpus;
	}
	
	//获取训练集
	public void getTrainingSet(){
		getTTSetByTime();
	}
	
	public Map<String, List<WaveList>> getTTSetByTime() {
		// TODO Auto-generated method stub
		List<WaveList> count = new ArrayList<WaveList>();
		double time = 0;
		if(type == 1){
			//wavetagger
			try {
				count = queryRunner.query("select id from wavetagger where corpus = ?", new BeanListHandler<WaveList>(WaveList.class), corpus);
				time = queryRunner.query("select time from corpus where id = ?", new BeanHandler<Double>(Double.class), corpus);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("error: get count or time about wavetagger in TrainingSet.java");
				e.printStackTrace();
			}
		}else if(type == 0){
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
			try {
				count = queryRunner.query("select id from wave where corpus =  ?", new BeanListHandler<WaveList>(WaveList.class), corpus);
				time = queryRunner.query("select " + labelType + " from corpusfmt where corpus = ", new BeanHandler<Double>(Double.class), corpus);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("error: get count or time in TrainingSet.java of praat");
				e.printStackTrace();
			}
		}
		
		Map<String, List<WaveList>> waveList = new HashMap<String, List<WaveList>>();
		waveList = getTestSetByTime(count, time);
		
		try {
			queryRunner.update("update setlist set flag = 1 where id = " + id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//trainAndTestDao.updateSetFlag(trainAndTestDao.selectIdByCorpusAndName(trainSet));
		
		return waveList;
	}
	
	//使用所有数据
	public Map<String, List<WaveList>> getTestSetByTime(List<WaveList> count, double totalTime) {
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
		
		if(per > 0){
			try {
				train = (per / 100 * totalTime);
				test = totalTime - train;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		List<Integer> idList = new ArrayList<Integer>();
		while(flag){
			int number = random.nextInt(num);
			if(idList.contains(number))
				continue;
			
			idList.add(number);
			int waveID = count.get(number).getId();
			idList_train.add(waveID);
			switch (type) {
			case 0:
				try {
					time += queryRunner.query("select " + labelType + " from wave where id = " + waveID, new BeanHandler<Double>(Double.class));
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("error: getTestSetByTime()");
					e.printStackTrace();
				}
				break;
			case 1:
				try {
					time += queryRunner.query("select time from wavetagger where id = " + waveID, new BeanHandler<Double>(Double.class));
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("error: getTestSetByTime()");
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					time += queryRunner.query("select " + labelType + " from wave where id = " + waveID, new BeanHandler<Double>(Double.class));
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("error: getTestSetByTime()");
					e.printStackTrace();
				}
				break;
			default:
				System.out.println("type不正确");
				break;
			}
			if(time > train)
				flag = false;
		}
		train = (float) time;
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
				try {
					time += queryRunner.query("select " + labelType + " from wave where id = " + count.get(number).getId(), new BeanHandler<Double>(Double.class));
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("error: getTestSetByTime()");
					e.printStackTrace();
				}
				break;
			case 1:
				try {
					time += queryRunner.query("select time from wavetagger where id = " + count.get(number).getId(), new BeanHandler<Double>(Double.class));
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("error: getTestSetByTime()");
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					time += queryRunner.query("select " + labelType + " from wave where id = " + count.get(number).getId(), new BeanHandler<Double>(Double.class));
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("error: getTestSetByTime()");
					e.printStackTrace();
				}
				break;
			default:
				System.out.println("type不正确");
				break;
			}
		}
		if(time >= test)
			flag = false;
		
		test = (float) time;
		
		TrainSet trainSet = new TrainSet();
		trainSet.setCorpusID(id);
		trainSet.setName(name);
		trainSet.setLabelType(labelType);
		trainSet.setType(type);
		trainSet.setTrain(train);
		trainSet.setTest(test);
		//insert into setlist(corpusID, type, percent, train, test, labelType, name) 
		//values(#{corpusID}, #{type}, #{percent}, #{train}, #{test}, #{labelType}, #{name})
		try {
			queryRunner.update("insert into setlist(corpusID, type, train, test, labelType, name, percent) values(?, ?, ?, ?, ?, ?", corpus, type, train, test, labelType, name, per);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error: getTestSetByTime 215");
			e.printStackTrace();
		}
		//trainAndTestDao.insert2setlist(trainSet);
		int setID = id;
		try {
			//setID = queryRunner.query("select id from setlist where name = ", new BeanHandler<Integer>(Integer.class), name);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//int setID = trainAndTestDao.selectIdByName(name);
		for(int i = 0; i < idList_train.size(); i++){
			trainSet.setWaveID(idList_train.get(i));
			trainSet.setId(setID);
			trainSet.setTypeFlag(0);
			//trainAndTestDao.insert2setwavelist(trainSet);
			try {
				queryRunner.update("insert into setwavelist(setID, waveID, type) values(?, ?, ?)", setID, idList_train, 0);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("error");
				e.printStackTrace();
			}
		}
		for(int i = 0; i < idList_test.size(); i++){
			trainSet.setWaveID(idList_test.get(i));
			trainSet.setId(setID);
			trainSet.setTypeFlag(1);
			//trainAndTestDao.insert2setwavelist(trainSet);
			try {
				queryRunner.update("insert into setwavelist(setID, waveID, type) values(?, ?, ?)", setID, idList_train, 1);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("error");
				e.printStackTrace();
			}
		}
		
		return waveList;
	}
	
}
