package com.imageSim.shared;

public class MyArray {
	public MyArray(){
		
	}
	
	public static double[] multiplyWithFactor(double factor,double [] array){
		for(int i=0;i<array.length;++i){
			array[i]*=factor;
		}
		return array;
	}
	
	public static double[] addArrayElements(double[] a1,double[] a2){
		for(int i=0;i<a1.length;++i){
			a1[i]+=a2[i];
		}
		return a1;
	}
}
