package com.bkdn.pbl3.model;

import com.bkdn.pbl3.domain.Equipment;
import com.bkdn.pbl3.domain.Report;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto {
    @NotEmpty
    private String statusId;
    @NotEmpty
    private String equipmentStatus;
    @NotEmpty
    private String equipmentId;
    private boolean isEdit;
}
