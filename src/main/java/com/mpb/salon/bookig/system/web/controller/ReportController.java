package com.mpb.salon.bookig.system.web.controller;

import com.mpb.salon.bookig.system.service.BookingService;
import com.mpb.salon.bookig.system.service.PaymentService;
import com.mpb.salon.bookig.system.service.StockItemService;
import com.mpb.salon.bookig.system.web.api.API;
import com.mpb.salon.bookig.system.web.api.ReportApi;
import com.mpb.salon.bookig.system.web.req.FromTo;
import com.mpb.salon.bookig.system.web.res.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController implements ReportApi {

    private final BookingService bookingService;
    private final StockItemService stockItemService;
    private final PaymentService paymentService;


    public ReportController(BookingService bookingService, StockItemService stockItemService, PaymentService paymentService) {
        this.bookingService = bookingService;
        this.stockItemService = stockItemService;
        this.paymentService = paymentService;
    }

    @Override
    public ResponseEntity<ApiResponse> dayIncome(FromTo fromTo) {
        return API.send(bookingService.dayIncome(fromTo.getFrom(), fromTo.getTo()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> dayBooking(FromTo fromTo) {
        return API.send(bookingService.dayBookingCount(fromTo.getFrom(), fromTo.getTo()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> monthlyIncome(FromTo fromTo) {
        return API.send(bookingService.approvedMonthlyIncomes(fromTo.getFrom(), fromTo.getTo()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> monthlyStockCost(FromTo fromTo) {
        return API.send(stockItemService.monthlyStockAddingCost(fromTo.getFrom(), fromTo.getTo()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> monthlyEmployeePayment(FromTo fromTo) {
        return API.send(paymentService.monthlyEmployeePayment(fromTo.getFrom(), fromTo.getTo()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> monthlyOtherPayment(FromTo fromTo) {
        return API.send(paymentService.monthlyOtherPayment(fromTo.getFrom(), fromTo.getTo()), HttpStatus.OK);
    }
}
