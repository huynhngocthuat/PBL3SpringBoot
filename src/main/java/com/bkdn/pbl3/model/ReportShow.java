package com.bkdn.pbl3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportShow {
    private int reportId;
    private int accountId;
    private String roomID;
    private String responsedDate;
    private int responseType;
    private String responseMessage;
    private String equipmentName;
    private String equipmentStatus;
    private String reportMessage;
    private String reportedDate;
    private Boolean isEdit;
}
