package com.epam.spring.core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.spring.core.domain.Auditorium;

/**
 * @author alehstruneuski
 */
@Repository
public interface AuditoriumRepository  extends JpaRepository<Auditorium, Long> {

	public Auditorium findByName(String name);
	public Auditorium findById(Long id);

}
