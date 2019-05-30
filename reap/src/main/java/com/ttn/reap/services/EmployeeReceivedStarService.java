package com.ttn.reap.services;

import com.ttn.reap.entities.Employee;
import com.ttn.reap.entities.EmployeeReceivedStar;
import com.ttn.reap.repositories.EmployeeReceivedStarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeReceivedStarService {

    @Autowired
    EmployeeReceivedStarRepository employeeReceivedStarRepository;

    public EmployeeReceivedStar getEmployeeByEmployeeId(Employee employee) {
        return employeeReceivedStarRepository.findByEmployee(employee);
    }

    public List<EmployeeReceivedStar> getAllEmployeeReceivedStars() {
        return employeeReceivedStarRepository.findAll();
    }

    public List<EmployeeReceivedStar> getTopEmployeesWithMaxPoints() {
        return employeeReceivedStarRepository.findFirst5ByOrderByTotalPointsDesc();
    }

    public void saveEmployee(EmployeeReceivedStar employeeReceivedStar) {
        employeeReceivedStarRepository.save(employeeReceivedStar);
    }

    public void changeTotalPoints(Integer points, Employee employee){
        EmployeeReceivedStar employeeReceivedStar = getEmployeeByEmployeeId(employee);
        employeeReceivedStar.setTotalPoints(points);
        employeeReceivedStarRepository.save(employeeReceivedStar);
    }

    public void placedOrderSettlement(EmployeeReceivedStar employeeReceivedStar, Integer cartPrice) {
        employeeReceivedStar.setTotalPoints(employeeReceivedStar.getTotalPoints() - cartPrice);
        employeeReceivedStarRepository.save(employeeReceivedStar);
    }
}
