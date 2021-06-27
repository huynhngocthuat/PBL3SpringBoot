package com.bkdn.pbl3.repository;

import com.bkdn.pbl3.domain.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, String> {
    List<Zone> findByZoneNameContaining(String name);
    Page<Zone> findByZoneNameContaining(String name, Pageable pageable);
    @Modifying()
    @Query(value = "UPDATE zone SET zone_name = ?1 WHERE zone_id = ?2", nativeQuery = true)
    @Transactional
    int updateZone(String zoneName, String zoneId);
}
