package com.bkdn.pbl3.service;

import com.bkdn.pbl3.domain.Room;
import com.bkdn.pbl3.domain.Zone;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    List<Room> findAll();

    List<Room> findRoomByZone(Zone zone);

    Room getById(String s);

    <S extends Room> S save(S s);

    Optional<Room> findById(String s);

    void deleteById(String s);

//    void deleteRoomByZone(Zone zone);
}
