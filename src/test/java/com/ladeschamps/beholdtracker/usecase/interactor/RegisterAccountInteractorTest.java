package com.ladeschamps.beholdtracker.usecase.interactor;

import com.ladeschamps.beholdtracker.domain.Account;
import com.ladeschamps.beholdtracker.usecase.port.AccountRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UseCase - RegisterAccountInteractor Unit Tests")
class RegisterAccountInteractorTest {

    @Mock
    private AccountRepositoryPort accountRepositoryPort;

    private RegisterAccountInteractor interactor;

    @BeforeEach
    void setUp() {
        interactor = new RegisterAccountInteractor(accountRepositoryPort);
    }

    @Test
    @DisplayName("Should successfully register new account and persist it")
    void shouldRegisterAccountSuccessfully() {
        String name = "BTG Pactual";
        BigDecimal initialBalance = new BigDecimal("10000.00");

        when(accountRepositoryPort.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account created = interactor.register(name, initialBalance);

        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(initialBalance, created.getInitialBalance());
        assertEquals(initialBalance, created.getCurrentBalance());

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepositoryPort, times(1)).save(captor.capture());
        assertEquals("BTG Pactual", captor.getValue().getName());
        assertEquals(initialBalance, captor.getValue().getInitialBalance());
    }
}
