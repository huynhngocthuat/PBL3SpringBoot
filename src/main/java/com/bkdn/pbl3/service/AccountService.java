package com.bkdn.pbl3.service;

import com.bkdn.pbl3.domain.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account login(String username, String password);

    List<Account> findAll();

    <S extends Account> S save(S s);

    Account findByUserName(String username);

    Optional<Account> findById(Long aLong);

    void deleteById(Long aLong);
}
