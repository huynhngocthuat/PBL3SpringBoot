package com.bkdn.pbl3.service;

import com.bkdn.pbl3.domain.Account;
import com.bkdn.pbl3.domain.Report;
import com.bkdn.pbl3.domain.Response;

import java.util.List;
import java.util.Optional;

public interface ResponseService {
    List<Response> findAll();

    Response getById(Long aLong);

    List<Response> findResponsesByAccount(Account account);

    List<Response> findResponsesByReport(Report report);

    <S extends Response> S save(S s);

    Optional<Response> findById(Long aLong);

    void deleteById(Long aLong);

    void deleteResponseByReport(Report report);
}
