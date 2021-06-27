package com.bkdn.pbl3.service.impl;

import com.bkdn.pbl3.domain.Room;
import com.bkdn.pbl3.domain.Zone;
import com.bkdn.pbl3.repository.RoomRepository;
import com.bkdn.pbl3.service.EquipmentService;
import com.bkdn.pbl3.service.RoomService;
import com.bkdn.pbl3.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private EquipmentService equipmentService;

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Page<Room> findAll(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    @Override
    public List<Room> findRoomByZone(Zone zone) {
        return roomRepository.findRoomByZone(zone);
    }

    @Override
    public Room getById(String s) {
        return roomRepository.getById(s);
    }

    @Override
    public <S extends Room> S save(S s) {
        return roomRepository.save(s);
    }

    @Override
    public Optional<Room> findById(String s) {
        return roomRepository.findById(s);
    }

    @Override
    public void deleteById(String s) {
        roomRepository.deleteById(s);
    }
//    @Override
//    public  void deleteRoomByZone(Zone zone){
//        List<Room> list = this.findRoomByZone(zone);
//        for(Room room : list){
//            equipmentService.deleteEquipmentByRoom(room);
//            this.deleteById(room.getRoomId());
//        }
//    }

    @Override
    @Transactional
    @Query(value = "UPDATE room SET room_function = ?1, zone_id = ?2 WHERE room_id = ?3", nativeQuery = true)
    @Modifying
    public int updateRoom(String roomFunction, String zoneId, String roomId) {
        return roomRepository.updateRoom(roomFunction, zoneId, roomId);
    }

    @Override
    public Page<Room> findByRoomIdContaining(String roomId, Pageable pageable) {
        return roomRepository.findByRoomIdContaining(roomId, pageable);
    }
}
