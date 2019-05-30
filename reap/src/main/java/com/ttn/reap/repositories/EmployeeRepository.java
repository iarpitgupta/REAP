package com.ttn.reap.repositories;

import com.ttn.reap.entities.Employee;
import com.ttn.reap.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Integer> {

    Employee findByEmail(String email);

    Employee findByResetToken(String token);

    List<Employee> findAll();

}
