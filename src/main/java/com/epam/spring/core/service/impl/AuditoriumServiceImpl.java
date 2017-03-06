package com.epam.spring.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.spring.core.dao.repository.AuditoriumRepository;
import com.epam.spring.core.domain.Auditorium;
import com.epam.spring.core.service.IAuditoriumService;

/**
 * @author alehstruneuski
 */
@Service
public class AuditoriumServiceImpl implements IAuditoriumService {

	@Autowired
	private AuditoriumRepository auditoriuRepository;

	@Override
	public Collection<Auditorium> getAll() {
		List<Auditorium> targetCollection = new ArrayList<Auditorium>();
		Iterable<Auditorium> eventIterator = auditoriuRepository.findAll();
		CollectionUtils.addAll(targetCollection, eventIterator.iterator());
		return targetCollection;
	}

	@Override
	public Auditorium getByName(String name) {
		return auditoriuRepository.findByName(name);
	}

	@Override
	public Auditorium getById(Long id) {
		return auditoriuRepository.findById(id);
	}

	@Override
	public Auditorium save(Auditorium auditorium) {
		return auditoriuRepository.save(auditorium);
	}

	@Override
	public void remove(Auditorium auditorium) {
		auditoriuRepository.delete(auditorium.getId());
	}
	
}
