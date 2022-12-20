package com.mpb.salon.bookig.system.service.specifications;

import com.mpb.salon.bookig.system.entity.Booking;
import com.mpb.salon.bookig.system.entity.Customer;
import com.mpb.salon.bookig.system.web.req.BookingSearch;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class BookingSpecification implements Specification<Booking> {
    private final BookingSearch bookingSearch;

    public BookingSpecification(BookingSearch bookingSearch) {
        this.bookingSearch = bookingSearch;
    }

    @Override
    public Predicate toPredicate(Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (bookingSearch != null){
            if (bookingSearch.getBookingState() != null){
                predicates.add(criteriaBuilder.equal(root.get("bookingState"),  bookingSearch.getBookingState() ));
            }

            if (bookingSearch.getCustomerName() != null){
                Join<Booking, Customer> join = root.join("customer", JoinType.INNER);
                predicates.add(criteriaBuilder.like(join.get("name"), "%" + bookingSearch.getCustomerName() + "%"));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
