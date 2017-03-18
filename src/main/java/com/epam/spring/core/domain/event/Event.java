package com.epam.spring.core.domain.event;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKey;
import javax.persistence.MapKeyTemporal;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.epam.spring.core.domain.Auditorium;
import com.epam.spring.core.domain.DomainObject;
import com.epam.spring.core.domain.ticket.Ticket;
import com.epam.spring.core.domain.utils.jaxb.BigDecimalAdaptor;

/**
 * @author alehstruneuski
 */
@Entity
@Table(name = "event")
@XmlRootElement(name = "event")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "name", "basePrice", "rating", "ticketPrice" })
@XmlSeeAlso(value = { EventRating.class })
public class Event extends DomainObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6975358077943195597L;
	
	@Column(name = "name")
	@XmlElement(name = "name") 
	private String name;
	
	@Column(name = "base_price")
	@XmlElement(name = "base_price") 
    private double basePrice;
	
	@Column(name = "rating")
	@Enumerated(EnumType.STRING)
    @XmlElement(name = "rating")
    private EventRating rating;
	
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(
			name = "local_time_auditorium", 
			joinColumns = @JoinColumn(name = "event_id"), 
			inverseJoinColumns = @JoinColumn(name = "auditorium_id"))
	@MapKey(name = "date")    
	@MapKeyTemporal(TemporalType.TIMESTAMP)
	@XmlTransient
	private Map<Date, Auditorium> auditoriums = new HashMap<>();
    
	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	@XmlTransient
	private Set<Ticket> tickets = new HashSet<>();
	
	@Column(name = "ticket_price", precision = 19, scale = 0)
	@XmlElement(name = "ticket_price") 
	@XmlJavaTypeAdapter(BigDecimalAdaptor.class)
	private BigDecimal ticketPrice;

	public void assignAuditorium(Date dateTime, Auditorium auditorium) {
    	auditoriums.put(dateTime, auditorium);
    }

    public boolean removeAuditoriumAssignment(Date dateTime) {
        return auditoriums.remove(dateTime) != null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public EventRating getRating() {
        return rating;
    }

    public void setRating(EventRating rating) {
        this.rating = rating;
    }

    public Map<Date, Auditorium> getAuditoriums() {
        return auditoriums;
    }

    public void setAuditoriums(Map<Date, Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }
    
    public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	public BigDecimal getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(BigDecimal ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	@Override
	public int hashCode() { 
		return Objects.hash(getId(), name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


}
