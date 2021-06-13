package com.bkdn.pbl3.service.impl;

import com.bkdn.pbl3.domain.Zone;
import com.bkdn.pbl3.repository.ZoneRepository;
import com.bkdn.pbl3.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZoneServiceImpl implements ZoneService {

    ZoneRepository zoneRepository;
    public ZoneServiceImpl(ZoneRepository zoneRepository){
        this.zoneRepository = zoneRepository;
    }

    @Override
    public List<Zone> findAll() {
        return zoneRepository.findAll();
    }

    @Override
    public Page<Zone> findAll(Pageable pageable) {
        return zoneRepository.findAll(pageable);
    }

    @Override
    public void delete(Zone zone) {
        zoneRepository.delete(zone);
    }

    @Override
    public Optional<Zone> findById(String s) {
        return zoneRepository.findById(s);
    }

    @Override
    public void deleteById(String s) {
        zoneRepository.deleteById(s);
    }

    @Override
    public List<Zone> findByZoneNameContaining(String name) {
        return zoneRepository.findByZoneNameContaining(name);
    }

    @Override
    public Page<Zone> findByZoneNameContaining(String name, Pageable pageable) {
        return zoneRepository.findByZoneNameContaining(name, pageable);
    }

    @Override
    public <S extends Zone> S save(S s) {
        return zoneRepository.save(s);
    }

}
