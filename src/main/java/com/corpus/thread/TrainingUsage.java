package com.corpus.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.corpus.entity.Corpus;
import com.corpus.entity.Linux;
import com.corpus.entity.PraatDetailSelect;
import com.corpus.entity.Usage;
import com.corpus.file.FileOperation;
import com.corpus.utils.TrainingSetUsageUtil;


public class TrainingUsage extends Thread {
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
				List<Usage> usages = new ArrayList<Usage>();
				
				try {
					usages = queryRunner.query("select id, setID, wavePath from setuse where flag = 0", new BeanListHandler<Usage>(Usage.class));
					if(usages != null){
						//Corpus corpus, int labelType
						for(int i = 0; i < usages.size(); i++){
							//usage
							Usage usage = usages.get(i);
							//linux
							long time = 0;
							Linux linux = new Linux();
							String sql_linux = "select ip, username, password from ip where userID = " + usage.getId();
							linux = queryRunner.query(sql_linux, new BeanHandler<Linux>(Linux.class));
							linux.setPath(usage.getWavePath());
							//labelType and wavePath in corpus
							String sql_corpus = "select corpus.id, corpus.wavePath, corpus.labelPath, corpus.labelType from corpus inner join setlist on setlist.corpusID = corpus.id where setlist.id = " + usage.getId();
							Corpus corpus = queryRunner.query(sql_corpus, new BeanHandler<Corpus>(Corpus.class));
							String wavePath = corpus.getWavePath();
							int labelType = corpus.getLabelType();
							
							TrainingSetUsageUtil trainingSetUsageUtil = new TrainingSetUsageUtil(linux, usage, wavePath, labelType, queryRunner);
							trainingSetUsageUtil.copyFile();
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
		}, intervalTime, period);
	}
}
