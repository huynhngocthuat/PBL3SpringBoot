package com.bkdn.pbl3.service;

import com.bkdn.pbl3.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account login(String username, String password);

    List<Account> findAll();

    Page<Account> findAll(Pageable pageable);

    <S extends Account> S save(S s);

    Account findByUserName(String username);

    Optional<Account> findById(Long aLong);

    Account getById(Long aLong);

    void deleteById(Long aLong);

    Page<Account> findByFullNameContaining(String fullName, Pageable pageable);
}
