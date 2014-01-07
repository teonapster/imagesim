package com.imageSim.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ImageSim extends LayoutPanel implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	private VerticalPanel uploadPanel = new VerticalPanel();
	private VerticalPanel searchPanel = new VerticalPanel();
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync socket = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	
	      public void onModuleLoad(){
	    	  Button upload = new Button("Upload");
	    	  Button search = new Button("Search");
	    	  
	    	  HorizontalPanel panel = new HorizontalPanel();
	    	  panel.add(upload);
	    	  panel.add(search);
	    	  RootPanel.get().add(panel);
	    	  upload.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					resetPanels();
					onUpload();
					RootPanel.get().add(uploadPanel);
				}
	    	  });
	    	  
	    	  search.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					resetPanels();
					onSearch();
					RootPanel.get().add(searchPanel);
				}
	    		  
	    	  });
	      }
	      public void resetPanels(){
	    	  for(int i=0;i<searchPanel.getWidgetCount();i++)
					searchPanel.remove(i);
				for(int i=0;i<uploadPanel.getWidgetCount();i++)
					uploadPanel.remove(i);
	      }
	      public void onSearch(){
	    	  VerticalPanel panel = new VerticalPanel();
	    	  
	    	  HTML html = new HTML(
		    	      "<div class='startForm'> <form method='POST' action='fileUploaderServlet?upload=0' enctype='multipart/form-data' > "+
		    	    		  "<div class='formElement'>Κ:</div><input type='text' class='formElement' value='3' name='kapa' id='kapa'/><br/>"+
		    	    		  "<input type='file' name='file' id='file'/> "+
		    	      "</br><input type='submit' class='formElement gwt-Button' value='Search' name='upload' id='upload' /></form></div>", true);
    
	    	  
	    	 
	    	  panel.add(html);
	    	  searchPanel.add(panel);
	      }
		  public void onUpload() {
		    // Create a FormPanel and point it at a service.
		    final FormPanel form = new FormPanel();
		    // Add them to the root panel.
	  	    VerticalPanel panel = new VerticalPanel();
	  	  HTML html = new HTML(
	    	      "<div class='startForm'><form method='POST' action='fileUploaderServlet' enctype='multipart/form-data' > File:"+
        "<input type='file' class='formElement' name='file' id='file' multiple='multiple' /> <br/>"+
        //"Destination:<input type='text' value='/tmp' name='destination' />"+
        "</br><input type='submit' class='formElement gwt-Button'value='Upload' name='upload' id='upload' /></form></div>", true);

	   	    panel.add(html);
		    uploadPanel.add(panel);
		  }
}
