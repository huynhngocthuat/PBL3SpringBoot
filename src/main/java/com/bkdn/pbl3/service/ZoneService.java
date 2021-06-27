package com.bkdn.pbl3.service;

import com.bkdn.pbl3.domain.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ZoneService {

    List<Zone> findAll();

    Page<Zone> findAll(Pageable pageable);

    void delete(Zone zone);

    Optional<Zone> findById(String s);

    void deleteById(String s);

    List<Zone> findByZoneNameContaining(String name);

    Page<Zone> findByZoneNameContaining(String name, Pageable pageable);

    @Transactional
    @Query(value = "UPDATE zone SET zone_name = ?1 WHERE zone_id = ?2", nativeQuery = true)
    @Modifying
    int updateZone(String zoneName, String zoneId);

    Zone getById(String s);

    <S extends Zone> S save(S s);
}
