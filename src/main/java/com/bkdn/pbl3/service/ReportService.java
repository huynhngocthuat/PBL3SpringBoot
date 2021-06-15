package com.bkdn.pbl3.service;

import com.bkdn.pbl3.domain.Report;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    List<Report> findAll();

    <S extends Report> S save(S s);

    Optional<Report> findById(Long aLong);

    void deleteById(Long aLong);
}
