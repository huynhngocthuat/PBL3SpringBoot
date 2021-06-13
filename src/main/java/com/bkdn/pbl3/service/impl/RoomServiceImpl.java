package com.bkdn.pbl3.service.impl;

import com.bkdn.pbl3.domain.Room;
import com.bkdn.pbl3.repository.RoomRepository;
import com.bkdn.pbl3.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
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
}
