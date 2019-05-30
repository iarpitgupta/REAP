package com.ttn.reap.controllers;

import com.ttn.reap.entities.Employee;
import com.ttn.reap.entities.EmployeeReceivedStar;
import com.ttn.reap.services.EmployeeReceivedStarService;
import com.ttn.reap.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/private")
public class ItemController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeReceivedStarService employeeReceivedStarService;

    @GetMapping("/itemspage")
    public ModelAndView itemsPage(HttpSession session, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
        ModelAndView modelAndView = new ModelAndView("item");
        Employee employee = employeeService.getEmployeeByEmail((String) session.getAttribute("email"));
        EmployeeReceivedStar employeeReceivedStar = employeeReceivedStarService.getEmployeeByEmployeeId(employee);
        modelAndView.addObject("employee", employee);
        modelAndView.addObject("employeebadges", employeeReceivedStar);
        return modelAndView;
    }
}
