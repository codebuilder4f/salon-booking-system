package com.mpb.salon.bookig.system.service.specifications;

import com.mpb.salon.bookig.system.entity.Services;
import com.mpb.salon.bookig.system.web.req.ServicesSearch;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ServicesSpecification implements Specification<Services> {
    private final ServicesSearch servicesSearch;

    public ServicesSpecification(ServicesSearch servicesSearch) {
        this.servicesSearch = servicesSearch;
    }

    @Override
    public Predicate toPredicate(Root<Services> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (servicesSearch != null){
            if (servicesSearch.getName() != null){
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + servicesSearch.getName() + "%"));
            }
//            if (servicesSearch.getPrice() != null){
//                predicates.add(criteriaBuilder.like(root.get("price"), "%" + servicesSearch.getPrice() + "%"));
//            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
