package com.epam.spring.core.domain.user;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.epam.spring.core.domain.DomainObject;
import com.epam.spring.core.domain.UserAccount;
import com.epam.spring.core.domain.ticket.Ticket;
import com.epam.spring.core.domain.utils.jaxb.DateAdapter;
import com.epam.spring.core.domain.utils.orm.RoleSetConverter;

/**
 * @author alehstruneuski
 */

@Entity
@Table(name = "user")
@Convert(attributeName = "roles", converter = RoleSetConverter.class)
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "firstName", "lastName", "email", "password", "birthday", "roles", "userAccount" })
@XmlSeeAlso(value = { UserAccount.class, Role.class })
public class User extends DomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7770363918609493353L;
	
	@Column(name = "first_name")
	@XmlElement(name = "first_name") 
	private String firstName;
	
	@Column(name = "last_name")
	@XmlElement(name = "last_name") 
	private String lastName;
	
	@Column(name = "email")
	@XmlElement(name = "email") 
    private String email;
	
	@Column(name = "password")
	@XmlElement(name = "password") 
    private String password;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birthday")
	@XmlElement(name = "birthday") 
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date birthday;	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@XmlTransient
	private Set<Ticket> tickets = new HashSet<>();

	@Column(name = "roles")
    @XmlElement(name = "role")
	private Set<Role> roles = new HashSet<>();
	 
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_account_id")
    @XmlElement(name = "user_account")
	private UserAccount userAccount;

	public User() {
    }
	
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
    
    public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
    public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@Override
	public int hashCode() {
		 return Objects.hash(getId(), firstName, lastName, email, birthday);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

}
