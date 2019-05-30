package com.ttn.reap.services;

import com.ttn.reap.entities.Employee;
import com.ttn.reap.entities.Post;
import com.ttn.reap.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {


    @Autowired
    EmployeeRepository employeeRepository;



    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }
}
