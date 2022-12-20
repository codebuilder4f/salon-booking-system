package com.mpb.salon.bookig.system.web.api;

import com.mpb.salon.bookig.system.entity.Employee;
import com.mpb.salon.bookig.system.entity.Payment;
import com.mpb.salon.bookig.system.web.req.PaymentSearch;
import com.mpb.salon.bookig.system.web.req.StockItemSearch;
import com.mpb.salon.bookig.system.web.res.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.mpb.salon.bookig.system.Application.API_V;

@RequestMapping(path = "/"+API_V+"/payment")
public interface PaymentApi {
    @GetMapping(value = "/{uuid}")
    ResponseEntity<ApiResponse> get(@PathVariable UUID uuid);

    @PostMapping()
    ResponseEntity<ApiResponse> add(@RequestBody Payment payment);

    @PutMapping(value = "/{uuid}")
    ResponseEntity<ApiResponse> update(@RequestBody Payment payment, @PathVariable UUID uuid);

    @DeleteMapping(value = "/{uuid}")
    ResponseEntity<ApiResponse> delete(@PathVariable UUID uuid);

    @GetMapping()
    ResponseEntity<ApiResponse> page(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "-") String sort
    );

    @PostMapping(value = "/search")
    ResponseEntity<ApiResponse> search(
            @RequestBody PaymentSearch paymentSearch,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "-") String sort
    );
}
