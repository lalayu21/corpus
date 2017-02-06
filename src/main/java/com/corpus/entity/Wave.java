package com.corpus.entity;

public class Wave {
	private int id;
	
	private String wave;
	
	private double time;
	
	private int corpus;
	
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getWave() {
		return wave;
	}

	public void setWave(String wave) {
		this.wave = wave;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
}
