package com.ladeschamps.beholdertracker;

import com.ladeschamps.beholdertracker.adapter.repository.AccountJpaRepository;
import com.ladeschamps.beholdertracker.usecase.port.AccountRepositoryPort;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
class BeholderTrackerApplicationTests {

    @MockBean
    private AccountJpaRepository accountJpaRepository;

    @MockBean
    private AccountRepositoryPort accountRepositoryPort;

    @Test
    void contextLoads() {
        assertNotNull(accountJpaRepository);
        assertNotNull(accountRepositoryPort);
    }
}
