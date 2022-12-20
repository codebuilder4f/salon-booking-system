package com.mpb.salon.bookig.system.web.api;

import com.mpb.salon.bookig.system.entity.Booking;
import com.mpb.salon.bookig.system.entity.Customer;
import com.mpb.salon.bookig.system.web.req.BookingSearch;
import com.mpb.salon.bookig.system.web.req.CustomerSearch;
import com.mpb.salon.bookig.system.web.res.ApiResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import static com.mpb.salon.bookig.system.Application.API_V;

@RequestMapping(path = "/"+API_V+"/booking")
public interface BookingApi {

    @RequestMapping(value = "/bill/{name}", method = RequestMethod.GET)
    ResponseEntity<byte[]> getPdf(@PathVariable("name")String name) throws IOException;


    @GetMapping(value = "/{uuid}")
    ResponseEntity<ApiResponse> get(@PathVariable UUID uuid);

    @GetMapping(value = "/booked-slots")
    ResponseEntity<ApiResponse> getBookedTimes(@RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date);

    @PostMapping(value = "/search")
    ResponseEntity<ApiResponse> search(
            @RequestBody BookingSearch bookingSearch,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "-") String sort
    );

    @GetMapping(value = "/{uuid}/get-bill")
    ResponseEntity<ApiResponse> getBill(@PathVariable UUID uuid);

    @PostMapping()
    ResponseEntity<ApiResponse> add(@RequestBody Booking booking);

    @PutMapping(value = "/{uuid}")
    ResponseEntity<ApiResponse> update(@RequestBody Booking booking, @PathVariable UUID uuid);

    @DeleteMapping(value = "/{uuid}")
    ResponseEntity<ApiResponse> delete(@PathVariable UUID uuid);

    @GetMapping()
    ResponseEntity<ApiResponse> page(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "-") String sort
    );
}
