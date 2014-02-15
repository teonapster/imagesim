package com.imageSim.shared;

import com.imageSim.server.FeatureExtraction.ColorExtract;
import com.imageSim.server.FeatureExtraction.TSCVector;
import com.imageSim.server.FeatureExtraction.TextureExtract;

public class Distance {
	private int imgId;
	private double distance;
	public Distance(int id,double distance){
		this.imgId = id;
		this.distance = distance;
	}
	
	public int getIndex(){
		return this.imgId;
	}
	
	public double getDist(){
		return this.distance;
	}
	
	/**
	 * Find Euclidean Distance between two tables
	 * @param p fist table
	 * @param q second table
	 * @return distance in double
	 */
	public static double euclideanDist(double [] p, double[] q){
		double sum = 0.0;
		//Equal lengths
		for(int i=0;i<p.length;++i){
			sum+= Math.pow(p[i]-q[i], 2);
		}
		return Math.sqrt(sum);
	}
	
	/**
	 * Get euclidean distance vector (texture,shape,color)
	 * @param searchImg user given image1
	 * @param posibleRelevant image from DB
	 * @return Returns euclidean Distance between two images by texture,shape,color
	 */
	public static TSCVector euclideanDist(Image searchImg, Image posibleRelevant){
		double textureDist=0,shapeDist=0,colorDist=0;
		
		//Find Texture Distance between two images
		
		textureDist = euclideanDist(searchImg.getTexture(),posibleRelevant.getTexture());
		double size = Math.sqrt(searchImg.getTexture().length);
		textureDist = textureDist/size;
		
		//textureDist = textureDist*ColorExtract.maxVal/TextureExtract.maxVal;
		//todo convert to 0-1 range
		
		//Find Shape Distance between two images
		shapeDist = euclideanDist(searchImg.getShape(),posibleRelevant.getShape());
		size = Math.sqrt(searchImg.getShape().length);
		shapeDist = shapeDist/size;
		
		//Find Color Distance between two images
		colorDist = euclideanDist(searchImg.getColor(),posibleRelevant.getColor());
		size = Math.sqrt(searchImg.getColor().length);
		colorDist = colorDist/size;
		
		return new TSCVector(textureDist,shapeDist,colorDist);
	}
	
}
