package com.bkdn.pbl3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    @NotEmpty
    private String roomId;
    @NotEmpty
    private String roomFunction;
    @NotEmpty
    private String zoneId;
    private Boolean isEdit = false;
}
