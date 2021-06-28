package com.bkdn.pbl3.service.impl;

import com.bkdn.pbl3.domain.Equipment;
import com.bkdn.pbl3.domain.Room;
import com.bkdn.pbl3.domain.Status;
import com.bkdn.pbl3.repository.EquipmentRepository;
import com.bkdn.pbl3.service.EquipmentService;
import com.bkdn.pbl3.service.ReportService;
import com.bkdn.pbl3.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    EquipmentRepository equipmentRepository;
    @Autowired
    StatusService statusService;

    @Autowired
    ReportService reportService;

    @Override
    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    @Override
    public Page<Equipment> findAll(Pageable pageable) {
        return equipmentRepository.findAll(pageable);
    }

    @Override
    public List<Equipment> findEquipmentByRoom(Room room) {
        return equipmentRepository.findEquipmentByRoom(room);
    }

    @Override
    public Equipment getById(String s) {
        return equipmentRepository.getById(s);
    }

    @Override
    public Optional<Equipment> findById(String s) {
        return equipmentRepository.findById(s);
    }

    @Override
    public <S extends Equipment> S save(S s) {
        return equipmentRepository.save(s);
    }

    @Override
    public void deleteById(String s) {
        equipmentRepository.deleteById(s);
    }
//    @Override
//    public  void deleteEquipmentByRoom(Room room){
//        //FIX CHO NAY
//        List<Equipment> list = this.findEquipmentByRoom(room);
//        for(Equipment equipment : list){
//            statusService.deleteStatusByEquipment(equipment);
//            reportService.deleteReportByEquipment(equipment);
//            this.deleteById(equipment.getEquipmentId());
//        }
//    }


    @Override
    public Page<Equipment> findByEquipmentIdContaining(String equipmentId, Pageable pageable) {
        return equipmentRepository.findByEquipmentIdContaining(equipmentId, pageable);
    }
}
