package com.epam.spring.core.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.spring.core.domain.PersistentLogin;

@Repository
@Transactional
public class TokenRepositoryImpl implements PersistentTokenRepository {
	  
	@PersistenceContext  
	private EntityManager em;
  
    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        PersistentLogin persistentLogin = new PersistentLogin();
        persistentLogin.setUsername(token.getUsername());
        persistentLogin.setSeries(token.getSeries());
        persistentLogin.setToken(token.getTokenValue());
        persistentLogin.setLastUsed(token.getDate());
        getSession().persist(persistentLogin);
    }
 
    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        try {
            Criteria crit = getSession().createCriteria(PersistentLogin.class);
            crit.add(Restrictions.eq("series", seriesId));
            PersistentLogin persistentLogin = (PersistentLogin) crit.uniqueResult();
            return new PersistentRememberMeToken(persistentLogin.getUserName(), persistentLogin.getSeries(), persistentLogin.getToken(), persistentLogin.getLastUsed());
        } catch (Exception e) {
            return null;
        }
    }
 
    @Override
    public void removeUserTokens(String username) {
        Criteria crit = getSession().createCriteria(PersistentLogin.class);
        crit.add(Restrictions.eq("userName", username));
        PersistentLogin persistentLogin = (PersistentLogin) crit.uniqueResult();
        if (persistentLogin != null) {
        	getSession().delete(persistentLogin);
        }
    }
 
    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        Criteria crit = getSession().createCriteria(PersistentLogin.class);
        crit.add(Restrictions.eq("series", series));
        
        PersistentLogin persistentLogin = (PersistentLogin) crit.uniqueResult();
    	persistentLogin.setToken(tokenValue);
        persistentLogin.setLastUsed(lastUsed);
        getSession().update(persistentLogin);
    }
    
    private Session getSession(){
    	HibernateEntityManager hem = em.unwrap(HibernateEntityManager.class);
        return hem.getSession();
    }
 
}
