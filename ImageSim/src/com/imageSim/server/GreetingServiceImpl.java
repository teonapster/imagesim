package com.imageSim.server;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import com.imageSim.client.GreetingService;
import com.imageSim.shared.Distance;
import com.imageSim.shared.FieldVerifier;
import com.imageSim.shared.Image;
import com.imageSim.shared.MinHeap;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.server.Base64Utils;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	private DBConnector db;
	public GreetingServiceImpl(DBConnector db){
		this.db = db;
	}
	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	@Override
	public  String [] getKRelevantImages(int id,int k) throws IllegalArgumentException {
		String [] images = new String [k];
		ArrayList<Image> img = db.getVectors();
		Image searchImg = db.getImage(id);
		double [] distance = new double[img.size()];
		MinHeap mh = new MinHeap(MinHeap.MAX_HEAP_SIZE);
		for(int i=0;i<img.size();++i){
			//distance[i] = Distance.euclideanDist(searchImg, img.get(i));
			mh.insert(new Distance(i,distance[i]));
		}
		for(int i=1;i<=k;++i){
			Image tmp = img.get(mh.Heap[i].getIndex());
			images[i] = tmp.imageAsString();
		}
		return images;
	}

	@Override
	public void startDb() {
		db.connect();
	}

}
