package com.corpus.entity;

public class WaveTable {
	private int id;
	
	private String waveList;
	
	private int corpus;
	
	private long length;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWaveList() {
		return waveList;
	}

	public void setWaveList(String waveList) {
		this.waveList = waveList;
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
