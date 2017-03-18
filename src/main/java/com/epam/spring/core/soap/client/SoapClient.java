package com.epam.spring.core.soap.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
public class SoapClient {

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    public <T> void sendRequest(T objectToSend) {
        webServiceTemplate.marshalSendAndReceive(objectToSend);        
    }
    
    public <T> T sendRequest(Object objectToSend, Class<T> payloadOfRpMsg) {
        Object resultOfRq = webServiceTemplate.marshalSendAndReceive(objectToSend);   
        return payloadOfRpMsg.cast(resultOfRq);
    }
    
}
