package com.ladeschamps.beholdtracker.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountDatabaseEntity, Long> {
    @SuppressWarnings("unused")
    Optional<AccountDatabaseEntity> findByName(String name);
}
