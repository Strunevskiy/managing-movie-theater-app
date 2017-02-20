package com.epam.spring.core.web;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.spring.core.app.AppConfig;
import com.epam.spring.core.app.configs.WebConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;

@WebAppConfiguration
@ContextConfiguration(classes = { AppConfig.class, WebConfig.class })
public class FileUploadTests extends AbstractTestNGSpringContextTests {
	
	private static final String PATH_FILE_UPLOAD = "/upload/files/usersEvents";

	private static final String EVENTS_JSON = "events.json";
	private static final String USERS_JSON = "users.json";

    @Autowired
    private WebApplicationContext webAppContext;
    private MockMvc mockMvc;
    private MockMultipartFile usersJsonFile;
    private MockMultipartFile eventsJsonFile;
    
    
    @BeforeClass
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webAppContext).build();
    }
    
    @BeforeClass
    public void initTest() throws IOException {
    	Resource resourceEvents = new ClassPathResource(EVENTS_JSON);
    	byte[] eventsJson = IOUtils.toByteArray(resourceEvents.getInputStream());
    	
    	Resource resourceUsers = new ClassPathResource(USERS_JSON);
    	byte[] usersJson = IOUtils.toByteArray(resourceUsers.getInputStream());
    	
    	this.eventsJsonFile = new MockMultipartFile("fileEvents", "fileEvents.json", null, eventsJson);    	
    	this.usersJsonFile = new MockMultipartFile("fileUsers", "fileUsers.json" , null, usersJson);
    }

    @Test
    public void shouldSaveUploadedFile() throws Exception {
        this.mockMvc.perform(fileUpload(PATH_FILE_UPLOAD)
        		.file(usersJsonFile)
        		.file(eventsJsonFile))
                .andExpect(status().isOk());        
    }

    @Test
    public void shoul500WhenMissingOneFile() throws Exception {
        this.mockMvc.perform(fileUpload(PATH_FILE_UPLOAD).file(usersJsonFile))
        .andExpect(status().isInternalServerError());
        
        this.mockMvc.perform(fileUpload(PATH_FILE_UPLOAD).file(eventsJsonFile))
        .andExpect(status().isInternalServerError());
    }

}