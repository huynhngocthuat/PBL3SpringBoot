package com.bkdn.pbl3.service.impl;

import com.bkdn.pbl3.domain.Account;
import com.bkdn.pbl3.repository.AccountRepository;
import com.bkdn.pbl3.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceIml implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public <S extends Account> S save(S s) {
        s.setPassWord(bCryptPasswordEncoder.encode(s.getPassWord()));
        return accountRepository.save(s);
    }

    @Override
    public Account findByUserName(String username) {
        return accountRepository.findByUserName(username);
    }

    @Override
    public Optional<Account> findById(Long aLong) {
        return accountRepository.findById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        accountRepository.deleteById(aLong);
    }
}
