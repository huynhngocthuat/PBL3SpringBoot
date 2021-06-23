package com.bkdn.pbl3.repository;

import com.bkdn.pbl3.domain.Account;
import com.bkdn.pbl3.domain.Report;
import com.bkdn.pbl3.domain.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findResponsesByAccount(Account account);
    List<Response> findResponsesByReport(Report report);
}
