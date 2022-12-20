package com.mpb.salon.bookig.system.web.api;

import com.mpb.salon.bookig.system.entity.Customer;
import com.mpb.salon.bookig.system.web.req.CustomerSearch;
import com.mpb.salon.bookig.system.web.res.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.mpb.salon.bookig.system.Application.API_V;


@RequestMapping(path = "/"+API_V+"/customer")
public interface CustomerAPI {
    @GetMapping(value = "/{uuid}")
    ResponseEntity<ApiResponse> get(@PathVariable UUID uuid);

    @PostMapping(value = "/search")
    ResponseEntity<ApiResponse> search(
            @RequestBody CustomerSearch customerSearch,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "-") String sort
    );

    @PostMapping()
    ResponseEntity<ApiResponse> add(@RequestBody Customer customer);

    @PutMapping(value = "/{uuid}")
    ResponseEntity<ApiResponse> update(@RequestBody Customer customer, @PathVariable UUID uuid);

    @DeleteMapping(value = "/{uuid}")
    ResponseEntity<ApiResponse> delete(@PathVariable UUID uuid);

    @GetMapping()
    ResponseEntity<ApiResponse> page(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "-") String sort
    );
}
