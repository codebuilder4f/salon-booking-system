package com.mpb.salon.bookig.system.service.specifications;

import com.mpb.salon.bookig.system.entity.Payment;
import com.mpb.salon.bookig.system.web.req.PaymentSearch;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PaymentSpecification implements Specification<Payment> {
    private final PaymentSearch PaymentSearch;

    public PaymentSpecification(PaymentSearch PaymentSearch) {
        this.PaymentSearch = PaymentSearch;
    }

    @Override
    public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (PaymentSearch != null){


            if (PaymentSearch.getFrom() != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("paymentDate"),  PaymentSearch.getFrom() ));
            }

            if (PaymentSearch.getTo() != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("paymentDate"),  PaymentSearch.getTo() ));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
