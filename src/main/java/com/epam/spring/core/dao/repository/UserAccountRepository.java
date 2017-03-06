package com.epam.spring.core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.spring.core.domain.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

}
