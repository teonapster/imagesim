package com.imageSim.server.FeatureExtraction;



import de.lmu.ifi.dbs.jfeaturelib.LibProperties;
import de.lmu.ifi.dbs.jfeaturelib.features.Haralick;
import de.lmu.ifi.dbs.jfeaturelib.features.Tamura;
import ij.process.ColorProcessor;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * This is  is a very basic Class that demonstrates the usage of a descriptor with plain Java without the commandline
 * exctractor.
 *
 * @author Franz
 */
public class TextureExtract implements FeatureExtraction{
	//private Tamura descriptor =null;
	private Haralick descriptor =null;
	private File f = null;
	public static double minVal = 0.0;
	public static double defaultMaxVal = 1800.0;
	public static double normalizedMaxVal = 1.0;
	public TextureExtract(){
		
	}

	@Override
	public List<double[]> FeatureExtractRun(String imagePath) {
		try {
			f = new File(imagePath);

	        ColorProcessor image;
		    image = new ColorProcessor(ImageIO.read(f));
		    // initialize the descriptor , set the properties and run it
		    descriptor = new Haralick();
		    //descriptor.setProperties(prop);
		    descriptor.run(image);
				
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//Change value range to 0-1
		List<double[]> d= descriptor.getFeatures();
		double [] array = TextureExtract.normalizeTextureTable(d.get(0));
		d.set(0,array);
		return d;
	}
	
	public static double [] normalizeTextureTable(double [] array){
		for(int i=0;i<array.length;i++){
			array[i]= array[i]*normalizedMaxVal/defaultMaxVal;
		}
		return array;
	}
}
