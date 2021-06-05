package com.bkdn.pbl3.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private int accountId;
    private String userName;
    private String passWord;
    private int role;
    private String fullName;
    private String _class;
    private String faculty;
}
