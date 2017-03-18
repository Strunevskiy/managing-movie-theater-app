package com.epam.spring.core.utils.soap;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.ws.client.core.WebServiceTemplate;

public class WebServiceClient {
	
	 private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		    
	 public void setDefaultUri(String defaultUri) {
		 webServiceTemplate.setDefaultUri(defaultUri);
	 }
	 	    
	 public void simpleSendAndReceive() {
		 StreamSource source = new StreamSource();
		 StreamResult result = new StreamResult(System.out);
		 webServiceTemplate.sendSourceAndReceiveToResult(source, result);
		 
	 }
	    
	 public void customSendAndReceive() {
		 StreamSource source = new StreamSource();	        
		 StreamResult result = new StreamResult(System.out);
		 webServiceTemplate.sendSourceAndReceiveToResult("http://localhost:8080/AnotherWebService", source, result);
	 }
		    
}
