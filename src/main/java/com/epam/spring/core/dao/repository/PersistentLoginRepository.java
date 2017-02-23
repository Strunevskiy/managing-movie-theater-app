package com.epam.spring.core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.spring.core.dao.repository.custom.PersistentLoginRepositoryCustom;
import com.epam.spring.core.domain.PersistentLogin;

@Repository
public interface PersistentLoginRepository extends JpaRepository<PersistentLogin,Long>, PersistentLoginRepositoryCustom {
	
}
