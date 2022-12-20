package com.mpb.salon.bookig.system.service;

import com.mpb.salon.bookig.system.entity.Payment;
import com.mpb.salon.bookig.system.entity.StockItem;
import com.mpb.salon.bookig.system.service.dao.DayReport;
import com.mpb.salon.bookig.system.web.req.PaymentSearch;
import com.mpb.salon.bookig.system.web.req.StockItemSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface PaymentService extends CRUDServices<Payment>{
    Page<Payment> search(PaymentSearch paymentSearch, Pageable pageable);
    List<DayReport> monthlyEmployeePayment(Date from, Date to);
    List<DayReport> monthlyOtherPayment(Date from, Date to);
}
