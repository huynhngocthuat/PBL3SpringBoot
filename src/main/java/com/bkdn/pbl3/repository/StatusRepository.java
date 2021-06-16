package com.bkdn.pbl3.repository;

import com.bkdn.pbl3.domain.Equipment;
import com.bkdn.pbl3.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, String> {
    List<Status> findStatusByEquipment(Equipment equipment);
}
