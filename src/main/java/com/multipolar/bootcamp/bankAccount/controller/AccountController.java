package com.multipolar.bootcamp.bankAccount.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multipolar.bootcamp.bankAccount.domain.BankAccount;
import com.multipolar.bootcamp.bankAccount.dto.ErrorMessage;
import com.multipolar.bootcamp.bankAccount.service.AccountService;

@RestController
@RequestMapping("/bankAccount")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody BankAccount bankAccount,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ErrorMessage> validationErrors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                ErrorMessage errorMessage = new ErrorMessage();
                errorMessage.setCode("VALIDATION_ERROR");
                errorMessage.setMessage(error.getDefaultMessage());
                validationErrors.add(errorMessage);
            }
            return ResponseEntity.badRequest().body(validationErrors);
        }
        BankAccount createAccount = accountService.createOrUpdate(bankAccount);
        return ResponseEntity.ok(createAccount);
    }

    @PutMapping("/{id}")
    public BankAccount updateAccount(@PathVariable String id, @RequestBody BankAccount bankAccount) {
        bankAccount.setId(id);
        return accountService.createOrUpdate(bankAccount);
    }

    @GetMapping
    public List<BankAccount> getAllAccount() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> getAccountById(@PathVariable String id) {
        Optional<BankAccount> bankAccount = accountService.getAccountById(id);
        return bankAccount.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/accountNumber/{accountNumber}")
    public ResponseEntity<BankAccount> getAccountByNumber(@PathVariable String accountNumber) {
        Optional<List<BankAccount>> bankAccount = (accountService.getAccountByNumber(accountNumber));
        return bankAccount.map(accounts -> ResponseEntity.ok(accounts.get(0)))
                .orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountById(@PathVariable String id) {
        accountService.deleteAccountById(id);
        return ResponseEntity.noContent().build();
    }
}
