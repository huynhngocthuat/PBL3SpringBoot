package com.bkdn.pbl3.service.impl;

import com.bkdn.pbl3.domain.Account;
import com.bkdn.pbl3.repository.AccountRepository;
import com.bkdn.pbl3.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceIml implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Account login(String username, String password){
        Account optExist = findByUserName(username);
        if(optExist != null && bCryptPasswordEncoder.matches(password, optExist.getPassWord())){
            optExist.setPassWord("");
            return optExist;
        }
        return null;
    }
    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public <S extends Account> S save(S s) {
        s.setPassWord(bCryptPasswordEncoder.encode(s.getPassWord()));
        return accountRepository.save(s);
    }

    @Override
    @Transactional
    @Query(value = "UPDATE account SET classs= ?1, faculty = ?2, " +
            " full_name = ?3 WHERE account_id = ?4", nativeQuery = true)
    @Modifying
    public int updateAccount(String classs, String faculty, String fullName, long accountId) {
        return accountRepository.updateAccount(classs, faculty, fullName, accountId);
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
    public Account getById(Long aLong) {
        return accountRepository.getById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        accountRepository.deleteById(aLong);
    }

    @Override
    public Page<Account> findByFullNameContaining(String fullName, Pageable pageable) {
        return accountRepository.findByFullNameContaining(fullName, pageable);
    }

    @Override
    @Transactional
    @Query(value = "UPDATE account SET pass_word = ?1 WHERE account_id = ?2", nativeQuery = true)
    @Modifying
    public int updatePassword(String oldPassword, long accountId) {
        return accountRepository.updatePassword(oldPassword, accountId);
    }

}
