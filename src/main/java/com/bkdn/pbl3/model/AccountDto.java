package com.bkdn.pbl3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private long accountId;
    @NotEmpty
    @Length(min = 6)
    private String userName;
    @NotEmpty
    @Length(min = 6)
    private String passWord;
    private int role;
    @NotEmpty
    private String fullName;
    @NotEmpty
    private String classs;
    @NotEmpty
    private String faculty;
    private Boolean isEdit = false;
}
