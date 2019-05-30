package com.ttn.reap.services;

import com.ttn.reap.entities.Employee;
import com.ttn.reap.entities.EmployeeReceivedStar;
import com.ttn.reap.entities.Role;
import com.ttn.reap.repositories.EmployeeReceivedStarRepository;
import com.ttn.reap.repositories.EmployeeRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class SignupService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeReceivedStarRepository employeeReceivedStarRepository;

    @Autowired
    RoleService roleService;

    public boolean saveEmployee(Employee employee, MultipartFile employeeProfilePic) throws IOException {
        if(employeeRepository.findByEmail(employee.getEmail())!=null){
            return false;
        }
        String filePath;
        if (employeeProfilePic.getOriginalFilename().equals("")) {
            filePath = "/assets/images/reapprofilepic.png";
        } else {
            filePath = saveProfilePic(employeeProfilePic);
        }
        EmployeeReceivedStar employeeReceivedStar = new EmployeeReceivedStar();
        Role role = roleService.findRole("User");
        String encodedPassword = encodePassword(employee.getPassword());
        employee.setPassword(encodedPassword);
        employee.setGold(role.getGold());
        employee.setSilver(role.getSilver());
        employee.setBronze(role.getBronze());
        employee.setCreatedOn(new Date());
        employee.setProfilePic(filePath);
        employee.setActive(true);
        employee.getRoles().add(role);
        employeeReceivedStar.setGold(0);
        employeeReceivedStar.setSilver(0);
        employeeReceivedStar.setBronze(0);
        employeeReceivedStar.setPointsRedeemed(0);
        employeeReceivedStar.setTotalPoints(0);
        employeeReceivedStar.setEmployee(employee);
        employeeRepository.save(employee);
        employeeReceivedStarRepository.save(employeeReceivedStar);
        return true;
    }

    public String saveProfilePic(MultipartFile employeeProfilePic) throws IOException {
        String absolutePath = "/home/arpit/Reap/reap/src/main/resources/static/assets/images/";
        String productionPath = "/home/arpit/Reap/reap/out/production/resources/static/assets/images/";
        byte[] image = employeeProfilePic.getBytes();
        Path path = Paths.get(absolutePath + employeeProfilePic.getOriginalFilename());
        Path path1 = Paths.get(productionPath + employeeProfilePic.getOriginalFilename());
        Files.write(path, image);
        Files.write(path1, image);
        return "/assets/images/" + employeeProfilePic.getOriginalFilename();
    }

    public String encodePassword(String password){
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }
}
