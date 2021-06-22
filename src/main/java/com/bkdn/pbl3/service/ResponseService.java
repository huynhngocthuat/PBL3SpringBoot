package com.bkdn.pbl3.service;

import com.bkdn.pbl3.domain.Response;

import java.util.List;
import java.util.Optional;

public interface ResponseService {
    List<Response> findAll();

    Response getById(Long aLong);

    <S extends Response> S save(S s);

    Optional<Response> findById(Long aLong);

    void deleteById(Long aLong);
}
