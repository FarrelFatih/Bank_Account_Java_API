package com.multipolar.bootcamp.bankAccount.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multipolar.bootcamp.bankAccount.domain.BankAccount;
import com.multipolar.bootcamp.bankAccount.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<BankAccount> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<BankAccount> getAccountById(String id) {
        return accountRepository.findById(id);
    }

    public Optional<List<BankAccount>> getAccountByNumber(String accountNumber) {
        return accountRepository.findByNumberCaseInsensitive(accountNumber);
    }

    public BankAccount createOrUpdate(BankAccount bankAccount) {
        return accountRepository.save(bankAccount);
    }

    public void deleteAccountById(String id) {
        accountRepository.deleteById(id);
    }
}
