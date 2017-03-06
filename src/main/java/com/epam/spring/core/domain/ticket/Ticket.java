package com.epam.spring.core.domain.ticket;

import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.epam.spring.core.domain.Auditorium;
import com.epam.spring.core.domain.DomainObject;
import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.user.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author alehstruneuski
 */
@Entity
@Table(name = "ticket")
public class Ticket extends DomainObject implements Comparable<Ticket> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 655028145987433327L;
	
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_fk")
	private User user;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="event_fk")
    private Event event;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="auditorium_fk")
    private Auditorium auditorium;
    
	@Column(name = "seat")
    private long seat;
	
	@Column(name = "ticket_price")
    private double ticketPrice;
	
	@Column(name = "date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonDateSerializer.class)
	private Date date;
	
	@Transient
	private boolean isVipSeat;

    public Ticket() {
    }
    
	public Ticket(User user, Event event, long seat) {
        this.user = user;
        this.event = event;
        this.seat = seat;
    }
	
	public Ticket(User user, Event event, Date date, long seat) {
		this(user, event, seat);
		this.date = date;
    }

    public boolean isVipSeat() {
    	return isVipSeat;
    }
    
    public User getUser() {
        return user;
    }
    
	public void setUser(User user) {
		this.user = user;
	}

    public Event getEvent() {
        return event;
    }
    
    public void setEvent(Event event) {
    	this.event = event;
    }

    public long getSeat() {
        return seat;
    }
    
    public void setSeat(long seat) {
		this.seat = seat;
	}

	public double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	
    public Auditorium getAuditorium() {
		return auditorium;
	}

	public void setAuditorium(Auditorium auditorium) {
		this.auditorium = auditorium;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getId(), event, auditorium, date, seat);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		if (auditorium == null) {
			if (other.auditorium != null)
				return false;
		} else if (!auditorium.equals(other.auditorium))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (seat != other.seat)
			return false;
		return true;
	}

	@Override
	public int compareTo(Ticket o) {			  
		int result = this.date.compareTo(o.getDate());
		if (result == 0) {
		        result = new Long(seat).compareTo(o.getSeat());
		}
		return result;
	}

}
