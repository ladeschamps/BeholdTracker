package com.ladeschamps.beholdertracker.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ladeschamps.beholdertracker.adapter.controller.dto.CashMovementRequest;
import com.ladeschamps.beholdertracker.adapter.controller.dto.CreateAccountRequest;
import com.ladeschamps.beholdertracker.domain.Account;
import com.ladeschamps.beholdertracker.domain.exception.AccountNotFoundException;
import com.ladeschamps.beholdertracker.domain.exception.InsufficientBalanceException;
import com.ladeschamps.beholdertracker.usecase.port.AccountRepositoryPort;
import com.ladeschamps.beholdertracker.usecase.port.ExecuteDepositUseCase;
import com.ladeschamps.beholdertracker.usecase.port.ExecuteWithdrawalUseCase;
import com.ladeschamps.beholdertracker.usecase.port.RegisterAccountUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@DisplayName("Adapter - AccountController WebMvc Tests")
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegisterAccountUseCase registerAccountUseCase;

    @MockBean
    private ExecuteDepositUseCase executeDepositUseCase;

    @MockBean
    private ExecuteWithdrawalUseCase executeWithdrawalUseCase;

    @MockBean
    private AccountRepositoryPort accountRepositoryPort;

    @Test
    @DisplayName("POST /api/v1/accounts - Should return 201 Created on valid account registration")
    void shouldCreateAccountSuccessfully() throws Exception {
        CreateAccountRequest request = new CreateAccountRequest("NuInvest", new BigDecimal("1000.00"));
        Account createdAccount = new Account(1L, "NuInvest", new BigDecimal("1000.00"), new BigDecimal("1000.00"), LocalDateTime.now());

        when(registerAccountUseCase.register(eq("NuInvest"), eq(new BigDecimal("1000.00")))).thenReturn(createdAccount);

        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("NuInvest"))
                .andExpect(jsonPath("$.initialBalance").value(1000.00))
                .andExpect(jsonPath("$.currentBalance").value(1000.00));
    }

    @Test
    @DisplayName("POST /api/v1/accounts - Should return 400 Bad Request when request body is invalid")
    void shouldReturnBadRequestOnInvalidAccountCreation() throws Exception {
        CreateAccountRequest invalidRequest = new CreateAccountRequest("", new BigDecimal("-10.00"));

        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/v1/accounts/{id}/deposit - Should return 200 OK on successful deposit")
    void shouldDepositSuccessfully() throws Exception {
        Long accountId = 1L;
        CashMovementRequest request = new CashMovementRequest(new BigDecimal("500.00"));
        Account updatedAccount = new Account(accountId, "NuInvest", new BigDecimal("1000.00"), new BigDecimal("1500.00"), LocalDateTime.now());

        when(executeDepositUseCase.execute(eq(accountId), eq(new BigDecimal("500.00")))).thenReturn(updatedAccount);

        mockMvc.perform(post("/api/v1/accounts/{id}/deposit", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.currentBalance").value(1500.00));
    }

    @Test
    @DisplayName("POST /api/v1/accounts/{id}/withdraw - Should return 200 OK on successful withdrawal")
    void shouldWithdrawSuccessfully() throws Exception {
        Long accountId = 1L;
        CashMovementRequest request = new CashMovementRequest(new BigDecimal("300.00"));
        Account updatedAccount = new Account(accountId, "NuInvest", new BigDecimal("1000.00"), new BigDecimal("700.00"), LocalDateTime.now());

        when(executeWithdrawalUseCase.execute(eq(accountId), eq(new BigDecimal("300.00")))).thenReturn(updatedAccount);

        mockMvc.perform(post("/api/v1/accounts/{id}/withdraw", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.currentBalance").value(700.00));
    }

    @Test
    @DisplayName("POST /api/v1/accounts/{id}/withdraw - Should return 422 Unprocessable Entity when balance is insufficient")
    void shouldReturnUnprocessableEntityWhenBalanceInsufficient() throws Exception {
        Long accountId = 1L;
        BigDecimal excessiveAmount = new BigDecimal("5000.00");
        CashMovementRequest request = new CashMovementRequest(excessiveAmount);

        when(executeWithdrawalUseCase.execute(eq(accountId), eq(excessiveAmount)))
                .thenThrow(new InsufficientBalanceException(accountId, new BigDecimal("1000.00"), excessiveAmount));

        mockMvc.perform(post("/api/v1/accounts/{id}/withdraw", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("POST /api/v1/accounts/{id}/withdraw - Should return 404 Not Found when account does not exist")
    void shouldReturnNotFoundWhenAccountDoesNotExist() throws Exception {
        Long accountId = 999L;
        CashMovementRequest request = new CashMovementRequest(new BigDecimal("100.00"));

        when(executeWithdrawalUseCase.execute(eq(accountId), eq(new BigDecimal("100.00"))))
                .thenThrow(new AccountNotFoundException(accountId));

        mockMvc.perform(post("/api/v1/accounts/{id}/withdraw", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("GET /api/v1/accounts/{id} - Should return 200 OK with account details")
    void shouldGetAccountById() throws Exception {
        Long accountId = 1L;
        Account account = new Account(accountId, "NuInvest", new BigDecimal("1000.00"), new BigDecimal("1000.00"), LocalDateTime.now());

        when(accountRepositoryPort.findById(accountId)).thenReturn(Optional.of(account));

        mockMvc.perform(get("/api/v1/accounts/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("NuInvest"));
    }
}
