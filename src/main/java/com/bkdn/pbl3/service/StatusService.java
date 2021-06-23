package com.bkdn.pbl3.service;

import com.bkdn.pbl3.domain.Equipment;
import com.bkdn.pbl3.domain.Status;

import java.util.List;
import java.util.Optional;

public interface StatusService {
    List<Status> findAll();

    <S extends Status> S save(S s);

    List<Status> findStatusByEquipment(Equipment equipment);

    Status getById(String s);

    Optional<Status> findById(String s);

    void deleteById(String s);

    void deleteStatusByEquipment(Equipment equipment);
}
