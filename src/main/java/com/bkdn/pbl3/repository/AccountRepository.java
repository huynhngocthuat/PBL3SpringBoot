package com.bkdn.pbl3.repository;

import com.bkdn.pbl3.domain.Account;
import com.bkdn.pbl3.domain.Equipment;
import com.bkdn.pbl3.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUserName(String username);
    Page<Account> findByFullNameContaining(String fullName, Pageable pageable);
    @Modifying
    @Query(value = "UPDATE account SET classs= ?1, faculty = ?2, " +
            " full_name = ?3 WHERE account_id = ?4", nativeQuery = true)
    @Transactional
    int updateAccount(String classs, String faculty, String fullName, long accountId);
}
