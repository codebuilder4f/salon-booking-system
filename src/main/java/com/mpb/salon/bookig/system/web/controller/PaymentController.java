package com.mpb.salon.bookig.system.web.controller;

import com.mpb.salon.bookig.system.entity.Payment;
import com.mpb.salon.bookig.system.service.PaymentService;
import com.mpb.salon.bookig.system.web.api.API;
import com.mpb.salon.bookig.system.web.api.PaymentApi;
import com.mpb.salon.bookig.system.web.exception.ExceptionWithMessage;
import com.mpb.salon.bookig.system.web.req.PaymentSearch;
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
public class PaymentController implements PaymentApi {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public ResponseEntity<ApiResponse> get(UUID uuid) {
        return API.send(paymentService.get(uuid), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> add(Payment payment) {
        return API.send(paymentService.create(payment), HttpStatus.CREATED,"Success");
    }

    @Override
    public ResponseEntity<ApiResponse> update(Payment payment, UUID uuid) {
        return API.send(paymentService.update(uuid, payment), HttpStatus.CREATED,"Success");
    }

    @Override
    public ResponseEntity<ApiResponse> delete(UUID uuid) {
        paymentService.delete(uuid);
        return API.send(null, HttpStatus.OK,"Success");
    }

    @Override
    public ResponseEntity<ApiResponse> page(int page, int size, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, size, Sort.by(!Objects.equals(sort, "-") ? sort : "paymentDate")).toOptional();
        if (pageable.isEmpty()){
            throw new ExceptionWithMessage("Page request is not valid!!. Please check again");
        }
        return API.send(paymentService.getAll(pageable.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> search(PaymentSearch paymentSearch, int page, int size, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, size, Sort.by(!Objects.equals(sort, "-") ? sort: "paymentDate")).toOptional();
        if (pageable.isEmpty()) {
            throw new ExceptionWithMessage("Page request is not valid!. Please check again");
        }
        return API.send(paymentService.search(paymentSearch, pageable.get()), HttpStatus.OK);
    }
}
