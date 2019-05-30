package com.ttn.reap.controllers;


import com.ttn.reap.entities.Employee;
import com.ttn.reap.services.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class SignUpController {

    @Autowired
    SignupService signupService;

    @GetMapping("/signup")
    public String signUp(Employee employee) {
        return "signup";
    }

    @PostMapping(value = "/signup")
    public ModelAndView signup(@Valid Employee employee, BindingResult bindingResult, @RequestParam("profilepic") MultipartFile multipartFile) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()){
            modelAndView.setViewName("signup");
            return modelAndView;
        }

        if (signupService.saveEmployee(employee, multipartFile)) {
            modelAndView.setViewName("login");
            modelAndView.addObject("signupsuccessful","Signed up Successfully");
            return modelAndView;
        }
        modelAndView.addObject("emailexists","This email already exists.");
        modelAndView.setViewName("signup");
        return modelAndView;
    }
}
