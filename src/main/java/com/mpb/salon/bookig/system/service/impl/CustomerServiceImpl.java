package com.mpb.salon.bookig.system.service.impl;

import com.mpb.salon.bookig.system.entity.Customer;
import com.mpb.salon.bookig.system.repository.CustomerRepository;
import com.mpb.salon.bookig.system.service.CustomerService;
import com.mpb.salon.bookig.system.service.specifications.CustomerSpecification;
import com.mpb.salon.bookig.system.web.exception.EntityNotFoundException;
import com.mpb.salon.bookig.system.web.req.CustomerSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer get(UUID uuid) {
        Optional<Customer> customer = repository.findById(uuid);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new EntityNotFoundException(Customer.class, "id", uuid.toString());
        }
    }

    @Override
    public Customer create(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Customer update(UUID uuid, Customer customer) {
        Optional<Customer> customer1 = repository.findById(uuid);
        if (customer1.isPresent()) {
            customer.setId(uuid);
            return repository.save(customer);
        } else {
            throw new EntityNotFoundException(Customer.class, "id", uuid.toString());
        }
    }

    @Override
    public void delete(UUID uuid) {
        Optional<Customer> customer = repository.findById(uuid);
        if (customer.isPresent()) {
            repository.delete(customer.get());
        } else {
            throw new EntityNotFoundException(Customer.class, "id", uuid.toString());
        }
    }

    @Override
    public List<Customer> createAll(List<Customer> list) {
        return list.stream().parallel().map(this::create).collect(Collectors.toList());
    }

    @Override
    public Page<Customer> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Customer> search(CustomerSearch customerSearch, Pageable pageable) {
        return repository.findAll(new CustomerSpecification(customerSearch),pageable);
    }
}
