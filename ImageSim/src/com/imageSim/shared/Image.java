package com.imageSim.shared;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.google.gwt.user.server.Base64Utils;
import com.imageSim.server.UploadServlet;

public class Image implements Serializable {
	//Define Image Features
	private double [] texture; 
	private double [] shape;
	private double [] color;
	
	//Image's Filename
	private String filename;
	
	//Image's id
	private int id;
	
	public Image(){
		
	}
	public Image(int id, String filename){
		this.id = id;
		this.filename = filename;
	}
	
	//*******************Define Setters HERE******************//
	public void setTexture(double [] texture){
		this.texture = new double[texture.length];
		this.texture = texture;
	}
	
	public void setShape(double [] shape){
		this.shape= new double[shape.length];
		this.shape = shape;
	}
	
	public void setColor(double [] color){
		this.color= new double[color.length];
		this.color = color;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	//*******************EOF Setters definition***************//
	

	//*******************Define Getters HERE******************//
	public String getFilename() {
		return filename;
	}

	public int getId() {
		return id;
	}

	public double[] getTexture() {
		return texture;
	}

	public double[] getShape() {
		return shape;
	}

	public double[] getColor() {
		return color;
	}
	//*******************EOF Getters Definition***************//

	public String imageAsString() {
		String image = null;
		File f = new File(File.separator+UploadServlet.UPLOAD_DIRECTORY+filename);
		try {
			byte[] fileContent = Files.readAllBytes(f.toPath());
			image = Base64Utils.toBase64(fileContent);
			image = "data:image/jpg;base64,"+image;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}
}
