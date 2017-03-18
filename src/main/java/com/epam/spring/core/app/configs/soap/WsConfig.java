package com.epam.spring.core.app.configs.soap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
@ComponentScan("com.epam.spring.core") 
public class WsConfig extends WsConfigurerAdapter {

	@Bean(name = "user")
	public DefaultWsdl11Definition defaultWsdl11DefinitionUser(XsdSchema userSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("user");
		wsdl11Definition.setLocationUri("/soap");
		wsdl11Definition.setTargetNamespace("http://spring-course.com/schemas");
		wsdl11Definition.setSchema(userSchema);
		return wsdl11Definition;
	}
	
	@Bean(name = "event")
	public DefaultWsdl11Definition defaultWsdl11DefinitionEvent(XsdSchema eventSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("event");
		wsdl11Definition.setLocationUri("/soap");
		wsdl11Definition.setTargetNamespace("http://spring-course.com/schemas");
		wsdl11Definition.setSchema(eventSchema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema userSchema() {
		return new SimpleXsdSchema(new ClassPathResource("/event.xsd"));
	}
	
	@Bean
	public XsdSchema eventSchema() {
		return new SimpleXsdSchema(new ClassPathResource("/user.xsd"));
	}
	
}
