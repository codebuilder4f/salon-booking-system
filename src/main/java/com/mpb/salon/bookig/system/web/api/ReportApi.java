package com.mpb.salon.bookig.system.web.api;

import com.mpb.salon.bookig.system.web.req.FromTo;
import com.mpb.salon.bookig.system.web.res.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.mpb.salon.bookig.system.Application.API_V;

@RequestMapping(path = "/"+API_V+"/report")
public interface ReportApi {
    @PostMapping(value = "/day-income")
    ResponseEntity<ApiResponse> dayIncome(@RequestBody FromTo fromTo);

    @PostMapping(value = "/day-booking")
    ResponseEntity<ApiResponse> dayBooking(@RequestBody FromTo fromTo);

    @PostMapping(value = "/monthly-income")
    ResponseEntity<ApiResponse> monthlyIncome(@RequestBody FromTo fromTo);

    @PostMapping(value = "/monthly-stock-cost")
    ResponseEntity<ApiResponse> monthlyStockCost(@RequestBody FromTo fromTo);

    @PostMapping(value = "/monthly-employee-payment")
    ResponseEntity<ApiResponse> monthlyEmployeePayment(@RequestBody FromTo fromTo);

    @PostMapping(value = "/monthly-other-payment")
    ResponseEntity<ApiResponse> monthlyOtherPayment(@RequestBody FromTo fromTo);
}
