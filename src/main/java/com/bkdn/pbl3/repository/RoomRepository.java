package com.bkdn.pbl3.repository;

import com.bkdn.pbl3.domain.Room;
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
public interface RoomRepository extends JpaRepository<Room, String> {
    List<Room> findRoomByZone(Zone zone);
    Page<Room> findByRoomIdContaining(String roomId, Pageable pageable);

    @Modifying()
    @Query(value = "UPDATE room SET room_function = ?1, zone_id = ?2 WHERE room_id = ?3", nativeQuery = true)
    @Transactional
    int updateRoom(String roomFunction, String zoneId, String roomId);
}
