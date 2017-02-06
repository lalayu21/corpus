package com.corpus.thread;

import javax.servlet.http.HttpServlet;

public class ServletClass extends HttpServlet {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init(){
		
		LeadinCorpus leadinCorpus = new LeadinCorpus();
		leadinCorpus.start();
		
		GetTrainSet getTrainSet = new GetTrainSet();
		getTrainSet.start();
		
		TrainingUsage trainingUsage = new TrainingUsage();
		trainingUsage.start();
    }  
    
 
    public void destory(){  
    	
    } 
}
