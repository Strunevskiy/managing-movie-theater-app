package com.epam.spring.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.hibernate.annotations.GenericGenerator;

import com.epam.spring.core.domain.event.Event;
import com.epam.spring.core.domain.user.User;

/**
 * @author alehstruneuski
 */
@MappedSuperclass
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ UserAccount.class, User.class, Event.class })
public class DomainObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 607901199518892918L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name= "increment", strategy= "increment")
	@Column(name = "id", updatable = false, nullable = false)
	@XmlElement
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
