package com.epam.spring.core;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import com.epam.spring.core.app.AppConfig;
import com.epam.spring.core.app.configs.WebMvcConfig;
import com.epam.spring.core.app.configs.soap.WsConfig;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {     
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);
    	
        container.addListener(new ContextLoaderListener(rootContext));
        
        AnnotationConfigWebApplicationContext dispatcherContextSoap = new AnnotationConfigWebApplicationContext(); 
    	dispatcherContextSoap.register(WsConfig.class);
         
     	MessageDispatcherServlet dispatcherServletSoap = new MessageDispatcherServlet(dispatcherContextSoap);
     	dispatcherServletSoap.setApplicationContext(rootContext);
     	dispatcherServletSoap.setTransformWsdlLocations(true);
 		ServletRegistration.Dynamic dispatcherSoap = container.addServlet("dispatcher_soap", dispatcherServletSoap);
 		dispatcherSoap.setLoadOnStartup(1);
 		dispatcherSoap.addMapping("/soap/*");

 		
        AnnotationConfigWebApplicationContext dispatcherContextRest = new AnnotationConfigWebApplicationContext();
        dispatcherContextRest.register(WebMvcConfig.class);

        ServletRegistration.Dynamic dispatcherRest = container.addServlet("dispatcher", new DispatcherServlet(dispatcherContextRest));
        dispatcherRest.setLoadOnStartup(1);
        dispatcherRest.addMapping("/rest/*");
    }

}
