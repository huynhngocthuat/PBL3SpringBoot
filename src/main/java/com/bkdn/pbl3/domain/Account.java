package com.bkdn.pbl3.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Account")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String passWord;
    @Column(nullable = false)
    private int role;
    private String fullName;
    private String _class;
    private String faculty;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Report> reports;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Response> responses;
}
