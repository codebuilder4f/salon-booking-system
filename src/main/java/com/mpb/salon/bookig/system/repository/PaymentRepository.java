package com.mpb.salon.bookig.system.repository;

import com.mpb.salon.bookig.system.entity.Payment;
import com.mpb.salon.bookig.system.entity.type.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID>, JpaSpecificationExecutor<Payment> {
    List<Payment> findAllByPaymentTypeAndPaymentDateBetween(PaymentType paymentType, Date paymentDate, Date paymentDate2);
}
