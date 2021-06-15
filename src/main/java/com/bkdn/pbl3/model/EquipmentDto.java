package com.bkdn.pbl3.model;

import com.bkdn.pbl3.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDto {
    @NotEmpty
    private String equipmentId;
    @NotEmpty
    private String equipmentName;
//    @NotEmpty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfInstallation;
    @NotEmpty
    private String company;
    private String roomId;
    private Boolean isEdit = false;
}
