package com.ttn.reap.controllers;

import com.ttn.reap.entities.Employee;
import com.ttn.reap.entities.EmployeeReceivedStar;
import com.ttn.reap.services.EmployeeOrderService;
import com.ttn.reap.services.EmployeeReceivedStarService;
import com.ttn.reap.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/private")
public class EmployeeOrderController {

    @Autowired
    EmployeeOrderService employeeOrderService;

    @Autowired
    EmployeeReceivedStarService employeeReceivedStarService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/redeem")
    @ResponseBody
    public String redeemPoints(@RequestParam("items") String items,
                               @RequestParam("imgUrl") String imgUrl,
                               @RequestParam("totalPrice") String totalPrice,
                               HttpSession session) {

        Employee employee = employeeService.getEmployeeByEmail((String) session.getAttribute("email"));
        EmployeeReceivedStar employeeReceivedStar = employeeReceivedStarService.getEmployeeByEmployeeId(employee);
        Integer cartPrice = Integer.parseInt(totalPrice);

        if (employeeReceivedStar.getTotalPoints() >= cartPrice) {
            String itemArr[] = items.split(" ");
            String imgArr[] = imgUrl.split(" ");
            Integer totalQuantity = itemArr.length;
            List<String> itemsList = new ArrayList<>(Arrays.asList(itemArr));
            List<String> imageUrlList = new ArrayList<>(Arrays.asList(imgArr));

            employeeReceivedStarService.placedOrderSettlement(employeeReceivedStar,cartPrice);
            employeeOrderService.enterOrderDetails(employee,itemsList,imageUrlList,cartPrice,totalQuantity);
            return "done";
        }
        else {
            return "Not enough points";
        }
    }
}
