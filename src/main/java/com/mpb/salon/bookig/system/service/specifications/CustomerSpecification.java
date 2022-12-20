package com.mpb.salon.bookig.system.service.specifications;

import com.mpb.salon.bookig.system.entity.Customer;
import com.mpb.salon.bookig.system.web.req.CustomerSearch;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CustomerSpecification implements Specification<Customer> {
    private final CustomerSearch customerSearch;

    public CustomerSpecification(CustomerSearch customerSearch) {
        this.customerSearch = customerSearch;
    }

    @Override
    public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (customerSearch != null){
            if (customerSearch.getName() != null){
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + customerSearch.getName() + "%"));
            }
            if (customerSearch.getAddress() != null){
                predicates.add(criteriaBuilder.like(root.get("address"), "%" + customerSearch.getAddress() + "%"));
            }
            if (customerSearch.getEmail() != null){
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + customerSearch.getEmail() + "%"));
            }
            if (customerSearch.getMobileOne() != null){
                predicates.add(criteriaBuilder.like(root.get("mobileOne"), "%" + customerSearch.getMobileOne() + "%"));
            }
            if (customerSearch.getMobileTwo() != null){
                predicates.add(criteriaBuilder.like(root.get("mobileTwo"), "%" + customerSearch.getMobileTwo() + "%"));
            }
            if (customerSearch.getNic() != null){
                predicates.add(criteriaBuilder.like(root.get("nic"), "%" +customerSearch.getNic() + "%"));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
