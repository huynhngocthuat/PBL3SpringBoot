package com.bkdn.pbl3.service.impl;

import com.bkdn.pbl3.domain.Equipment;
import com.bkdn.pbl3.domain.Room;
import com.bkdn.pbl3.domain.Status;
import com.bkdn.pbl3.repository.StatusRepository;
import com.bkdn.pbl3.service.RoomService;
import com.bkdn.pbl3.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    StatusRepository statusRepository;

    @Override
    public List<Status> findAll() {
        return statusRepository.findAll();
    }

    @Override
    public <S extends Status> S save(S s) {
        return statusRepository.save(s);
    }

    @Override
    public List<Status> findStatusByEquipment(Equipment equipment) {
        return statusRepository.findStatusByEquipment(equipment);
    }

    @Override
    public Status getById(String s) {
        return statusRepository.getById(s);
    }

    @Override
    public Optional<Status> findById(String s) {
        return statusRepository.findById(s);
    }

    @Override
    public void deleteById(String s) {
        statusRepository.deleteById(s);
    }

    @Override
    public  void deleteStatusByEquipment(Equipment equipment){
        List<Status> list = this.findStatusByEquipment(equipment);
        for(Status status : list){
            this.deleteById(status.getStatusId());
        }
    }

}
