package com.mpb.salon.bookig.system.service.impl;


import com.mpb.salon.bookig.system.entity.Employee;
import com.mpb.salon.bookig.system.entity.type.EmployeeState;
import com.mpb.salon.bookig.system.repository.EmployeeRepository;
import com.mpb.salon.bookig.system.service.EmployeeService;
import com.mpb.salon.bookig.system.service.specifications.EmployeeSpecification;
import com.mpb.salon.bookig.system.web.exception.EntityNotFoundException;
import com.mpb.salon.bookig.system.web.req.EmployeeSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.repository = employeeRepository;
    }

    @Override
    public Employee get(UUID uuid) {
        Optional<Employee> category = repository.findById(uuid);
        if (category.isPresent()) {
            return category.get();
        } else {
            throw new EntityNotFoundException(Employee.class, "id", uuid.toString());
        }
    }

    @Override
    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public Employee update(UUID uuid, Employee employee) {
        Optional<Employee> category1 = repository.findById(uuid);
        if (category1.isPresent()) {
            employee.setId(uuid);
            return repository.save(employee);
        } else {
            throw new EntityNotFoundException(Employee.class, "id", uuid.toString());
        }
    }

    @Override
    public void delete(UUID uuid) {
        Optional<Employee> addon = repository.findById(uuid);
        if (addon.isPresent()) {
            repository.delete(addon.get());
        } else {
            throw new EntityNotFoundException(Employee.class, "id", uuid.toString());
        }
    }

    @Override
    public List<Employee> createAll(List<Employee> list) {
        return list.stream().parallel().map(this::create).collect(Collectors.toList());
    }

    @Override
    public Page<Employee> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Employee> search(EmployeeSearch employeeSearch, Pageable pageable) {
        return repository.findAll(new EmployeeSpecification(employeeSearch), pageable);
    }

    @Override
    public Double allEmployeeSalary() {
        return this.repository.findAllByEmployeeState(EmployeeState.WORKING)
                .stream()
                .map(Employee::getMonthlyPayment)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
