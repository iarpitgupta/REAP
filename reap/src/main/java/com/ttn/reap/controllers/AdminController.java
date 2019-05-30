package com.ttn.reap.controllers;

import com.ttn.reap.csv.CSVWriter;
import com.ttn.reap.entities.Employee;
import com.ttn.reap.entities.Post;
import com.ttn.reap.services.EmployeeReceivedStarService;
import com.ttn.reap.services.EmployeeService;
import com.ttn.reap.services.PostsService;
import com.ttn.reap.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/private")
public class AdminController {

    @Autowired
    PostsService postsService;

    @Autowired
    RoleService roleService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeReceivedStarService employeeReceivedStarService;

    @PostMapping("/revoke/{id}/{reason}")
    @ResponseBody
    public String revokeBadge(@PathVariable Integer id, @PathVariable String reason) {
        if (postsService.revokePost(id, reason)) {
            return "success";
        }
        return "failure";
    }

    @PostMapping("/giverole/{role}/{email}")
    public String giveRole(@PathVariable String role, @PathVariable String email) {
        roleService.giveRoleAndUpdateBadges(role, email);
        return "redirect:/private/dashboard";
    }

    @PostMapping("/takerole/{role}/{email}")
    public String takeRole(@PathVariable String role, @PathVariable String email) {
        roleService.takeRoleAndUpdateBadges(role, email);
        return "redirect:/private/dashboard";
    }

    @PostMapping("/active/{active}/{email}")
    public String setActiveStatus(@PathVariable Boolean active, @PathVariable String email) {
        employeeService.setEmployeeActiveStatus(active, email);
        return "redirect:/private/dashboard";
    }

    @PostMapping("/changegold/{gold}/{email}")
    public String changeGold(@PathVariable Integer gold, @PathVariable String email) {
        employeeService.changeGoldBadge(gold, email);
        return "redirect:/private/dashboard";
    }

    @PostMapping("/changesilver/{silver}/{email}")
    public String changeSilver(@PathVariable Integer silver, @PathVariable String email) {
        employeeService.changeSilverBadge(silver, email);
        return "redirect:/private/dashboard";
    }

    @PostMapping("/changebronze/{bronze}/{email}")
    public String changeBronze(@PathVariable Integer bronze, @PathVariable String email) {
        employeeService.changeBronzeBadge(bronze, email);
        return "redirect:/private/dashboard";
    }

    @RequestMapping("/download")
    public void downloadWallOfFame(@RequestParam("startDate") String start,
                                   @RequestParam("endDate") String end,
                                   HttpServletResponse response) throws ParseException, IOException {
        response.setContentType("application/csv");
        response.setHeader("Content-Disposition", "attachment;file=Posts.csv");
        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(start);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(end);
        List<Post> posts = postsService.getPostsByDateBetweeen(startDate, endDate);
        CSVWriter.writePostToCSV(response.getWriter(), posts);
    }

    @PostMapping("/changetotalpoints/{points}/{email}")
    public String changeTotalPoints(@PathVariable Integer points, @PathVariable String email) {
        Employee employee = employeeService.getEmployeeByEmail(email);
        employeeReceivedStarService.changeTotalPoints(points, employee);
        return "redirect:/private/dashboard";
    }
}
