package com.imageSim.server.FeatureExtraction;

import ij.process.ColorProcessor;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import de.lmu.ifi.dbs.jfeaturelib.features.AutoColorCorrelogram;

public class ColorExtract implements FeatureExtraction{
	private File f;
	private AutoColorCorrelogram descriptor = null;
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
		return descriptor.getFeatures();
	}
	
}