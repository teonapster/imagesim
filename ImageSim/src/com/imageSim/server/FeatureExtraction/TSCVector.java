package com.imageSim.server.FeatureExtraction;
/**
 * @author teonapster
 * Contains Texture,Shape,Color Vector 
 */
public class TSCVector {
	private double texture;
	private double shape;
	private double color;
	private static final double TEXTURE_WEIGHT = 0.5;
	private static final double SHAPE_WEIGHT = 1;
	private static final double COLOR_WEIGHT = 0.8;
	
	public TSCVector(double t,double s,double c){
		texture = t;
		shape = s;
		color = c;
	}
	
	public double getVectorSum(){
		return texture*TEXTURE_WEIGHT+shape*SHAPE_WEIGHT+color*COLOR_WEIGHT;
	}
	
}
