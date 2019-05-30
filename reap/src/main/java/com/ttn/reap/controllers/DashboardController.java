package com.ttn.reap.controllers;

import com.ttn.reap.email.MailSender;
import com.ttn.reap.entities.Employee;
import com.ttn.reap.entities.EmployeeReceivedStar;
import com.ttn.reap.entities.Post;
import com.ttn.reap.entities.Role;
import com.ttn.reap.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/private")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @Autowired
    PostsService postsService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeReceivedStarService employeeReceivedStarService;

    @Autowired
    RoleService roleService;

    @Autowired
    MailSender mailSender;

    Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @RequestMapping("/dashboard")
    public ModelAndView dashboard(HttpSession session, Model model, HttpServletResponse response) {
        logger.info("Inside Dashboard");
        response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
        ModelAndView modelAndView = new ModelAndView("dashboard");

        List<Post> posts = postsService.getAllPosts()
                .stream().filter(e -> e.getIsActive()).collect(Collectors.toList());

        String email = (String) session.getAttribute("email");

        Employee employee = employeeService.getEmployeeByEmail(email);

        EmployeeReceivedStar employeeStar = employeeReceivedStarService.getEmployeeByEmployeeId(employee);

        List<EmployeeReceivedStar> employeeReceivedStarList = employeeReceivedStarService.getAllEmployeeReceivedStars()
                .stream().filter(e -> !e.getEmployee().getEmail()
                        .equals(email)).collect(Collectors.toList());

        List<EmployeeReceivedStar> top5EmployeesWithMaxPointsList = employeeReceivedStarService.getTopEmployeesWithMaxPoints();

        List<Role> roles = roleService.findAllRoles();

        modelAndView.addObject("allroles", roles);
        modelAndView.addObject("postslist", posts);
        modelAndView.addObject("top5employeeswithmaxpoints", top5EmployeesWithMaxPointsList);
        modelAndView.addObject("manageemployeeslist", employeeReceivedStarList);
        modelAndView.addObject("employeestar", employeeStar);
        modelAndView.addObject("employee", employee);
        return modelAndView;
    }

   /* @PostMapping("/getposts")
    @ResponseBody
    public List<Post> getPosts(Optional<Integer> pageNumber, Optional<Integer> pageSize, Model model, HttpSession session) {
        model.addAttribute("postslist", postsService.findAll(pageNumber, pageSize));
        model.addAttribute("employee", employeeService.getEmployeeByEmail((String) session.getAttribute("email")));

        return postsService.findAll(pageNumber,pageSize);
    }*/

    @RequestMapping("/getemployeesemail")
    @ResponseBody
    public List<Employee> getAllEmployeesEmail(HttpSession session) {
        logger.info("Getting all employees");
        return dashboardService.findAllEmployees().stream().
                filter(e -> !e.getEmail().equals(session.getAttribute("email"))).collect(Collectors.toList());
    }


    @GetMapping("/searchRecognitionByDate/{start}/{end}")
    public String getUserRecodByName(@PathVariable("start") String start, @PathVariable("end") String end,
                                     Model model, HttpSession session) throws ParseException {
        logger.info("Searching recognition by date");
        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(start);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(end);
        List<Post> posts = postsService.getPostsByDateBetweeen(startDate, endDate);
        String email = (String) session.getAttribute("email");
        Employee employee = employeeService.getEmployeeByEmail(email);
        model.addAttribute("postslist", posts);
        model.addAttribute("employee", employee);
        return "dashboard::wallOfFame";
    }

}

