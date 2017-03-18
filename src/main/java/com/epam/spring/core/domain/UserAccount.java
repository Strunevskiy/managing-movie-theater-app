package com.epam.spring.core.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.epam.spring.core.domain.user.User;
import com.epam.spring.core.domain.utils.jaxb.BigDecimalAdaptor;

/**
 * @author alehstruneuski
 */
@Entity
@Table(name = "user_account")
@XmlRootElement(name = "user_account")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "moneyAmount" })
public class UserAccount extends DomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3643386315023024393L;

	@Column(name = "money_amount", precision = 19, scale = 0)
	@XmlElement(name = "money_amount") 
	@XmlJavaTypeAdapter(BigDecimalAdaptor.class)
	private BigDecimal moneyAmount;
	
    @OneToOne(mappedBy = "userAccount")
    @XmlTransient
	private User user;

	public UserAccount() {
	}
	
	public BigDecimal getMoneyAmount() {
		return moneyAmount;
	}

	public void setMoneyAmount(BigDecimal moneyAmount) {
		this.moneyAmount = moneyAmount;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
