package com.ttn.reap.services;

import com.ttn.reap.entities.Employee;
import com.ttn.reap.repositories.EmployeeRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    EmployeeRepository employeeRepository;


    public boolean authenticate(String email, String password) {
        Employee employee = employeeRepository.findByEmail(email);
        if (employee == null) {
            return false;
        } else return BCrypt.checkpw(password,employee.getPassword()) && employee.getActive();
    }

    public boolean checkInactive(String email, String password){
        Employee employee = employeeRepository.findByEmail(email);
        if(employee == null){
            return false;
        }
        else return BCrypt.checkpw(password,employee.getPassword()) && !employee.getActive();
    }
}
