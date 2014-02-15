package com.imageSim.server.FeatureExtraction;

import ij.process.ColorProcessor;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

import de.lmu.ifi.dbs.jfeaturelib.LibProperties;
import de.lmu.ifi.dbs.jfeaturelib.features.AutoColorCorrelogram;

public class ColorExtract implements FeatureExtraction{
	private File f;
	private AutoColorCorrelogram descriptor = null;
	public static double minVal = 0.0;
	public static double maxVal = 1.0;
	public ColorExtract(){
		
	}
	
	@Override
	public List<double[]> FeatureExtractRun(String imagePath){
		 
		try {
			f = new File(imagePath);
	        ColorProcessor image;
		    image = new ColorProcessor(ImageIO.read(f));
		    // initialize the descriptor, set the properties and run it
		    descriptor= new AutoColorCorrelogram();
		    //descriptor.setProperties(prop);
		    descriptor.run(image);
				
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		/*List<double[]> d= descriptor.getFeatures();
		double [] array = d.get(0);
		for(int i=0;i<array.length;i++){
			array[i]= Math.round(array[i]*10000.0)/10000.0;
		}
		d.set(0,array);
		return d;*/
		return descriptor.getFeatures();
	}
	
}
