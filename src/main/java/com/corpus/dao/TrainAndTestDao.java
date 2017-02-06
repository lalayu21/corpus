package com.corpus.dao;

import java.util.List;

import com.corpus.entity.Corpus;
import com.corpus.entity.PraatDetailSelect;
import com.corpus.entity.TrainSet;
import com.corpus.entity.Usage;

public interface TrainAndTestDao {
	//将新的训练集和测试集insert到数据库
	public void insert2setlist(TrainSet trainSet);
	
	//将训练集和测试集所包含的训练和测试数据insert到setwavelist中
	public void insert2setwavelist(TrainSet trainSet);
	
	//为训练集id添加新的使用信息
	public void insertUsage(Usage usage);
	
	//将使用记录中的ip信息存入ip表中
	public void insert2ip(Usage usage);
	
	//根据名称获取训练集和测试集的专属id
	public int selectIdByName(String name);
	
	//根据corpusID获取set list
	public List<TrainSet> selectListByCorpus(int corpus);
	
	//根据setID获取某一个训练集的使用情况
	public List<Usage> selectUsageBySetID(int setID);
	
	//获取id的使用情况
	public Usage selectUsageById(int id);
	
	//获取setID所属的语料库
	public Corpus selectCorpusBySetID(int setID);
	
	//根据setID获取其对应的音频列表
	public List<PraatDetailSelect> selectWaveListBySetIDPraat(TrainSet trainSet);
	public List<PraatDetailSelect> selectWaveListBySetIDWavetagger(TrainSet trainSet);
	public List<PraatDetailSelect> selectWaveListBySetIDFile(TrainSet trainSet);
	
	//为使用情况id更新信息
	public void updateUsage(Usage usage);
	
	//根据使用者和训练集id获取使用记录的唯一id
	public int selectIdByUserAndSetid(Usage usage);
	
	//更新训练集信息
	public void updateTrainSet(int id);
	
	//根据用户名获取其使用情况
	public Usage selectUsageByUserAndSetid(Usage usage);
	
	//根据usageid获取ip
	public Corpus selectUsageFromIp(int id);
	
	//将训练集和测试集的状态从未完成改为完成
	public void updateSetFlag(int id);
	
	//根据语料库id和名称获取训练集和测试集的唯一id
	public int selectIdByCorpusAndName(TrainSet trainSet);
	
	//将训练集和测试集的使用情况的状态设置为1
	public void updateSetUseFlag(int id);
}
