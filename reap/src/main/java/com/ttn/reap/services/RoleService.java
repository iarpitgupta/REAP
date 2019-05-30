package com.ttn.reap.services;

import com.ttn.reap.entities.Employee;
import com.ttn.reap.entities.Role;
import com.ttn.reap.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EmployeeService employeeService;

    public Role findRole(String role) {
        return roleRepository.findByRole(role);
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public void giveRoleAndUpdateBadges(String giverole, String email) {
        Employee employee = employeeService.getEmployeeByEmail(email);
        Role role = findRole(giverole);
        employee.getRoles().add(role);
        updateBadges(employee);
    }

    public void updateBadges(Employee employee) {
        Role practiceHead = findRole("Practice Head");
        Role supervisor = findRole("Supervisor");
        Role admin = findRole("Admin");
        Role user = findRole("User");
        if (employee.getRoles().contains(practiceHead)) {
            employee.setGold(practiceHead.getGold());
            employee.setSilver(practiceHead.getSilver());
            employee.setBronze(practiceHead.getBronze());
        } else if (employee.getRoles().contains(supervisor)) {
            employee.setGold(supervisor.getGold());
            employee.setSilver(supervisor.getSilver());
            employee.setBronze(supervisor.getBronze());
        } else if (employee.getRoles().contains(admin)) {
            employee.setGold(admin.getGold());
            employee.setSilver(admin.getSilver());
            employee.setBronze(admin.getBronze());
        } else if (employee.getRoles().contains(user)) {
            employee.setGold(user.getGold());
            employee.setSilver(user.getSilver());
            employee.setBronze(user.getBronze());
        }
        employeeService.saveEmployee(employee);

    }

    public void takeRoleAndUpdateBadges(String takerole, String email) {
        Employee employee = employeeService.getEmployeeByEmail(email);
        Role role = findRole(takerole);
        employee.getRoles().remove(role);
        updateBadges(employee);
    }
}
