package com.imageSim.server.FeatureExtraction;



import de.lmu.ifi.dbs.jfeaturelib.features.Haralick;
import ij.process.ColorProcessor;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * This is is a very basic Class that demonstrates the usage of a descriptor with plain Java without the commandline
 * exctractor.
 *
 * @author Franz
 */
public class TextureExtract implements FeatureExtraction{
	private Haralick descriptor =null;
	private File f = null;
	public TextureExtract(){
		
	}

	@Override
	public List<double[]> FeatureExtractRun(String imagePath) {
		try {
			f = new File(imagePath);
	        ColorProcessor image;
		    image = new ColorProcessor(ImageIO.read(f));
		    // initialize the descriptor, set the properties and run it
		    descriptor = new Haralick();
		    //descriptor.setProperties(prop);
		    descriptor.run(image);
				
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return descriptor.getFeatures();
	}
	
}
