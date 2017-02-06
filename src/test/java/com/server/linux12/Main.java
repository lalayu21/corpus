package com.server.linux12;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		
		int n = (int) input.nextDouble();
		double l = input.nextDouble();
		
		double max = 0;
		double min = l;
		double out = 0;
		
		List<Double> list = new ArrayList<Double>();
		
		for(int i = 0; i < n; i++){
			list.add(input.nextDouble());
		}
		
		for(int j = 0; j < n - 1; j++){
			for(int i = 0; i < n - j - 1; i++){
				if(list.get(i) < list.get(i+1)){
					double temp = list.get(i);
					list.set(i, list.get(i+1));
					list.set(i+1, temp);
				}
			}
		}
		
		for(int i = 0; i < n - 1; i++){
			if((list.get(i) - list.get(i+1)) / 2 > out){
				out = (list.get(i) - list.get(i+1)) / 2;
			}
		}
		
		if(list.get(n-1) != 0 && list.get(n-1) > out){
			out = list.get(n-1);
		}
		if(list.get(0) != l && (l - list.get(0)) > out){
			out = l - list.get(0);
		}
		
		System.out.println(String.format("%.2f", out));
	}

}
