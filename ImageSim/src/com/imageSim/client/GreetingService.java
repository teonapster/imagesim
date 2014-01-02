package com.imageSim.client;

import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	void startDb();
	String [] getKRelevantImages(int id,int k) throws IllegalArgumentException;
}
