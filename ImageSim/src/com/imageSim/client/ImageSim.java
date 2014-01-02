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
					//RootPanel.get().remove(1);
					onUpload();
				}
	    	  });
	    	  
	    	  search.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					//RootPanel.get().remove(1);
					onSearch();
				}
	    		  
	    	  });
	      }
	      
	      public void onSearch(){
	    	  VerticalPanel panel = new VerticalPanel();
	    	  HTML html = new HTML(
		    	      " <form method='POST' action='fileUploaderServlet?upload=0&k=10' enctype='multipart/form-data' > File:"+
		    	      "<input type='file' name='file' id='file'/> <br/>"+
		    	      "Κ:<input type='text' value='' name='knum' id='knum'/>"+
		    	      "</br><input type='submit' value='Upload' name='upload' id='upload' /></form>", true);
    
	    	  
	    	  Button search = new Button("Search");
	    	  search.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					//socket
				}
	    		  
	    	  });
	    	  panel.add(html);
	    	  panel.add(search);
	    	  RootPanel.get().add(panel);
	      }
		  public void onUpload() {
		    // Create a FormPanel and point it at a service.
		    final FormPanel form = new FormPanel();
		    
		    // Add them to the root panel.
	  	    VerticalPanel panel = new VerticalPanel();
	  	  HTML html = new HTML(
	    	      " <form method='POST' action='fileUploaderServlet' enctype='multipart/form-data' > File:"+
        "<input type='file' name='file' id='file' multiple='multiple' /> <br/>"+
        //"Destination:<input type='text' value='/tmp' name='destination' />"+
        "</br><input type='submit' value='Upload' name='upload' id='upload' /></form>", true);

	   	    panel.add(html);
		    
		    // 	Set form's action
		    //form.setAction(GWT.getModuleBaseURL()+"fu");
		    /*
		    // Because we're going to add a FileUpload widget, we'll need to set the
		    // form to use the POST method, and multipart MIME encoding.
		    form.setEncoding(FormPanel.ENCODING_MULTIPART);
		    form.setMethod(FormPanel.METHOD_POST);
		    
		    // Create a panel to hold all of the form widgets.
		    VerticalPanel panel = new VerticalPanel();
		    form.setWidget(panel);
		    
		    
		    
		    // Create a TextBox, giving it a name so that it will be submitted.
		    final TextBox tb = new TextBox();
		    tb.setName("textBoxFormElement");
		    panel.add(tb);

		    // Create a FileUpload widget.
		    final FileUpload upload = new FileUpload();
		    
		    upload.setName("uploadFormElement");
		    panel.add(upload);

		    // Add a 'submit' button.
		    panel.add(new Button("Upload", new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  form.submit();
		      }
		    }));
			*/

		    RootPanel.get().add(panel);
		  }
}
