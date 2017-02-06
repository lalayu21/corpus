package com.corpus.entity;


public class FileLabeled {
	private int id;
	
	private String wave;
	
	private String result;
	
	private double time;
	
	private int corpus;
	
	private long length;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWave() {
		return wave;
	}

	public void setWave(String wave) {
		this.wave = wave;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public int getCorpus() {
		return corpus;
	}

	public void setCorpus(int corpus) {
		this.corpus = corpus;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}
	
}
