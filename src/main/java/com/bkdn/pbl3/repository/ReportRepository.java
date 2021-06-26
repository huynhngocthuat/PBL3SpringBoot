package com.bkdn.pbl3.repository;

import com.bkdn.pbl3.domain.Account;
import com.bkdn.pbl3.domain.Equipment;
import com.bkdn.pbl3.domain.Report;
import com.bkdn.pbl3.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findReportByEquipment(Equipment equipment);
    List<Report> findReportByAccount(Account account);


    @Query(nativeQuery = true, value = " SELECT REPORT.report_id, report.account_id, room_id, MAX(RESPONSE.response_type) as 'response_type', responsed_date, MIN(CAST(message AS NVARCHAR(100))) as message, equipment_name, equipment_status, CAST(note AS NVARCHAR(100))[note], reported_date, is_edit " +
            "FROM REPORT " +
            "LEFT JOIN RESPONSE ON RESPONSE.report_id = REPORT.report_id " +
            "INNER JOIN EQUIPMENT ON EQUIPMENT.equipment_id = REPORT.equipment_id " +
            "INNER JOIN STATUS ON STATUS.status_id = REPORT.status_id " +
            "GROUP BY REPORT.report_id, report.account_id, room_id, responsed_date, equipment_name, equipment_status, CAST(note AS NVARCHAR(100)) , reported_date, is_edit")
    List<Object> getReportShow();
}
