package com.mpb.salon.bookig.system.service;


import com.mpb.salon.bookig.system.entity.Employee;
import com.mpb.salon.bookig.system.web.req.EmployeeSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService extends CRUDServices<Employee>{
    Page<Employee> search(EmployeeSearch employeeSearch, Pageable pageable);
    Double allEmployeeSalary();
}
