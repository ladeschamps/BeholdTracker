package com.ladeschamps.beholdertracker.adapter.repository;

import com.ladeschamps.beholdertracker.domain.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Adapter - AccountMapper Unit Tests")
class AccountMapperTest {

    @Test
    @DisplayName("Should map AccountDatabaseEntity to pure domain Account")
    void shouldMapEntityToDomain() {
        LocalDateTime now = LocalDateTime.now();
        AccountDatabaseEntity entity = new AccountDatabaseEntity(
                10L,
                "XP Investimentos",
                new BigDecimal("5000.00"),
                new BigDecimal("6200.50"),
                now
        );

        Account domain = AccountMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(10L, domain.getId());
        assertEquals("XP Investimentos", domain.getName());
        assertEquals(new BigDecimal("5000.00"), domain.getInitialBalance());
        assertEquals(new BigDecimal("6200.50"), domain.getCurrentBalance());
        assertEquals(now, domain.getCreatedAt());
    }

    @Test
    @DisplayName("Should map pure domain Account to AccountDatabaseEntity")
    void shouldMapDomainToEntity() {
        LocalDateTime now = LocalDateTime.now();
        Account domain = new Account(
                20L,
                "Clear Corretora",
                new BigDecimal("1000.00"),
                new BigDecimal("850.00"),
                now
        );

        AccountDatabaseEntity entity = AccountMapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals(20L, entity.getId());
        assertEquals("Clear Corretora", entity.getName());
        assertEquals(new BigDecimal("1000.00"), entity.getInitialBalance());
        assertEquals(new BigDecimal("850.00"), entity.getCurrentBalance());
        assertEquals(now, entity.getCreatedAt());
    }

    @Test
    @DisplayName("Should return null when mapping null inputs")
    void shouldHandleNullInputs() {
        assertNull(AccountMapper.toDomain(null));
        assertNull(AccountMapper.toEntity(null));
    }
}
