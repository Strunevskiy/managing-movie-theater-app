package com.epam.spring.core.dao;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import com.epam.spring.core.domain.PersistentLogin;

@Repository
public class TokenRepositoryImpl implements PersistentTokenRepository {
	
    @Autowired
    private SessionFactory sessionFactory;
  
    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        //logger.info("Creating Token for user : {}", token.getUsername());
        PersistentLogin persistentLogin = new PersistentLogin();
        persistentLogin.setUsername(token.getUsername());
        persistentLogin.setSeries(token.getSeries());
        persistentLogin.setToken(token.getTokenValue());
        persistentLogin.setLastUsed(token.getDate());
        getSession().persist(persistentLogin);
    }
 
    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        //logger.info("Fetch Token if any for seriesId : {}", seriesId);
        try {
            Criteria crit = getSession().createCriteria(PersistentLogin.class);
            crit.add(Restrictions.eq("series", seriesId));
            PersistentLogin persistentLogin = (PersistentLogin) crit.uniqueResult();
            return new PersistentRememberMeToken(persistentLogin.getUserName(), persistentLogin.getSeries(), persistentLogin.getToken(), persistentLogin.getLastUsed());
        } catch (Exception e) {
           // logger.info("Token not found...");
            return null;
        }
    }
 
    @Override
    public void removeUserTokens(String username) {
        //logger.info("Removing Token if any for user : {}", username);
        Criteria crit = getSession().createCriteria(PersistentLogin.class);
        crit.add(Restrictions.eq("userName", username));
        PersistentLogin persistentLogin = (PersistentLogin) crit.uniqueResult();
        if (persistentLogin != null) {
            //logger.info("rememberMe was selected");
        	getSession().delete(persistentLogin);
        }
    }
 
    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        //logger.info("Updating Token for seriesId : {}", seriesId);
        Criteria crit = getSession().createCriteria(PersistentLogin.class);
        crit.add(Restrictions.eq("series", series));
        
        PersistentLogin persistentLogin = (PersistentLogin) crit.uniqueResult();
    	persistentLogin.setToken(tokenValue);
        persistentLogin.setLastUsed(lastUsed);
        getSession().update(persistentLogin);
    }
    
    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
 
}
