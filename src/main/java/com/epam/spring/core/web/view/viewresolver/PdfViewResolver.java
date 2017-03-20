package com.epam.spring.core.web.view.viewresolver;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import com.epam.spring.core.web.view.TicketsPdfView;

public class PdfViewResolver implements ViewResolver {
	 
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
    	TicketsPdfView view = new TicketsPdfView();
        return view;   
    }
     
}
