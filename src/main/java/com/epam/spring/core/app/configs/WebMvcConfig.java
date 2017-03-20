package com.epam.spring.core.app.configs;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.epam.spring.core.web.converter.PdfHttpMessageConverter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@EnableWebMvc
@Configuration
@Import({ SecurityConfiguration.class })
@ComponentScan(basePackages = {"com.epam.spring.core.web", "com.epam.spring.core.web.exception"})
public class WebMvcConfig extends WebMvcConfigurerAdapter {	
	
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }
    
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/").setCachePeriod(31556926);;  
	}
	
	 
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false).
        defaultContentType(MediaType.APPLICATION_JSON);
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {		  
		converters.add(pfdHttpMessageConverter());
		converters.add(mappingJackson2HttpMessageConverter());	  
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver multiPartresolver = new CommonsMultipartResolver();
	    return multiPartresolver;
	}
	
	@Bean 
	public FreeMarkerViewResolver freemarkerViewResolver() { 
	    FreeMarkerViewResolver resolver = new FreeMarkerViewResolver(); 
	    resolver.setCache(true); 
	    resolver.setPrefix(""); 
	    resolver.setSuffix(".ftl"); 
	    return resolver; 
	}
	
	@Bean 
	public FreeMarkerConfigurer freemarkerConfig() { 
	    FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
	    freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/views/ftl/");
	    return freeMarkerConfigurer; 
	}
	    
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false));
		return converter;
	}
	
	@Bean
	public PdfHttpMessageConverter pfdHttpMessageConverter() {
		PdfHttpMessageConverter converter = new PdfHttpMessageConverter();
		return converter;
	}	
	
	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);
		return resolver;
	}
	
}
