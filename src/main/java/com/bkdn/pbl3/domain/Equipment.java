package com.bkdn.pbl3.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipment {
    private String equipmentId;
    private String roomId;
    private String equipmentName;
    private Date dateOfInstallation;
    private String company;
}
