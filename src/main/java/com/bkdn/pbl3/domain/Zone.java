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
@Table(name = "Zone")
public class Zone implements Serializable {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String zoneId;
    @Column(nullable = false)
    private String zoneName;

    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL)
    private Set<Room> rooms;
}
