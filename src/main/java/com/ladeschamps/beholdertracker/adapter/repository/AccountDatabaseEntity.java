package com.ladeschamps.beholdertracker.adapter.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * JPA Entity representing the persistence model for accounts stored in PostgreSQL.
 */
@Entity
@Table(name = "accounts")
@SuppressWarnings("JpaDataSourceORMInspection")
public class AccountDatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "initial_balance", nullable = false, precision = 19, scale = 4)
    private BigDecimal initialBalance;

    @Column(name = "current_balance", nullable = false, precision = 19, scale = 4)
    private BigDecimal currentBalance;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public AccountDatabaseEntity() {
    }

    public AccountDatabaseEntity(Long id, String name, BigDecimal initialBalance, BigDecimal currentBalance, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.initialBalance = initialBalance;
        this.currentBalance = currentBalance;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
