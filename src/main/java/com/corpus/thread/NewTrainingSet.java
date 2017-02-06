package com.corpus.thread;

import com.corpus.service.LabelService;

public class NewTrainingSet extends Thread {
	
	private LabelService labelService;
	
	private int id;
	private int type;
	private float train;
	private float test;
	private String labelType;
	private String name;
	private String per;
	public NewTrainingSet(int id, int type, float train, float test, String labelType, String name, LabelService labelService) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.type = type;
		this.train = train;
		this.test = test;
		this.labelType = labelType;
		this.name = name;
		this.labelService = labelService;
	}
	
	public NewTrainingSet(int id, int type, String per, String labelType, String name, LabelService labelService) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.type = type;
		this.per = per;
		this.labelType = labelType;
		this.name = name;
		this.labelService = labelService;
	}
	
	public void run(){

		if(per == null || "".equals(per)){
			labelService.getTTSetByTime(id, type, train, test, labelType, name);
		}else{
			labelService.getTTSet(id, type,per, labelType, name);
		}
	}
}
