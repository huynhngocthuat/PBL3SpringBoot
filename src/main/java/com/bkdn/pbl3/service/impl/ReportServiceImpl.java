package com.bkdn.pbl3.service.impl;

import com.bkdn.pbl3.domain.Report;
import com.bkdn.pbl3.repository.ReportRepository;
import com.bkdn.pbl3.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Override
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public <S extends Report> S save(S s) {
        return reportRepository.save(s);
    }

    @Override
    public Optional<Report> findById(Long aLong) {
        return reportRepository.findById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        reportRepository.deleteById(aLong);
    }
}
