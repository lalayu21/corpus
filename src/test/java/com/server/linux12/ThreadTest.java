package com.server.linux12;

public class ThreadTest extends Thread {
	
	private String name = null;
	
	private String welcome = "";
	
	public ThreadTest(String name, String welcome) {

		// TODO Auto-generated constructor stub
		this.name = name;
		this.welcome = welcome;
	}
	
	public void run(){
		for(int i = 1; i < 100; i++){
			System.out.println(name + "，" + welcome + i);
		}
	}
	/*public static void main(String[] args) {
		
		ThreadTest t1 = new ThreadTest("A");
		ThreadTest t2 = new ThreadTest("B");
		
		t1.start();
		t2.start();
	}*/

}
