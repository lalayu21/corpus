package com.corpus.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.corpus.entity.Linux;
import com.corpus.entity.PraatDetailSelect;
import com.corpus.entity.Usage;
import com.corpus.file.FileOperation;

public class TrainingSetUsageUtil {
	//"scpWave.sh", "scpLabel.sh", linux, usage, corpus.getWavePath(), labelType, 0
	private String waveFile;
	private String labelFile;
	private Linux linux;
	private Usage usage;
	private String wavePath;
	private int labelType;
	private QueryRunner queryRunner;
	
	public TrainingSetUsageUtil(Linux linux, Usage usage, String wavePath, int labelType, QueryRunner queryRunner){
		this.linux = linux;
		this.usage = usage;
		this.wavePath = wavePath;
		this.labelType = labelType;
		this.queryRunner = queryRunner;
		this.waveFile = "scpWave" + usage.getWavePath() + ".sh";
		this.waveFile = "scpLabel" + usage.getWavePath() + ".sh";
	}
	
	public void copyFile(){
		//获取训练集和测试集
		String sql = "select wave.wave, praat.result, praat.starttime, praat.endtime from praat inner join wave on wave.id = praat.wave where wave.id in (select waveID from setwavelist where setID = ? and type = ?)";	
		List<PraatDetailSelect> train = new ArrayList<PraatDetailSelect>();
		List<PraatDetailSelect> test = new ArrayList<PraatDetailSelect>();
		try {
			train = queryRunner.query(sql, new BeanListHandler<PraatDetailSelect>(PraatDetailSelect.class), usage.getSetID(), 0);
			test = queryRunner.query(sql, new BeanListHandler<PraatDetailSelect>(PraatDetailSelect.class), usage.getSetID(), 1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		CopyTrainingSetUtil copyTrainingSetUtil = new CopyTrainingSetUtil(train);
		//FileOperation fileOperationtrain = new FileOperation(train);
		copyTrainingSetUtil.write(waveFile, labelFile, linux, usage, wavePath, labelType, 0);
		
		//label先写在复制
		//将数据库中的标注结果写入文件并将文件放入指定位置
		CopyTrainingSetUtil copyTrainingSetUtilTest = new CopyTrainingSetUtil(train);
		copyTrainingSetUtilTest.write(waveFile, labelFile, linux, usage, wavePath, labelType, 1);
		
		FileOperationUtil fileOperationUtil = new FileOperationUtil();
		fileOperationUtil.deleteFile(waveFile);
		fileOperationUtil.deleteFileDir(labelFile);
	}
}
