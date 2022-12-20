package com.mpb.salon.bookig.system.service.impl;


import com.mpb.salon.bookig.system.entity.Payment;
import com.mpb.salon.bookig.system.entity.StockItem;
import com.mpb.salon.bookig.system.entity.type.PaymentType;
import com.mpb.salon.bookig.system.repository.PaymentRepository;
import com.mpb.salon.bookig.system.service.PaymentService;
import com.mpb.salon.bookig.system.service.dao.DayReport;
import com.mpb.salon.bookig.system.service.specifications.PaymentSpecification;
import com.mpb.salon.bookig.system.web.exception.EntityNotFoundException;
import com.mpb.salon.bookig.system.web.req.PaymentSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    public PaymentServiceImpl(PaymentRepository repository) {
        this.repository = repository;
    }


    @Override
    public Payment get(UUID uuid) {
        Optional<Payment> payment = repository.findById(uuid);
        if (payment.isPresent()) {
            return payment.get();
        } else {
            throw new EntityNotFoundException(Payment.class, "id", uuid.toString());
        }
    }

    @Override
    public Payment create(Payment payment) {
        return repository.save(payment);
    }

    @Override
    public Payment update(UUID uuid, Payment payment) {
        Optional<Payment> payment1 = repository.findById(uuid);
        if (payment1.isPresent()) {
            payment.setId(uuid);
            return repository.save(payment);
        } else {
            throw new EntityNotFoundException(Payment.class, "id", uuid.toString());
        }
    }

    @Override
    public void delete(UUID uuid) {
        Optional<Payment> payment = repository.findById(uuid);
        if (payment.isPresent()) {
            repository.delete(payment.get());
        } else {
            throw new EntityNotFoundException(Payment.class, "id", uuid.toString());
        }
    }

    @Override
    public List<Payment> createAll(List<Payment> list) {
        return list.stream().parallel().map(this::create).collect(Collectors.toList());
    }

    @Override
    public Page<Payment> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Payment> search(PaymentSearch paymentSearch, Pageable pageable) {
        return repository.findAll(new PaymentSpecification(paymentSearch), pageable);
    }

    @Override
    public List<DayReport> monthlyEmployeePayment(Date from, Date to) {
        return this.repository.findAllByPaymentTypeAndPaymentDateBetween(PaymentType.EMPLOYEE,from,to)
                .stream()
                .map(item -> new DayReport(item.getPaymentDate().toString().split(" ")[0], BigDecimal.valueOf(item.getAmount())))
                .collect(
                        Collectors.toMap(
                                DayReport::getMonth,
                                Function.identity(),
                                (sum1, sum2) -> new DayReport(
                                        sum1.getDay(),
                                        sum1.getAmount().add(sum2.getAmount())
                                )
                        )
                )
                .values()
                .stream()
                .sorted(Comparator.comparing(DayReport::getDay))
                .collect(Collectors.toList());
    }

    @Override
    public List<DayReport> monthlyOtherPayment(Date from, Date to) {
        return this.repository.findAllByPaymentTypeAndPaymentDateBetween(PaymentType.OTHER,from,to)
                .stream()
                .map(item -> new DayReport(item.getPaymentDate().toString().split(" ")[0], BigDecimal.valueOf(item.getAmount())))
                .collect(
                        Collectors.toMap(
                                DayReport::getMonth,
                                Function.identity(),
                                (sum1, sum2) -> new DayReport(
                                        sum1.getDay(),
                                        sum1.getAmount().add(sum2.getAmount())
                                )
                        )
                )
                .values()
                .stream()
                .sorted(Comparator.comparing(DayReport::getDay))
                .collect(Collectors.toList());
    }
}
