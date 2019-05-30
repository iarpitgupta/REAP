package com.ttn.reap.schedulers;

import com.ttn.reap.entities.Employee;
import com.ttn.reap.entities.Role;
import com.ttn.reap.services.EmployeeService;
import com.ttn.reap.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BadgesScheduler {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    RoleService roleService;

    @Scheduled(cron = "0 0 1 1/3 * ?")
    public void scheduleTaskWithCronExpression() {
        Role practiceHead = roleService.findRole("Practice Head");
        Role supervisor = roleService.findRole("Supervisor");
        Role admin = roleService.findRole("Admin");
        Role user = roleService.findRole("User");
        List<Employee> employeeList = employeeService.getAllEmployees();
        for (Employee employee : employeeList) {
            if (employee.getRoles().stream().anyMatch(employeeRole -> employeeRole.equals(practiceHead))) {
                employee.setGold(practiceHead.getGold());
                employee.setSilver(practiceHead.getSilver());
                employee.setBronze(practiceHead.getBronze());
            } else if (employee.getRoles().stream().anyMatch(employeeRole -> employeeRole.equals(supervisor))) {
                employee.setGold(supervisor.getGold());
                employee.setSilver(supervisor.getSilver());
                employee.setBronze(supervisor.getBronze());
            } else if (employee.getRoles().stream().anyMatch(employeeRole -> employeeRole.equals(admin))) {
                employee.setGold(admin.getGold());
                employee.setSilver(admin.getSilver());
                employee.setBronze(admin.getBronze());
            } else if (employee.getRoles().stream().anyMatch(employeeRole -> employeeRole.equals(user))) {
                employee.setGold(user.getGold());
                employee.setSilver(user.getSilver());
                employee.setBronze(user.getBronze());
            }
            employeeService.saveEmployee(employee);
        }
    }
}
