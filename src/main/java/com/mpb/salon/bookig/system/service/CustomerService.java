package com.mpb.salon.bookig.system.service;


import com.mpb.salon.bookig.system.entity.Customer;
import com.mpb.salon.bookig.system.web.req.CustomerSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService extends CRUDServices<Customer>{
    Page<Customer> search(CustomerSearch customerSearch, Pageable pageable);
}
