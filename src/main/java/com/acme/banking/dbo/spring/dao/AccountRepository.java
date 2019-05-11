package com.acme.banking.dbo.spring.dao;

import com.acme.banking.dbo.spring.domain.Account;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** In production will be used code-generated implementation by Spring-data-jpa module */
@Repository
@Profile("integration-test")
public interface AccountRepository extends JpaRepository<Account, Long> {
    /** TODO Intro to Optional class */
}
