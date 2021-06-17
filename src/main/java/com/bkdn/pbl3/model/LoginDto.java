package com.bkdn.pbl3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String passWord;
    private Boolean rememberMe = false;
}
