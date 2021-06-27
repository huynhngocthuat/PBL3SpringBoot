package com.bkdn.pbl3.service;

import com.bkdn.pbl3.domain.Room;
import com.bkdn.pbl3.domain.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface RoomService {

    List<Room> findAll();

    Page<Room> findAll(Pageable pageable);

    List<Room> findRoomByZone(Zone zone);

    Room getById(String s);

    <S extends Room> S save(S s);

    Optional<Room> findById(String s);

    void deleteById(String s);

    @Transactional
    @Query(value = "UPDATE room SET room_function = ?1, zone_id = ?2 WHERE room_id = ?3", nativeQuery = true)
    @Modifying
    int updateRoom(String roomFunction, String zoneId, String roomId);

    Page<Room> findByRoomIdContaining(String roomId, Pageable pageable);

//    void deleteRoomByZone(Zone zone);
}
