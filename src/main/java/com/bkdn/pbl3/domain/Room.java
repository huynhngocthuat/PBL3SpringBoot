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
@Table(name = "Room")
public class Room implements Serializable{
    @Id
    private String roomId;
    @Column(columnDefinition = "nvarchar(200) not null")
    private String roomFunction;

    @ManyToOne
    @JoinColumn(name = "zoneId")
    private Zone zone;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<Equipment> equipments;
}
