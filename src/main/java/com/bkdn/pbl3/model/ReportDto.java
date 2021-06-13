package com.bkdn.pbl3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {
    private long reportId;
    private String image;
    private MultipartFile imageFile;
    private String note;
    private int reportStatus;
    private Date reportedDate;
    private Boolean isEdit;
    private long accountId;
    private String equipmentId;
    private String statusId;
}
