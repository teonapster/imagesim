package com.imageSim.server.FeatureExtraction;
/**
 * @author teonapster
 * Contains Texture,Shape,Color Vector 
 */
public class TSCVector {
	private double texture;
	private double shape;
	private double color;
	private double TEXTURE_WEIGHT = 0.5	;
	private double SHAPE_WEIGHT = 0.5;
	private double COLOR_WEIGHT = 0.5;
	private double NORMALIZATION_MAX = 100.0;
	private double NORMALIZATION_MIN = 0.0;
	
	public TSCVector(double t,double s,double c){
		texture = t;
		shape = s;
		color = c;
	}
	
	public void setWeights(double t, double s, double c){
		TEXTURE_WEIGHT = t;
		SHAPE_WEIGHT = s;
		COLOR_WEIGHT = c;
	}
	
	public double getVectorSumNormalized(){
		
		return texture*TEXTURE_WEIGHT+shape*SHAPE_WEIGHT+color*COLOR_WEIGHT;
	}
	
}
