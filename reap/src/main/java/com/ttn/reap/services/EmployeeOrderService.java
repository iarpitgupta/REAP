package com.ttn.reap.services;

import com.ttn.reap.entities.Employee;
import com.ttn.reap.entities.EmployeeOrder;
import com.ttn.reap.repositories.EmployeeOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmployeeOrderService {

    @Autowired
    EmployeeOrderRepository employeeOrderRepository;

    public void enterOrderDetails(Employee employee, List<String> itemsList, List<String> imageUrlList, Integer cartPrice, Integer totalQuantity) {
        EmployeeOrder employeeOrder = new EmployeeOrder();
        employeeOrder.setEmployee(employee);
        employeeOrder.setDateOfOrder(new Date());
        employeeOrder.getItems().addAll(itemsList);
        employeeOrder.getImageUrl().addAll(imageUrlList);
        employeeOrder.setTotalPrice(cartPrice);
        employeeOrder.setQuantity(totalQuantity);
        employeeOrderRepository.save(employeeOrder);
    }

    public List<EmployeeOrder> getOrderDetails(Employee employee){
        return employeeOrderRepository.findAllByEmployee(new Sort(Sort.Direction.DESC,"dateOfOrder"),employee);
    }
}
