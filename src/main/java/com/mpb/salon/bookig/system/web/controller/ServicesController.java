package com.mpb.salon.bookig.system.web.controller;

import com.mpb.salon.bookig.system.entity.Services;
import com.mpb.salon.bookig.system.service.ServicesService;
import com.mpb.salon.bookig.system.web.api.API;
import com.mpb.salon.bookig.system.web.api.ServiceAPI;
import com.mpb.salon.bookig.system.web.exception.ExceptionWithMessage;
import com.mpb.salon.bookig.system.web.req.ServicesSearch;
import com.mpb.salon.bookig.system.web.res.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ServicesController implements ServiceAPI {

    private final ServicesService servicesService;

    public ServicesController(ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    @Override
    public ResponseEntity<ApiResponse> get(UUID uuid) {
        return API.send(servicesService.get(uuid), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> search(ServicesSearch servicesSearch, int page, int size, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, size, Sort.by(!Objects.equals(sort, "-") ? sort: "name")).toOptional();
        if (pageable.isEmpty()){
            throw new ExceptionWithMessage("Page request is not valid!. Please check again");
        }
        return API.send(servicesService.search(servicesSearch, pageable.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> add(Services customer) {
        return API.send(servicesService.create(customer), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse> update(Services customer, UUID uuid) {
        return API.send(servicesService.update(uuid, customer), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> delete(UUID uuid) {
        servicesService.delete(uuid);
        return API.send(null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> page(int page, int size, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, size, Sort.by(!Objects.equals(sort, "-") ? sort : "name")).toOptional();
        if (pageable.isEmpty()){
            throw new ExceptionWithMessage("Page request is not valid!!. Please check again");
        }
        return API.send(servicesService.getAll(pageable.get()), HttpStatus.OK);    }
}
