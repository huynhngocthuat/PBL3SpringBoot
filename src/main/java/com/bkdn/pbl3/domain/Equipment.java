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
@Table(name = "Equipment")
public class Equipment implements Serializable {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private String equipmentId;
    @Column(columnDefinition = "nvarchar(100) not null")
    private String equipmentName;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateOfInstallation;
    @Column(columnDefinition = "nvarchar(100) not null")
    private String company;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room room;

    @OneToMany(mappedBy = "equipment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Status> statuses;
    @OneToMany(mappedBy = "equipment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Report> reports;
}
