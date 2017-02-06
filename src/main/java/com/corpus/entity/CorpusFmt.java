package com.corpus.entity;

public class CorpusFmt {
	private int id;
	//采样频率
	private int sample;
	//编码方式
	private int code;
	//是否有头
	private int head;
	//声道数
	private int channel;
	//量化数
	private int bitpersamples;
	//数据格式对应的语料库id
	private int corpus;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSample() {
		return sample;
	}
	public void setSample(int sample) {
		this.sample = sample;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getHead() {
		return head;
	}
	public void setHead(int head) {
		this.head = head;
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public int getBitpersamples() {
		return bitpersamples;
	}
	public void setBitpersamples(int bitpersamples) {
		this.bitpersamples = bitpersamples;
	}
	public int getCorpus() {
		return corpus;
	}
	public void setCorpus(int corpus) {
		this.corpus = corpus;
	}
}
