package com.bkdn.pbl3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZoneDto implements Serializable{
    @NotEmpty
    private String zoneId;
    @NotEmpty
    private String zoneName;
    private Boolean isEdit = false;
}
