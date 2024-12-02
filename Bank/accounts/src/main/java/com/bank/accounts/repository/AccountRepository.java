package com.bank.accounts.repository;

import com.bank.accounts.model.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByCustomerID(String id);

    Optional<Account> findByAccountNumber(String accountNumber);


    @Transactional
    @Modifying
    void deleteByCustomerID(String id);
}
