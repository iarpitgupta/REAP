package com.ttn.reap.repositories;

import com.ttn.reap.entities.Employee;
import com.ttn.reap.entities.EmployeeReceivedStar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeReceivedStarRepository extends CrudRepository<EmployeeReceivedStar, Integer> {

    EmployeeReceivedStar findByEmployee(Employee employee);

    List<EmployeeReceivedStar> findAll();


    List<EmployeeReceivedStar> findFirst5ByOrderByTotalPointsDesc();

}
