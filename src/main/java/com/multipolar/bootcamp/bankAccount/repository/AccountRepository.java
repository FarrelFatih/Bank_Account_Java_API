package com.multipolar.bootcamp.bankAccount.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.multipolar.bootcamp.bankAccount.domain.BankAccount;

public interface AccountRepository extends MongoRepository<BankAccount, String> {
    // Optional<BankAccount> findByAccountNumber(String accountNumber);

    @Query("{'accountNumber': {$regex: ?0, $options: 'i'}}")
    Optional<List<BankAccount>> findByNumberCaseInsensitive(String accountNumber);
}
