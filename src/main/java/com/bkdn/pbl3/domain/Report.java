package com.bkdn.pbl3.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Report")
public class Report implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reportId;
    @Column(nullable = false, length = 200)
    private String image;
    @Column(columnDefinition = "nvarchar(200) not null")
    private String note;
    @Column(nullable = false)
    private int reportStatus;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date reportedDate;
    @Column(nullable = false)
    private Boolean isEdit;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "equipmentId")
    private Equipment equipment;
    @ManyToOne
    @JoinColumn(name = "statusId")
    private Status status;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
    private Set<Response> responses;
}
