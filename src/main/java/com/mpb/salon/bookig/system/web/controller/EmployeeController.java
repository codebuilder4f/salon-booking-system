package com.mpb.salon.bookig.system.web.controller;

import com.mpb.salon.bookig.system.entity.Employee;
import com.mpb.salon.bookig.system.service.EmployeeService;
import com.mpb.salon.bookig.system.web.api.API;
import com.mpb.salon.bookig.system.web.api.EmployeeAPI;
import com.mpb.salon.bookig.system.web.exception.ExceptionWithMessage;
import com.mpb.salon.bookig.system.web.req.EmployeeSearch;
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
public class EmployeeController implements EmployeeAPI {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<ApiResponse> get(UUID uuid) {
        return API.send(employeeService.get(uuid), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> search(EmployeeSearch employeeSearch, int page, int size, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, size, Sort.by(!Objects.equals(sort, "-") ? sort: "fullName")).toOptional();
        if (pageable.isEmpty()){
            throw new ExceptionWithMessage("Page request is not valid!. Please check again");
        }
        return API.send(employeeService.search(employeeSearch, pageable.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> add(Employee employee) {
        return API.send(employeeService.create(employee), HttpStatus.CREATED,"Success");
    }

    @Override
    public ResponseEntity<ApiResponse> update(Employee employee, UUID uuid) {
        return API.send(employeeService.update(uuid, employee), HttpStatus.CREATED,"Success");
    }

    @Override
    public ResponseEntity<ApiResponse> delete(UUID uuid) {
        employeeService.delete(uuid);
        return API.send(null, HttpStatus.OK,"Success");
    }

    @Override
    public ResponseEntity<ApiResponse> page(int page, int size, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, size, Sort.by(!Objects.equals(sort, "-") ? sort : "fullName")).toOptional();
        if (pageable.isEmpty()){
            throw new ExceptionWithMessage("Page request is not valid!!. Please check again");
        }
        return API.send(employeeService.getAll(pageable.get()), HttpStatus.OK);
    }
}
