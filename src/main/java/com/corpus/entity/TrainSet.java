package com.corpus.entity;

public class TrainSet {
	//训练集和测试集id
	private int id;
	//训练集和测试集的名称
	private String name;
	//训练集和测试集所属corpus的id
	private int corpusID;
	//使用全部数据还是部分数据
	private int type;
	//训练集和测试集的百分比
	private float percent;
	//训练集时长
	private double train;
	//测试集时长
	private double test;
	//标注结果的类型：context、gender、language、speaker、effective
	private String labelType;
	//wave的id
	private int waveID;
	//标识是训练集还是测试集
	private int typeFlag;
	//使用次数
	private int useTime;
	//完成状态
	private int flag;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCorpusID() {
		return corpusID;
	}

	public void setCorpusID(int corpusID) {
		this.corpusID = corpusID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

	public double getTrain() {
		return train;
	}

	public void setTrain(double train) {
		this.train = train;
	}

	public double getTest() {
		return test;
	}

	public void setTest(double test) {
		this.test = test;
	}

	public String getLabelType() {
		return labelType;
	}

	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}

	public int getWaveID() {
		return waveID;
	}

	public void setWaveID(int waveID) {
		this.waveID = waveID;
	}

	public int getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(int typeFlag) {
		this.typeFlag = typeFlag;
	}

	public int getUseTime() {
		return useTime;
	}

	public void setUseTime(int useTime) {
		this.useTime = useTime;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
}
