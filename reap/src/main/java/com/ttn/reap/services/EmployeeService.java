package com.ttn.reap.services;

import com.ttn.reap.entities.Employee;
import com.ttn.reap.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee getEmployeeByEmail(String email) {

        return employeeRepository.findByEmail(email);
    }

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public Employee getEmployeeByToken(String token) {
        return employeeRepository.findByResetToken(token);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void setEmployeeActiveStatus(Boolean active, String email) {
        Employee employee = getEmployeeByEmail(email);
        employee.setActive(active);
        saveEmployee(employee);
    }

    public void changeGoldBadge(Integer gold, String email) {
        Employee employee = getEmployeeByEmail(email);
        employee.setGold(gold);
        saveEmployee(employee);
    }

    public void changeSilverBadge(Integer silver, String email) {
        Employee employee = getEmployeeByEmail(email);
        employee.setSilver(silver);
        saveEmployee(employee);
    }

    public void changeBronzeBadge(Integer bronze, String email) {
        Employee employee = getEmployeeByEmail(email);
        employee.setBronze(bronze);
        saveEmployee(employee);
    }
}
