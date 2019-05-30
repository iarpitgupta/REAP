package com.ttn.reap.bootstrap;

import com.ttn.reap.entities.Badges;
import com.ttn.reap.entities.Employee;
import com.ttn.reap.entities.Role;
import com.ttn.reap.repositories.BadgeRepository;
import com.ttn.reap.repositories.EmployeeRepository;
import com.ttn.reap.repositories.RoleRepository;
import org.fluttercode.datafactory.impl.DataFactory;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;

@Component
public class FillDataBase {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BadgeRepository badgeRepository;


    @EventListener(ApplicationStartedEvent.class)
    void fillData() {
        DataFactory dataFactory = new DataFactory();
        Date minDate = dataFactory.getDate(2001, 01, 01);
        Date maxDate = new Date();

        Iterator<Role> roleIterator = roleRepository.findAll().iterator();
        if (!roleIterator.hasNext()) {
            Role admin = new Role();
            admin.setRole("Admin");
            admin.setGold(1);
            admin.setSilver(2);
            admin.setBronze(3);
            roleRepository.save(admin);
            Role practiceHead = new Role();
            admin.setRole("Practice Head");
            admin.setGold(3);
            admin.setSilver(6);
            admin.setBronze(9);
            roleRepository.save(practiceHead);
            Role supervisor = new Role();
            admin.setRole("Supervisor");
            admin.setGold(2);
            admin.setSilver(3);
            admin.setBronze(6);
            roleRepository.save(supervisor);
            Role user = new Role();
            admin.setRole("User");
            admin.setGold(1);
            admin.setSilver(2);
            admin.setBronze(3);
            roleRepository.save(user);
        }

        Iterator<Employee> employeeIterator = employeeRepository.findAll().iterator();
        if (!employeeIterator.hasNext()) {
            Employee employee = new Employee();
            employee.setFirstName("Arpit");
            employee.setLastName("Gupta");
            employee.setActive(true);
            employee.setEmail("arpit.gupta@tothenew.com");
            employee.setCreatedOn(dataFactory.getDateBetween(minDate, maxDate));
            employee.setGold(0);
            employee.setSilver(0);
            employee.setBronze(0);
            String salt = BCrypt.gensalt();
            employee.setPassword(BCrypt.hashpw("1234", salt));
            employee.getRoles().add(roleRepository.findByRole("User"));
            employee.getRoles().add(roleRepository.findByRole("Admin"));
            employeeRepository.save(employee);
        }

        Iterator<Badges> badgesIterator = badgeRepository.findAll().iterator();
        if (!badgesIterator.hasNext()) {
            Badges gold = new Badges();
            gold.setBadgeName("Gold");
            gold.setValue(30);
            badgeRepository.save(gold);
            Badges silver = new Badges();
            silver.setBadgeName("silver");
            silver.setValue(20);
            badgeRepository.save(silver);
            Badges bronze = new Badges();
            gold.setBadgeName("Bronze");
            gold.setValue(10);
            badgeRepository.save(bronze);
        }
    }
}
