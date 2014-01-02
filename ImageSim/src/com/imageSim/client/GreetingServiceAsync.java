package com.imageSim.client;


import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
	void getKRelevantImages(int id, int k, AsyncCallback<String []> callback)throws IllegalArgumentException;
	void startDb(AsyncCallback<Void> callback);
}
