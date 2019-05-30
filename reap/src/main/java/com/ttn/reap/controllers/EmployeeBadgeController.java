package com.ttn.reap.controllers;

import com.ttn.reap.entities.Employee;
import com.ttn.reap.entities.EmployeeOrder;
import com.ttn.reap.entities.EmployeeReceivedStar;
import com.ttn.reap.entities.Post;
import com.ttn.reap.services.EmployeeOrderService;
import com.ttn.reap.services.EmployeeReceivedStarService;
import com.ttn.reap.services.EmployeeService;
import com.ttn.reap.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.ttn.reap.constants.Constants.SENDER_POSTS;

@Controller
@RequestMapping("/private")
public class EmployeeBadgeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeReceivedStarService employeeReceivedStarService;

    @Autowired
    PostsService postsService;

    @Autowired
    EmployeeOrderService employeeOrderService;

    @RequestMapping("/badges/{email}")
    public ModelAndView showEmployeeBadgesPage(@PathVariable("email") String email, HttpSession session,
                                               HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
        ModelAndView modelAndView = new ModelAndView("badges");
        Employee employee = employeeService.getEmployeeByEmail(email);
        Employee loggedInEmployee = employeeService.getEmployeeByEmail((String) session.getAttribute("email"));
        List<EmployeeOrder> orderList = employeeOrderService.getOrderDetails(loggedInEmployee);
        EmployeeReceivedStar employeeReceivedStar = employeeReceivedStarService.getEmployeeByEmployeeId(employee);
        List<Post> employeePosts = postsService.getUserPosts(employee, employee);
        List<Post> receiverPosts = postsService.getReceiverPosts(employee);
        List<Post> senderPosts = postsService.getSenderPosts(employee);
        modelAndView.addObject("loggedinemployee", loggedInEmployee);
        modelAndView.addObject("orderlist", orderList);
        modelAndView.addObject("receiverPosts", receiverPosts);
        modelAndView.addObject(SENDER_POSTS, senderPosts);
        modelAndView.addObject("employeebadges", employeeReceivedStar);
        modelAndView.addObject("employeePosts", employeePosts);
        return modelAndView;
    }
}
