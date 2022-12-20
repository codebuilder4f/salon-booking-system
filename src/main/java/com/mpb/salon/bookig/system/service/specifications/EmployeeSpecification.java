package com.mpb.salon.bookig.system.service.specifications;

import com.mpb.salon.bookig.system.entity.Employee;
import com.mpb.salon.bookig.system.web.req.EmployeeSearch;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification implements Specification<Employee> {

    private final EmployeeSearch employeeSearch;

    public EmployeeSpecification(EmployeeSearch employeeSearch) {
        this.employeeSearch = employeeSearch;
    }

    @Override
    public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (employeeSearch != null){
            if (employeeSearch.getFullName() != null){
                predicates.add(criteriaBuilder.like(root.get("fullName"), "%" + employeeSearch.getFullName() + "%"));
            }
            if (employeeSearch.getAddress() != null){
                predicates.add(criteriaBuilder.like(root.get("address"), "%" + employeeSearch.getAddress() + "%"));
            }
            if (employeeSearch.getMobile() != null){
                predicates.add(criteriaBuilder.like(root.get("mobile"), "%" + employeeSearch.getMobile() + "%"));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
