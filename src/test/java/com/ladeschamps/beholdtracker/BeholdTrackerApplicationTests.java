package com.ladeschamps.beholdtracker;

import com.ladeschamps.beholdtracker.adapter.repository.AccountJpaRepository;
import com.ladeschamps.beholdtracker.usecase.port.AccountRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@SuppressWarnings("unused")
class BeholdTrackerApplicationTests {

    @MockBean
    private AccountJpaRepository accountJpaRepository;

    @MockBean
    private AccountRepositoryPort accountRepositoryPort;

    @Test
    @DisplayName("Context Loads - Spring Application Context Initializes Cleanly")
    void contextLoads() {
    }
}
