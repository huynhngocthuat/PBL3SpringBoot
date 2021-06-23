package com.bkdn.pbl3.service;

import com.bkdn.pbl3.domain.Account;
import com.bkdn.pbl3.domain.Equipment;
import com.bkdn.pbl3.domain.Report;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    List<Report> findAll();

    <S extends Report> S save(S s);

    List<Report> findReportByEquipment(Equipment equipment);

    Report getById(Long aLong);

    List<Report> findReportByAccount(Account account);

    Optional<Report> findById(Long aLong);

    void deleteById(Long aLong);

    void deleteReportByEquipment(Equipment equipment);
}
