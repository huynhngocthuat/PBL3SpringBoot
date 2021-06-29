package com.bkdn.pbl3.service;

import com.bkdn.pbl3.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account login(String username, String password);

    List<Account> findAll();

    Page<Account> findAll(Pageable pageable);

    <S extends Account> S save(S s);

    @Transactional
    @Query(value = "UPDATE account SET classs= ?1, faculty = ?2, " +
            " full_name = ?3 WHERE account_id = ?4", nativeQuery = true)
    @Modifying
    int updateAccount(String classs, String faculty, String fullName, long accountId);

    Account findByUserName(String username);

    Optional<Account> findById(Long aLong);

    Account getById(Long aLong);

    void deleteById(Long aLong);

    Page<Account> findByFullNameContaining(String fullName, Pageable pageable);
}
