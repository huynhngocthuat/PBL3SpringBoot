package com.bkdn.pbl3.service.impl;

import com.bkdn.pbl3.domain.*;
import com.bkdn.pbl3.repository.ResponseRepository;
import com.bkdn.pbl3.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResponseServiceImp implements ResponseService {
    @Autowired
    private ResponseRepository responseRepository;

    @Override
    public List<Response> findAll() {
        return responseRepository.findAll();
    }

    @Override
    public Response getById(Long aLong) {
        return responseRepository.getById(aLong);
    }

    @Override
    public List<Response> findResponsesByAccount(Account account) {
        return responseRepository.findResponsesByAccount(account);
    }

    @Override
    public List<Response> findResponsesByReport(Report report) {
        return responseRepository.findResponsesByReport(report);
    }

    @Override
    public <S extends Response> S save(S s) {
        return responseRepository.save(s);
    }

    @Override
    public Optional<Response> findById(Long aLong) {
        return responseRepository.findById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        responseRepository.deleteById(aLong);
    }

    @Override
    public  void deleteResponseByReport(Report report){
        List<Response> list = this.findResponsesByReport(report);
        for(Response response : list){
            this.deleteById(response.getResponseId());
        }
    }

}
