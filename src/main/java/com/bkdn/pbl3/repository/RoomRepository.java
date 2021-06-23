package com.bkdn.pbl3.repository;

import com.bkdn.pbl3.domain.Room;
import com.bkdn.pbl3.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    List<Room> findRoomByZone(Zone zone);
}
