package com.epam.spring.core.domain;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.epam.spring.core.domain.ticket.Ticket;

/**
 * @author alehstruneuski
 */
@Entity
@Table(name = "auditorium")
public class Auditorium extends DomainObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1927343050850714131L;
	
	@Column(name = "name")
	private String name;
	@Column(name = "number_of_seats")
    private long numberOfSeats;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "auditorium")
	private Set<Ticket> tickets;
    @ElementCollection
    @CollectionTable(name = "vip_seats", joinColumns = @JoinColumn(name = "auditorium_id"))
    @Column(name="vip_seats")
    private Set<Long> vipSeats;
    
    public Auditorium() {
    }

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
    
    public void addTicket(Ticket ticket) {
    	tickets.add(ticket);
    }
    
    public void remove(Ticket ticket) {
    	tickets.remove(ticket);
    }
    
    public Set<Ticket> getTickets() {
    	return tickets;
    }

    /**
     * Counts how many vip seats are there in supplied <code>seats</code>
     * 
     * @param seats
     *            Seats to process
     * @return number of vip seats in request
     */
    public long countVipSeats(Collection<Long> seats) {
        return seats.stream().filter(seat -> vipSeats.contains(seat)).count();
    }
    
    /**
     * Checks whether passed id of seat is VIP
     * @param id - id of seat
     * @return whether id is VIP seat or not
     */
    public boolean isSeatVip(Long id) {
    	return vipSeats.contains(id);
    }

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
    
    public Set<Long> getAllSeats() {
        return LongStream.range(1, numberOfSeats + 1).boxed().collect(Collectors.toSet());
    }

    public Set<Long> getVipSeats() {
        return vipSeats;
    }

    public void setVipSeats(Set<Long> vipSeats) {
        this.vipSeats = vipSeats;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Auditorium other = (Auditorium) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
