package com.corpus.thread;

import java.util.List;

import com.corpus.dao.TrainAndTestDao;
import com.corpus.entity.Corpus;
import com.corpus.entity.Linux;
import com.corpus.entity.PraatDetailSelect;
import com.corpus.entity.Usage;
import com.corpus.file.FileOperation;

public class UsingTrainSetThread extends Thread {
	
	private List<PraatDetailSelect> train;
	
	private List<PraatDetailSelect> test;
	
	private long time;
	
	private Linux linux;
	
	private Usage usage;
	
	private Corpus corpus;
	
	private int labelType;
	
	private TrainAndTestDao trainAndTestDao;
	
	public UsingTrainSetThread(List<PraatDetailSelect> train, List<PraatDetailSelect> test, long time, Linux linux, Usage usage, Corpus corpus, int labelType, TrainAndTestDao trainAndTestDao) {
		// TODO Auto-generated constructor stub
		this.train = train;
		this.test = test;
		this.time = time;
		this.linux = linux;
		this.usage = usage;
		this.corpus = corpus;
		this.labelType = labelType;
		this.trainAndTestDao = trainAndTestDao;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		FileOperation fileOperationtrain = new FileOperation(train);
		time = fileOperationtrain.write("scpWave.sh", "scpLabel.sh", linux, usage, corpus.getWavePath(), labelType, 0, time);
		
		//label先写在复制
		//将数据库中的标注结果写入文件并将文件放入指定位置
		FileOperation fileOperationtest = new FileOperation(test);
		fileOperationtest.write("scpWave.sh", "scpLabel.sh", linux, usage, corpus.getWavePath(), labelType, 1, time);
		
		fileOperationtest.deleteFile("scpWave.sh");
		fileOperationtest.deleteFileDir("scpLabel");
		
		trainAndTestDao.updateSetUseFlag(usage.getId());
		
	}	
	
}
