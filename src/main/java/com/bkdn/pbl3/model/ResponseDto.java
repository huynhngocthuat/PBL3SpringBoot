package com.bkdn.pbl3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private long responseId;
    @NotEmpty
    private String message;
    private int responseType;
    private Date responsedDate;
    private long reportId;
    private int accountId;

}
