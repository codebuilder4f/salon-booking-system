package com.mpb.salon.bookig.system.repository;




import com.mpb.salon.bookig.system.entity.Employee;
import com.mpb.salon.bookig.system.entity.type.EmployeeState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID>, JpaSpecificationExecutor<Employee> {
    List<Employee> findAllByEmployeeState(@NotNull EmployeeState employeeState);
}
