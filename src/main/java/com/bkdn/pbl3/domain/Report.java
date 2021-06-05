package com.bkdn.pbl3.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private int reportId;
    private int accountId;
    private String roomId;
    private String equipmentId;
    private String statusId;
    private String note;
    private int reportStatus;
    private Date reportedDate;
    private Boolean isEdit;
}
