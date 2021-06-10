package com.bkdn.pbl3.service;

import com.bkdn.pbl3.domain.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ZoneService {

    List<Zone> findAll();

    void delete(Zone zone);

    Optional<Zone> findById(String s);

    void deleteById(String s);

    List<Zone> findByZoneNameContaining(String name);

    Page<Zone> findByZoneNameContaining(String name, Pageable pageable);

    <S extends Zone> S save(S s);
}
