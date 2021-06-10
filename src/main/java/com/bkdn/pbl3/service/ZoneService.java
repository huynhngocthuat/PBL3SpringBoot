package com.bkdn.pbl3.service;

import com.bkdn.pbl3.domain.Zone;

import java.util.List;
import java.util.Optional;

public interface ZoneService {

    List<Zone> findAll();

    Optional<Zone> findById(String s);

    void delete(Zone zone);

    <S extends Zone> S save(S s);
}
