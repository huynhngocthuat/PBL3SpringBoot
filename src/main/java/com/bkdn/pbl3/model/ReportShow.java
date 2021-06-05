package com.bkdn.pbl3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportShow {
    private int reportId;
    private int accountId;
    private String roomID;
    private Date responsedDate;
    private int responseType;
    private String responseMessage;
    private String equipmentName;
    private String equipmentStatus;
    private String reportMessage;
    private Date reportedDate;
    private Boolean isEdit;
}
