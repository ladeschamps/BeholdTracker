package com.ladeschamps.beholdtracker.adapter.controller;

import com.ladeschamps.beholdtracker.adapter.controller.dto.AccountResponse;
import com.ladeschamps.beholdtracker.adapter.controller.dto.CashMovementRequest;
import com.ladeschamps.beholdtracker.adapter.controller.dto.CreateAccountRequest;
import com.ladeschamps.beholdtracker.domain.Account;
import com.ladeschamps.beholdtracker.domain.exception.AccountNotFoundException;
import com.ladeschamps.beholdtracker.usecase.port.AccountRepositoryPort;
import com.ladeschamps.beholdtracker.usecase.port.ExecuteDepositUseCase;
import com.ladeschamps.beholdtracker.usecase.port.ExecuteWithdrawalUseCase;
import com.ladeschamps.beholdtracker.usecase.port.RegisterAccountUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * Inbound REST adapter exposing HTTP endpoints for Account and cash movement operations.
 */
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final RegisterAccountUseCase registerAccountUseCase;
    private final ExecuteDepositUseCase executeDepositUseCase;
    private final ExecuteWithdrawalUseCase executeWithdrawalUseCase;
    private final AccountRepositoryPort accountRepositoryPort;

    public AccountController(
            RegisterAccountUseCase registerAccountUseCase,
            ExecuteDepositUseCase executeDepositUseCase,
            ExecuteWithdrawalUseCase executeWithdrawalUseCase,
            AccountRepositoryPort accountRepositoryPort
    ) {
        this.registerAccountUseCase = Objects.requireNonNull(registerAccountUseCase, "registerAccountUseCase must not be null");
        this.executeDepositUseCase = Objects.requireNonNull(executeDepositUseCase, "executeDepositUseCase must not be null");
        this.executeWithdrawalUseCase = Objects.requireNonNull(executeWithdrawalUseCase, "executeWithdrawalUseCase must not be null");
        this.accountRepositoryPort = Objects.requireNonNull(accountRepositoryPort, "accountRepositoryPort must not be null");
    }

    /**
     * Creates a new brokerage or cash account.
     * POST /api/v1/accounts
     */
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        Account createdAccount = registerAccountUseCase.register(request.name(), request.initialBalance());
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountResponse.fromDomain(createdAccount));
    }

    /**
     * Executes a cash deposit into the specified account.
     * POST /api/v1/accounts/{id}/deposit
     */
    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountResponse> deposit(
            @PathVariable("id") Long id,
            @Valid @RequestBody CashMovementRequest request
    ) {
        Account updatedAccount = executeDepositUseCase.execute(id, request.amount());
        return ResponseEntity.ok(AccountResponse.fromDomain(updatedAccount));
    }

    /**
     * Executes a cash withdrawal from the specified account.
     * POST /api/v1/accounts/{id}/withdraw
     */
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(
            @PathVariable("id") Long id,
            @Valid @RequestBody CashMovementRequest request
    ) {
        Account updatedAccount = executeWithdrawalUseCase.execute(id, request.amount());
        return ResponseEntity.ok(AccountResponse.fromDomain(updatedAccount));
    }

    /**
     * Retrieves an account by its ID.
     * GET /api/v1/accounts/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable("id") Long id) {
        Account account = accountRepositoryPort.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        return ResponseEntity.ok(AccountResponse.fromDomain(account));
    }

    /**
     * Retrieves all accounts.
     * GET /api/v1/accounts
     */
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        List<AccountResponse> accounts = accountRepositoryPort.findAll().stream()
                .map(AccountResponse::fromDomain)
                .toList();
        return ResponseEntity.ok(accounts);
    }
}
