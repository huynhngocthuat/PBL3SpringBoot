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
@Table(name = "Status")
public class Status implements Serializable {
    @Id
    private String statusId;
    @Column(columnDefinition = "nvarchar(200) not null")
    private String equipmentStatus;

    @ManyToOne
    @JoinColumn(name = "equipmentId")
    private Equipment equipment;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    private Set<Report> reports;
}
