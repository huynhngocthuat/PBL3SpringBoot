package com.bkdn.pbl3.service;

import com.bkdn.pbl3.domain.Equipment;
import com.bkdn.pbl3.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EquipmentService {
    List<Equipment> findAll();

    Page<Equipment> findAll(Pageable pageable);

    List<Equipment> findEquipmentByRoom(Room room);

    Equipment getById(String s);

    Optional<Equipment> findById(String s);

    <S extends Equipment> S save(S s);

    void deleteById(String s);

    Page<Equipment> findByEquipmentIdContaining(String equipmentId, Pageable pageable);

//    void deleteEquipmentByRoom(Room room);
}
