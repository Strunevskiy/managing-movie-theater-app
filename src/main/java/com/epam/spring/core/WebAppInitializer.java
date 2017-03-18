package com.epam.spring.core;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.epam.spring.core.app.AppConfig;
import com.epam.spring.core.app.configs.WebMvcConfig;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {     
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);
    	
        container.addListener(new ContextLoaderListener(rootContext));
        
        AnnotationConfigWebApplicationContext dispatcherContextRest = new AnnotationConfigWebApplicationContext();
        dispatcherContextRest.register(WebMvcConfig.class);

        ServletRegistration.Dynamic dispatcherRest = container.addServlet("dispatcher", new DispatcherServlet(dispatcherContextRest));
        dispatcherRest.setLoadOnStartup(1);
        dispatcherRest.addMapping("/");
    }

}
