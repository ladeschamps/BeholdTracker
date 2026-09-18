package com.ladeschamps.beholdertracker.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository for AccountDatabaseEntity.
 */
@Repository
public interface AccountJpaRepository extends JpaRepository<AccountDatabaseEntity, Long> {
}
