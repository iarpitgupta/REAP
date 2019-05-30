package com.ttn.reap.repositories;

import com.ttn.reap.entities.Employee;
import com.ttn.reap.entities.EmployeeOrder;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeOrderRepository extends CrudRepository<EmployeeOrder, Integer> {

    List<EmployeeOrder> findAllByEmployee(Sort sort,Employee employee);
}
