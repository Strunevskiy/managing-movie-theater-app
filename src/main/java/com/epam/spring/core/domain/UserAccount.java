package com.epam.spring.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.epam.spring.core.domain.user.User;

/**
 * @author alehstruneuski
 */
@Entity
@Table(name = "user_account")
public class UserAccount extends DomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3643386315023024393L;

	@Column(name = "money_amount", precision = 19, scale = 0)
	private BigDecimal moneyAmount;
	
    @OneToOne(mappedBy = "userAccount")
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
