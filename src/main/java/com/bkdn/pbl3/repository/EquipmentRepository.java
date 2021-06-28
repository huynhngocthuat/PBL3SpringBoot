package com.bkdn.pbl3.repository;

import com.bkdn.pbl3.domain.Equipment;
import com.bkdn.pbl3.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, String> {
    List<Equipment> findEquipmentByRoom(Room room);
    Page<Equipment> findByEquipmentIdContaining(String equipmentId, Pageable pageable);
}
