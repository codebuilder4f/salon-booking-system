package com.mpb.salon.bookig.system.service.specifications;

import com.mpb.salon.bookig.system.entity.StockItem;
import com.mpb.salon.bookig.system.web.req.StockItemSearch;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockItemSpecification implements Specification<StockItem> {
    private final StockItemSearch stockItemSearch;

    public StockItemSpecification(StockItemSearch stockItemSearch) {
        this.stockItemSearch = stockItemSearch;
    }

    @Override
    public Predicate toPredicate(Root<StockItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (stockItemSearch != null){
            if (stockItemSearch.getName() != null){
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + stockItemSearch.getName() + "%"));
            }

            if (stockItemSearch.getFrom() != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("addedDate"),  stockItemSearch.getFrom() ));
            }

            if (stockItemSearch.getTo() != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("addedDate"),  stockItemSearch.getTo() ));
            }
//            if (stockItemSearch.getPrice() != null){
//                predicates.add(criteriaBuilder.like(root.get("price"), "%" + stockItemSearch.getPrice() + "%"));
//            }
//            if (stockItemSearch.getAddedDate() != null){
//                predicates.add(criteriaBuilder.like(root.get("addedDate"), "%" + stockItemSearch.getAddedDate() + "%"));
//            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
