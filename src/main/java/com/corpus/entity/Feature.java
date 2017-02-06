package com.corpus.entity;

public class Feature {
	private int id;
	
	private int corpusID;
	
	private int mfcc;
	
	private int plp;
	
	private int fbank;
	
	private int energy;
	
	private int melBins;
	
	private int ceps;
	
	private int lowFreq;
	
	private int highFreq;
	
	private String path;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCorpusID() {
		return corpusID;
	}

	public void setCorpusID(int corpusID) {
		this.corpusID = corpusID;
	}

	public int getMfcc() {
		return mfcc;
	}

	public void setMfcc(int mfcc) {
		this.mfcc = mfcc;
	}

	public int getPlp() {
		return plp;
	}

	public void setPlp(int plp) {
		this.plp = plp;
	}

	public int getFbank() {
		return fbank;
	}

	public void setFbank(int fbank) {
		this.fbank = fbank;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getMelBins() {
		return melBins;
	}

	public void setMelBins(int melBins) {
		this.melBins = melBins;
	}

	public int getCeps() {
		return ceps;
	}

	public void setCeps(int ceps) {
		this.ceps = ceps;
	}

	public int getLowFreq() {
		return lowFreq;
	}

	public void setLowFreq(int lowFreq) {
		this.lowFreq = lowFreq;
	}

	public int getHighFreq() {
		return highFreq;
	}

	public void setHighFreq(int highFreq) {
		this.highFreq = highFreq;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
