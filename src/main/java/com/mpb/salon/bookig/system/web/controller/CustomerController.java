package com.mpb.salon.bookig.system.web.controller;

import com.mpb.salon.bookig.system.entity.Customer;
import com.mpb.salon.bookig.system.service.CustomerService;
import com.mpb.salon.bookig.system.web.api.API;
import com.mpb.salon.bookig.system.web.api.CustomerAPI;
import com.mpb.salon.bookig.system.web.exception.ExceptionWithMessage;
import com.mpb.salon.bookig.system.web.req.CustomerSearch;
import com.mpb.salon.bookig.system.web.res.ApiResponse;
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
public class CustomerController implements CustomerAPI {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public ResponseEntity<ApiResponse> get(UUID uuid) {
        return API.send(customerService.get(uuid), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> search(CustomerSearch customerSearch, int page, int size, String sort){
        Optional<Pageable> pageable = PageRequest.of(page, size, Sort.by(!Objects.equals(sort, "-") ? sort: "name")).toOptional();
        if (pageable.isEmpty()){
            throw new ExceptionWithMessage("Page requset is not valid!. Please check again");
        }
        return API.send(customerService.search(customerSearch, pageable.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> add(Customer customer) {
        return API.send(customerService.create(customer), HttpStatus.OK,"Success");
    }

    @Override
    public ResponseEntity<ApiResponse> update(Customer customer, UUID uuid) {
        return API.send(customerService.update(uuid, customer), HttpStatus.OK,"Success");
    }

    @Override
    public ResponseEntity<ApiResponse> delete(UUID uuid) {
        customerService.delete(uuid);
        return API.send(null, HttpStatus.OK,"Success");
    }

    @Override
    public ResponseEntity<ApiResponse> page(int page, int size, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, size, Sort.by(!Objects.equals(sort, "-") ? sort : "name")).toOptional();
        if (pageable.isEmpty()){
            throw new ExceptionWithMessage("Page request is not valid!!. Please check again");
        }
        return API.send(customerService.getAll(pageable.get()), HttpStatus.OK);
    }
}
