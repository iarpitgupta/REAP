package com.ttn.reap.controllers;

import com.ttn.reap.services.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public String loginPage(HttpSession session, HttpServletResponse response) {
        logger.info("On Logging Page");
        response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
        String email = (String) session.getAttribute("email");
        if (email != null){
            return "redirect:/private/dashboard";
        }
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam Map<String,String> loginCredentials, HttpSession session){
        ModelAndView modelAndView;
        if (loginService.checkInactive(loginCredentials.get("email"), loginCredentials.get("password"))){
            logger.info(loginCredentials.get("email") + " tried to login but is inactive");
            modelAndView = new ModelAndView("login");
            modelAndView.addObject("inactive", "Your account is currently inactive.");
            return modelAndView;
        }
        else if (loginService.authenticate(loginCredentials.get("email"), loginCredentials.get("password"))) {
            logger.info(loginCredentials.get("email") + " loggedin.");
            modelAndView = new ModelAndView("redirect:/private/dashboard");
            session.setAttribute("email",loginCredentials.get("email"));
            return modelAndView;
        } else {
            modelAndView = new ModelAndView("login");
            logger.info(loginCredentials.get("email") + " tried to login but failed");
            modelAndView.addObject("badCredentials","Your email/password did not match!!");
            return modelAndView;
        }
    }

}
