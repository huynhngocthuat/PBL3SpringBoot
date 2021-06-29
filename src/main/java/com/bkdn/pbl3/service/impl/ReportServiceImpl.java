package com.bkdn.pbl3.service.impl;

import com.bkdn.pbl3.domain.Account;
import com.bkdn.pbl3.domain.Equipment;
import com.bkdn.pbl3.domain.Report;
import com.bkdn.pbl3.model.ReportShow;
import com.bkdn.pbl3.repository.ReportRepository;
import com.bkdn.pbl3.service.ReportService;
import com.bkdn.pbl3.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public Page<Report> findAll(Pageable pageable) {
        return reportRepository.findAll(pageable);
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

    @Override
    @Query(nativeQuery = true, value = " SELECT REPORT.report_id, report.account_id, room_id, MAX(RESPONSE.response_type) as 'response_type', responsed_date, MIN(CAST(message AS NVARCHAR(100))) as message, equipment_name, equipment_status, CAST(note AS NVARCHAR(100))[note], reported_date, is_edit " +
            "FROM REPORT " +
            "LEFT JOIN RESPONSE ON RESPONSE.report_id = REPORT.report_id " +
            "INNER JOIN EQUIPMENT ON EQUIPMENT.equipment_id = REPORT.equipment_id " +
            "INNER JOIN STATUS ON STATUS.status_id = REPORT.status_id " +
            "GROUP BY REPORT.report_id, report.account_id, room_id, responsed_date, equipment_name, equipment_status, CAST(note AS NVARCHAR(100)) , reported_date, is_edit")
    public List<ReportShow> getReportShow() {
            List<ReportShow> listShow = new ArrayList<>();
        for(Object obj : reportRepository.getReportShow()){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Object[] rowArray = (Object[]) obj;
        ReportShow rp = new ReportShow();
        rp.setReportId(Integer.parseInt(String.valueOf(rowArray[0])));
        rp.setAccountId(Integer.parseInt(String.valueOf(rowArray[1])));
        rp.setRoomID(String.valueOf(rowArray[2]));
        if(rowArray[3]!=null){
            rp.setResponseType(Integer.parseInt(String.valueOf(rowArray[3])));
            rp.setResponsedDate(String.valueOf(rowArray[4]));
            rp.setResponseMessage(String.valueOf(rowArray[5]));
        }
        rp.setEquipmentName(String.valueOf(rowArray[6]));
        rp.setEquipmentStatus(String.valueOf(rowArray[7]));
        rp.setReportMessage(String.valueOf(rowArray[8]));
        rp.setReportedDate(String.valueOf(rowArray[9]));
        rp.setIsEdit(Boolean.parseBoolean(String.valueOf(rowArray[10])));
        listShow.add(rp);
    }
        return listShow;
    }

    @Override
    @Modifying
    @Query(value = "UPDATE report SET image = ?1, note = ?2, reported_date = ?3, " +
            "equipment_id = ?4, status_id = ?5 WHERE report_id = ?6", nativeQuery = true)
    @Transactional
    public int updateReport(String image, String note, Date reportedDate, String equipmentId, String statusId, long reportId) {
        return reportRepository.updateReport(image, note, reportedDate, equipmentId, statusId, reportId);
    }
}
