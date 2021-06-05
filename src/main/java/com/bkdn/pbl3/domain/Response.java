package com.bkdn.pbl3.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Response implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int responseId;
   // @Column(nullable = false)
   // private int accountId;
   // @Column(nullable = false)
   // private int reportId;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private int responseType;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date responsedDate;

    @ManyToOne
    @JoinColumn(name = "reportId")
    private Report report;
    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;
}
