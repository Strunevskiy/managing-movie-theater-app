package com.epam.spring.core.app.configs.soap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
public class SoapClientConfig {
	
	private static final String DEFAULT_URI_SOPA_URI = "http://localhost:8080/soap";

	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPaths(
        		"com.epam.spring.core.domain.utils.jaxb",
        		"com.epam.spring.core.soap.bean.event.jaxb",
        		"com.epam.spring.core.soap.bean.user.jaxb");
        return jaxb2Marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(jaxb2Marshaller());
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller());
        webServiceTemplate.setDefaultUri(DEFAULT_URI_SOPA_URI);
        return webServiceTemplate;
    }
	
}
