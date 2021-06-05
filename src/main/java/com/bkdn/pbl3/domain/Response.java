package com.bkdn.pbl3.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private int responseId;
    private int accountId;
    private int reportId;
    private String message;
    private int responseType;
    private Date responsedDate;
}
