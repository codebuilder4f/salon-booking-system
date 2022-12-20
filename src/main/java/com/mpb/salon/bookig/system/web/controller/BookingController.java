package com.mpb.salon.bookig.system.web.controller;

import com.mpb.salon.bookig.system.ResourceBundleUtil;
import com.mpb.salon.bookig.system.entity.Booking;
import com.mpb.salon.bookig.system.service.BookingService;
import com.mpb.salon.bookig.system.web.api.API;
import com.mpb.salon.bookig.system.web.api.BookingApi;
import com.mpb.salon.bookig.system.web.exception.ExceptionWithMessage;
import com.mpb.salon.bookig.system.web.req.BookingSearch;
import com.mpb.salon.bookig.system.web.res.ApiResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
public class BookingController implements BookingApi {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public ResponseEntity<byte[]> getPdf(String name) throws IOException {
        String tempBillPath = ResourceBundleUtil.getYmlStringForActive("application","app.temp-bill-path");
        byte[] bFile = Files.readAllBytes(new File(tempBillPath+name+".pdf").toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(bFile.length);

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bFile, headers, HttpStatus.OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<ApiResponse> get(UUID uuid) {
        return API.send(bookingService.get(uuid), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> getBookedTimes(Date date) {
        return API.send(bookingService.getBookedTimes(date), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> search(BookingSearch bookingSearch, int page, int size, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, size, Sort.by(!Objects.equals(sort, "-") ? sort: "totalAmount")).toOptional();
        if (pageable.isEmpty()){
            throw new ExceptionWithMessage("Page request is not valid!. Please check again");
        }
        return API.send(bookingService.search(bookingSearch, pageable.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> getBill(UUID uuid) {
        return API.send(bookingService.getBill(uuid),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> add(Booking booking) {
        return API.send(bookingService.create(booking), HttpStatus.CREATED,"Success");
    }

    @Override
    public ResponseEntity<ApiResponse> update(Booking booking, UUID uuid) {
        return API.send(bookingService.update(uuid,booking), HttpStatus.CREATED,"Success");
    }

    @Override
    public ResponseEntity<ApiResponse> delete(UUID uuid) {
        bookingService.delete(uuid);
        return API.send(null, HttpStatus.OK,"Success");
    }

    @Override
    public ResponseEntity<ApiResponse> page(int page, int size, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, size, Sort.by(!Objects.equals(sort, "-") ? sort: "totalAmount")).toOptional();
        if (pageable.isEmpty()){
            throw new ExceptionWithMessage("Page request is not valid!. Please check again");
        }
        return API.send(bookingService.getAll(pageable.get()), HttpStatus.OK);
    }
}
