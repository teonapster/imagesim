package com.imageSim.server;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.imageSim.server.FeatureExtraction.TextureExtract;
import com.imageSim.shared.Image;

public class DBConnector {
	private Connection connection = null;
	
	public DBConnector(){
		
	}
	
	public void connect(){
		try {
			 
			Class.forName("org.postgresql.Driver");
 
		} catch (ClassNotFoundException e) {
 
			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;
 
		}
 
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/myDb", "postgres",
					"TeoKourou*9");
 
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
 
		if (connection != null) {
			System.out.println("Database connector ON");
		} else {
			System.out.println("Database connector OFF");
		}
		
	}
	
	public boolean insertCorrelogramVector(List<double[]> list,int imgId){
		boolean success = true;
		Double [] array = new Double[list.get(0).length];
		array = ArrayUtils.toObject(list.get(0));
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement("INSERT INTO image_color (image_id,color_vector) VALUES (?,?);");
		    Array myArray = connection.createArrayOf("float8", array);
	        stmt.setInt(1, imgId);
	        stmt.setArray(2, myArray);
	        stmt.execute();
	        stmt.close();
	        System.out.print("Image Color Vector insertion completed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
	
	public boolean insertTextureVector(List<double[]> list,int imgId){
		boolean success = true;
		Double [] array = new Double[list.get(0).length];
		array = ArrayUtils.toObject(list.get(0));
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement("INSERT INTO image_texture (image_id,texture_vector) VALUES (?,?);");
		    Array myArray = connection.createArrayOf("float8", array);
	        stmt.setInt(1, imgId);
	        stmt.setArray(2, myArray);
	        stmt.execute();
	        stmt.close();
	        System.out.print("Image Texture Vector insertion completed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
	
	public boolean insertShapeVector(List<double[]> list,int imgId){
		boolean success = true;
		Double [] array = new Double[list.get(0).length];
		array = ArrayUtils.toObject(list.get(0));
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement("INSERT INTO image_shape (image_id,shape_vector) VALUES (?,?);");
		    Array myArray = connection.createArrayOf("float8", array);
	        stmt.setInt(1, imgId);
	        stmt.setArray(2, myArray);
	        stmt.execute();
	        stmt.close();
	        System.out.print("Image Shape Vector insertion completed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
	
	public int insertImage(String filename){
		int imgId=-1;
		Statement stmt = null;
		try {
			String query = "INSERT INTO images VALUES ('"+filename+"') returning image_id;";
			stmt = connection.createStatement();
		    stmt.execute(query);
		    ResultSet rs = stmt.getResultSet();
		    if ( rs.next() ) {
		        // Retrieve the auto generated key(s).
		        imgId = rs.getInt(1);
		    }
	        stmt.close();
	        System.out.print("Image insertion completed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imgId;
	}
	
	public ArrayList<Image> getVectors(){
		ArrayList<Image> imageList = new ArrayList<Image>();
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement("Select images.image_id,image_path,texture_vector,shape_vector,color_vector " +
					"FROM images INNER JOIN image_texture ON images.image_id = image_texture.image_id" +
					"            INNER JOIN image_shape ON images.image_id = image_shape.image_id " +
					"			 INNER JOIN image_color ON images.image_id = image_color.image_id ");
			ResultSet rs = stmt.executeQuery();
			double [] t,s,c;
			int id;
			String imagePath;
			
			while(rs.next()){
				id = rs.getInt(1);
				imagePath = rs.getString(2);
				t = ArrayUtils.toPrimitive((Double[])rs.getArray(3).getArray());
				t= TextureExtract.normalizeTextureTable(t);
				s = ArrayUtils.toPrimitive((Double[])rs.getArray(4).getArray());
				c = ArrayUtils.toPrimitive((Double[])rs.getArray(5).getArray());
				
				//Initialize new Image
				Image img = new Image(id,imagePath);
				img.setTexture(t);
				img.setShape(s);
				img.setColor(c);
				imageList.add(img);
			}
			stmt.close();
	        System.out.print("Image Shape Vector insertion completed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imageList;
	}
	
	public Image getImage(int id){
		Image img = null;
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement("Select images.image_id,image_path,texture_vector,shape_vector,color_vector " +
					"FROM images INNER JOIN image_texture ON images.image_id = image_texture.image_id" +
					"            INNER JOIN image_shape ON images.image_id = image_shape.image_id " +
					"			 INNER JOIN image_color ON images.image_id = image_color.image_id " +
					"WHERE images.image_id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			double [] t,s,c;
			int imgId;
			String imagePath;
			
			while(rs.next()){
				id = rs.getInt(1);
				imagePath = rs.getString(2);
				t = ArrayUtils.toPrimitive((Double[])rs.getArray(3).getArray());
				t= TextureExtract.normalizeTextureTable(t);
				s = ArrayUtils.toPrimitive((Double[])rs.getArray(4).getArray());
				c = ArrayUtils.toPrimitive((Double[])rs.getArray(5).getArray());
				
				//Initialize new Image
				img = new Image(id,imagePath);
				img.setTexture(t);
				img.setShape(s);
				img.setColor(c);
			}
			stmt.close();
	        System.out.print("Image Shape Vector insertion completed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}
	
}
