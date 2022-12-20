package com.mpb.salon.bookig.system.web.controller;


import com.mpb.salon.bookig.system.entity.StockItem;
import com.mpb.salon.bookig.system.service.StockItemService;
import com.mpb.salon.bookig.system.web.api.API;
import com.mpb.salon.bookig.system.web.api.StockItemAPI;
import com.mpb.salon.bookig.system.web.exception.ExceptionWithMessage;
import com.mpb.salon.bookig.system.web.req.StockItemSearch;
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
public class StockItemController implements StockItemAPI {

    private final StockItemService stockItemService;

    public StockItemController(StockItemService stockItemService) {
        this.stockItemService = stockItemService;
    }

    @Override
    public ResponseEntity<ApiResponse> get(UUID uuid) {
        return API.send(stockItemService.get(uuid), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> search(StockItemSearch stockItemSearch, int page, int size, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, size, Sort.by(!Objects.equals(sort, "-") ? sort: "name")).toOptional();
        if (pageable.isEmpty()) {
            throw new ExceptionWithMessage("Page request is not valid!. Please check again");
        }
        return API.send(stockItemService.search(stockItemSearch, pageable.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> add(StockItem stockItem) {
        return API.send(stockItemService.create(stockItem), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse> update(StockItem stockItem, UUID uuid) {
        return API.send(stockItemService.update(uuid, stockItem), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> delete(UUID uuid) {
        stockItemService.delete(uuid);
        return API.send(null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> page(int page, int size, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, size, Sort.by(!Objects.equals(sort, "-") ? sort : "name")).toOptional();
        if (pageable.isEmpty()){
            throw new ExceptionWithMessage("Page request is not valid!!. Please check again");
        }
        return API.send(stockItemService.getAll(pageable.get()), HttpStatus.OK);
    }
}
