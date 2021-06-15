package com.bkdn.pbl3.service;

import com.bkdn.pbl3.domain.Equipment;

import java.util.List;
import java.util.Optional;

public interface EquipmentService {
    List<Equipment> findAll();

    Optional<Equipment> findById(String s);

    <S extends Equipment> S save(S s);

    void deleteById(String s);
}
