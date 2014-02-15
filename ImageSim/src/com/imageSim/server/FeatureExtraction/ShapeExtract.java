package com.imageSim.server.FeatureExtraction;


import ij.process.ColorProcessor;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import de.lmu.ifi.dbs.jfeaturelib.features.PHOG;

public class ShapeExtract implements FeatureExtraction{
	private File f;
	private PHOG descriptor = null;
	public static double minVal = 0.0;
	public static double maxVal = 1.0;
	//private CentroidBoundaryDistance descriptor = null; instead of phog
	public ShapeExtract(){
		
	}
	
	@Override
	public List<double[]> FeatureExtractRun(String imagePath){
		 
		try {
			f = new File(imagePath);
	        ColorProcessor image;
		    image = new ColorProcessor(ImageIO.read(f));
		    image.setMinAndMax(0.0, 10.0);
		    // initialize the descriptor, set the properties and run it
		    descriptor= new PHOG();
		    //descriptor.setProperties(prop);
		    descriptor.run(image);
				
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return descriptor.getFeatures();
	}
	
}

