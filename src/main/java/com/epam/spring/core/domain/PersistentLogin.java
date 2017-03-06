package com.epam.spring.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="persistent_logins")
public class PersistentLogin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5564877363955325772L;

	@Id 
	private String series;
	 
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;
     
    @Column(name = "token", unique = true, nullable = false)
    private String token;
     
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUsed;
 
    public String getSeries() {
        return series;
    }
 
    public void setSeries(String series) {
        this.series = series;
    }
 
    public String getUserName() {
        return userName;
    }
 
    public void setUsername(String userName) {
        this.userName = userName;
    }
 
    public String getToken() {
        return token;
    }
 
    public void setToken(String token) {
        this.token = token;
    }
 
    public Date getLastUsed() {
        return lastUsed;
    }
 
    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }
    
}