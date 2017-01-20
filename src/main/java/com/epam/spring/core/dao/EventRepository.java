package com.epam.spring.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.spring.core.domain.Event;

/**
 * @author alehstruneuski
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

	public void delete(Event event);
	public Event findByName(String name);
	
}
