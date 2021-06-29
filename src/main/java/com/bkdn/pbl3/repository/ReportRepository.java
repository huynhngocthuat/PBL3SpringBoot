package com.bkdn.pbl3.repository;

import com.bkdn.pbl3.domain.Account;
import com.bkdn.pbl3.domain.Equipment;
import com.bkdn.pbl3.domain.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findReportByEquipment(Equipment equipment);
    List<Report> findReportByAccount(Account account);

    @Transactional
    @Query(value = "UPDATE report SET image = ?1, note = ?2, reported_date = ?3, " +
            "equipment_id = ?4, status_id = ?5 WHERE report_id = ?6", nativeQuery = true)
    @Modifying
    int updateReport(String image, String note, Date reportedDate, String equipmentId, String statusId, long reportId);

    @Query(nativeQuery = true, value = " SELECT REPORT.report_id, report.account_id, room_id, MAX(RESPONSE.response_type) as 'response_type', responsed_date, MIN(CAST(message AS NVARCHAR(100))) as message, equipment_name, equipment_status, CAST(note AS NVARCHAR(100))[note], reported_date, is_edit " +
            "FROM REPORT " +
            "LEFT JOIN RESPONSE ON RESPONSE.report_id = REPORT.report_id " +
            "INNER JOIN EQUIPMENT ON EQUIPMENT.equipment_id = REPORT.equipment_id " +
            "INNER JOIN STATUS ON STATUS.status_id = REPORT.status_id " +
            "GROUP BY REPORT.report_id, report.account_id, room_id, responsed_date, equipment_name, equipment_status, CAST(note AS NVARCHAR(100)) , reported_date, is_edit")
    List<Object> getReportShow();
}
