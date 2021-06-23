package com.bkdn.pbl3.service.impl;

import com.bkdn.pbl3.domain.Account;
import com.bkdn.pbl3.domain.Equipment;
import com.bkdn.pbl3.domain.Report;
import com.bkdn.pbl3.domain.Status;
import com.bkdn.pbl3.repository.ReportRepository;
import com.bkdn.pbl3.service.ReportService;
import com.bkdn.pbl3.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ResponseService responseService;

    @Override
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public <S extends Report> S save(S s) {
        return reportRepository.save(s);
    }

    @Override
    public List<Report> findReportByEquipment(Equipment equipment) {
        return reportRepository.findReportByEquipment(equipment);
    }

    @Override
    public Report getById(Long aLong) {
        return reportRepository.getById(aLong);
    }

    @Override
    public List<Report> findReportByAccount(Account account) {
        return reportRepository.findReportByAccount(account);
    }

    @Override
    public Optional<Report> findById(Long aLong) {
        return reportRepository.findById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        reportRepository.deleteById(aLong);
    }

    @Override
    public  void deleteReportByEquipment(Equipment equipment){
        List<Report> list = this.findReportByEquipment(equipment);
        for(Report report : list){
            responseService.deleteResponseByReport(report);
            this.deleteById(report.getReportId());
        }
    }
}
