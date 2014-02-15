package com.imageSim.server;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.ArrayUtils;

import com.imageSim.server.FeatureExtraction.ColorExtract;
import com.imageSim.server.FeatureExtraction.ShapeExtract;
import com.imageSim.server.FeatureExtraction.TSCVector;
import com.imageSim.server.FeatureExtraction.TextureExtract;
import com.imageSim.shared.Distance;
import com.imageSim.shared.Image;
import com.imageSim.shared.MinHeap;
import com.imageSim.shared.MyArray;
 
/**
 * A Java servlet that handles file upload from client.
 * @author www.codejava.net
 */

public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public static final String UPLOAD_DIRECTORY = "images";
    private static final int THRESHOLD_SIZE     = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 100; //100MB
    private static final double ORIGINAL_QUERY_WEIGHT   = 1.0;
    private static final double POSITIVE_FEEDBACK_WEIGHT   = 0.2;
    private Image searchImage = null;
    private DBConnector db = new DBConnector();
    private double textureWeight,shapeWeight,colorWeight;
    /**
     * handles file upload via HTTP POST method
     */
    
   /* */
    
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	    	
        // checks if the request actually contains upload file
      /*  if (!ServletFileUpload.isMultipartContent(request)) {
            PrintWriter writer = response.getWriter();
            writer.println("Request does not contain upload data");
            writer.flush();
            return;
        }*/
        
        //Get parameters here
        //Upload =1 when user want just to upload images. Zero when want to find similar images
    	
        boolean uploadMode = true;
        boolean rf = false; // relevance feedback mode disabled
        
        try{
            uploadMode = Integer.valueOf(request.getParameter("upload"))==1?true:false;
        }catch (NumberFormatException nfe){}
        try{
            rf = Integer.valueOf(request.getParameter("rf"))==1?true:false;
        }catch (NumberFormatException nfe){}
        
        
        String kNum = null;
        int imgId=0;
        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(THRESHOLD_SIZE);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
         
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);
         
        // constructs the directory path to store upload file
        String uploadPath = getServletContext().getRealPath("")
            + File.separator + UPLOAD_DIRECTORY;
        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String filePath = null;
        String fileName = null;
      //Connect to database
        db.connect();
        try {
            // parses the request's content to extract file data
        	
            List formItems = upload.parseRequest(request);
            Iterator iter = formItems.iterator();
            
            
            
            // iterates over form's fields
            if(ServletFileUpload.isMultipartContent(request))
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                
                // processes only fields that are not form fields
                if (!item.isFormField()&&!rf) {
                     fileName = new File(item.getName()).getName();
                    filePath = uploadPath + File.separator + fileName;
                    File storeFile = new File(filePath);
                     
                    // saves the file on disk
                    item.write(storeFile);
                    
                    
                    //Color Extraction & insert to DB
                    ColorExtract ce = new ColorExtract();
                    List<double[]> colorList  = ce.FeatureExtractRun(filePath);
                    
                    //Texture Extraction & insert to DB
                    TextureExtract te = new TextureExtract();
                    List<double[]> textureList  = te.FeatureExtractRun(filePath);
                   
                    //ShapeExtraction & insert to DB
                    ShapeExtract se = new ShapeExtract();
                    List<double[]> shapeList  = se.FeatureExtractRun(filePath);
                    if(uploadMode){
                    	imgId = db.insertImage(fileName);
    	                db.insertCorrelogramVector(colorList, imgId);
    	                db.insertTextureVector(textureList, imgId);
    	                db.insertShapeVector(shapeList, imgId);
                    }
                    else if(!uploadMode && !rf){
                    	searchImage = new Image(imgId,fileName);
                    	searchImage.setColor(ArrayUtils.toPrimitive(ArrayUtils.toObject(colorList.get(0))));
                    	searchImage.setShape(ArrayUtils.toPrimitive(ArrayUtils.toObject(shapeList.get(0))));
                    	searchImage.setTexture(ArrayUtils.toPrimitive(ArrayUtils.toObject(textureList.get(0))));
                    	request.setAttribute("imageQuery", searchImage);
                    	Image [] results = getKRelevantImages(searchImage, Integer.parseInt(kNum));
             	        request.setAttribute("images", Arrays.asList(results));
                    }
                }
                else if (item.isFormField()){
                	String fieldName = item.getFieldName();
                	String fieldValue = item.getString();
                	if(fieldName.equals("kapa")){
                		kNum = fieldValue;
                		request.setAttribute("kapa",Integer.parseInt(kNum));
                	}
                	else if(fieldName.equals("textureWeight")){
                		textureWeight = Double.parseDouble(fieldValue);
                		request.setAttribute("textureWeight",textureWeight);
                	}
                	else if(fieldName.equals("shapeWeight")){
                		shapeWeight = Double.parseDouble(fieldValue);
                		request.setAttribute("shapeWeight",shapeWeight);
                	}
                	else if(fieldName.equals("colorWeight")){
                		colorWeight = Double.parseDouble(fieldValue);
                		request.setAttribute("colorWeight",colorWeight);
                	}
                }
                
               
                
            }
            request.setAttribute("message", "Upload has been done successfully!");
         } catch (Exception ex) {
            request.setAttribute("message", "There was an error: " + ex.getMessage());
        }
        if(!uploadMode){
        	if (!uploadMode && rf && !ServletFileUpload.isMultipartContent(request)){
        		searchImage = (Image) request.getSession().getAttribute("iq");
        		textureWeight = (double) (request.getSession().getAttribute("textureWeight"));
        		shapeWeight = (double) (request.getSession().getAttribute("shapeWeight"));
        		colorWeight = (double) (request.getSession().getAttribute("colorWeight"));
            	kNum = (String) request.getParameter("kapa");
            	request.setAttribute("kapa",Integer.parseInt(kNum));
            	request.setAttribute("textureWeight",textureWeight);
            	request.setAttribute("shapeWeight",shapeWeight);
            	request.setAttribute("colorWeight",colorWeight);
            	Image [] positiveFeedback = getSelectedImages(request);
            	RocchioMethod(positiveFeedback); // update searchImage
            	request.setAttribute("imageQuery", searchImage);
            	
            	Image [] results = getKRelevantImages(searchImage, Integer.parseInt(kNum));
            	request.setAttribute("images", Arrays.asList(results));
            }
        	request.getRequestDispatcher("WEB-INF/imageRF.jsp").forward(request, response);
        }
    }
    
	private  Image [] getKRelevantImages(Image searchImg,int k) throws IllegalArgumentException {
    	Image [] images = new Image [k];
    	double [] distances = new double [k];// debug reason
    	
		ArrayList<Image> img = db.getVectors();
		double [] distance = new double[img.size()];
		MinHeap mh = new MinHeap(MinHeap.MAX_HEAP_SIZE);
		for(int i=0;i<img.size();++i){
			TSCVector originalQuery = Distance.euclideanDist(searchImg, img.get(i)); 
			originalQuery.setWeights(textureWeight, shapeWeight, colorWeight);
			distance[i] = originalQuery.getVectorSumNormalized();
			mh.insert(new Distance(i,distance[i]));
		}
		
		for(int i=0;i<k;++i){
			distances[i] = mh.removemin().getDist();
			images[i] = img.get(mh.removemin().getIndex());
		}
		
		return images;
	}
    private Image [] getSelectedImages(HttpServletRequest request){
    	String [] vals = request.getParameterValues("positiveFeedback");
    	Image [] result = null;
    	if(vals!=null){
    		result = new Image[vals.length];
    		for(int i=0;i<vals.length;++i){
    			result[i]= db.getImage(Integer.parseInt(vals[i]));
    		}
    	}
    	return result;
    }
    private void RocchioMethod(Image [] posImage){
    	
    	//Get original weighted Queries 
    	double [] OriginalTexture = MyArray.multiplyWithFactor(ORIGINAL_QUERY_WEIGHT,searchImage.getTexture());
    	double [] OriginalShape =   MyArray.multiplyWithFactor(ORIGINAL_QUERY_WEIGHT,searchImage.getShape());
    	double [] OriginalColor =   MyArray.multiplyWithFactor(ORIGINAL_QUERY_WEIGHT, searchImage.getColor());
    	
    	//Get weighted Positive Feedback 
    	double [] posTexture=new double[OriginalTexture.length],
    			posShape = new double [OriginalShape.length],
    			posColor = new double[OriginalColor.length];
    	for(int i=0;i<posImage.length;++i){
    		posTexture =MyArray.addArrayElements(posTexture, posImage[i].getTexture());
    		posShape = MyArray.addArrayElements(posShape, posImage[i].getShape());
    		posColor = MyArray.addArrayElements(posColor, posImage[i].getColor());
    	}
    	posTexture = MyArray.multiplyWithFactor(1.0/posImage.length, posTexture);
    	posShape = MyArray.multiplyWithFactor(1.0/posImage.length, posShape);
    	posColor = MyArray.multiplyWithFactor(1.0/posImage.length, posColor);
    	
    	//Add queries
    	searchImage.setTexture(MyArray.addArrayElements(OriginalTexture,posTexture));
    	searchImage.setShape(MyArray.addArrayElements(OriginalShape,posShape));
    	searchImage.setColor(MyArray.addArrayElements(OriginalColor,posColor));
    	
    }
}