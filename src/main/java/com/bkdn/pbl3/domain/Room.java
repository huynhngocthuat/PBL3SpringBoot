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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String roomId;
    //@Column(nullable = false)
    //private String zoneId;
    @Column(nullable = false)
    private String roomFunction;

    @ManyToOne
    @JoinColumn(name = "zoneId")
    private Zone zone;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<Equipment> equipments;
}
