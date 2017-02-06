package com.corpus.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.corpus.entity.TrainSet;
import com.corpus.utils.TrainingSetUtil;

public class GetTrainSet extends Thread {
	
	public void run(){
		long intervalTime = 1000;
		long period = 200000;
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//获取待生成训练集的语料库
				QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
				List<TrainSet> trainSets = new ArrayList<TrainSet>();
				try {
					trainSets = queryRunner.query("select id, corpusID, type, percent, train, test, labelType, name from setlist where flag = 0", new BeanListHandler<TrainSet>(TrainSet.class));
					if(trainSets != null){
						for(int i = 0; i < trainSets.size(); i++){
							TrainSet temp = trainSets.get(i);
							TrainingSetUtil trainingSetUtil = new TrainingSetUtil(temp.getId(), temp.getCorpusID(), temp.getType(), temp.getPercent(), temp.getTrain(), temp.getTest(), temp.getLabelType(), temp.getName(), queryRunner);
							trainingSetUtil.getTrainingSet();
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("error: getTrainSet.java' get trainSets using select");
				}
			}
		}, intervalTime, period);
	}
}
