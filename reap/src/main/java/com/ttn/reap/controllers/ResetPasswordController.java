package com.ttn.reap.controllers;

import com.ttn.reap.email.MailSender;
import com.ttn.reap.entities.Employee;
import com.ttn.reap.services.EmailService;
import com.ttn.reap.services.EmployeeService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.UUID;

@Controller
public class ResetPasswordController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmailService emailService;

    @Autowired
    MailSender mailSender;

    @GetMapping("/recoveremail")
    public ModelAndView recoverEmailForm() {
        ModelAndView modelAndView = new ModelAndView("recoveremail");
        return modelAndView;
    }


    @PostMapping("/recoveremail")
    public ModelAndView processRecoverEmailForm(@RequestParam("email") String email) {

        ModelAndView modelAndView = new ModelAndView("recoveremail");
        Employee employee = employeeService.getEmployeeByEmail(email);

        if (employee == null) {
            modelAndView.addObject("errorMessage", "This e-mail id does not exist!");
            return modelAndView;
        } else {
            employee.setResetToken(UUID.randomUUID().toString());
            employeeService.saveEmployee(employee);

            String url = "http://localhost:8080";
            String receiver = employee.getEmail();
            String subject = "Password Reset";
            String text = "Click the link below to reset your password \n" +
                    url + "/resetpassword?token=" + employee.getResetToken();
            mailSender.sendMail(receiver, subject, text);
            modelAndView.addObject("successMessage",
                    "Password reset link is sent to " + employee.getEmail());
        }
        return modelAndView;
    }

    @GetMapping("/resetpassword")
    public ModelAndView resetPasswordForm(@RequestParam("token") String token) {
        ModelAndView modelAndView = new ModelAndView("resetpassword");
        Employee employee = employeeService.getEmployeeByToken(token);

        if (employee == null) {
            modelAndView.addObject("invalidLink",
                    "This is an invalid password reset link!!");
        } else {
            modelAndView.addObject("resetToken", token);
        }

        return modelAndView;
    }

    @PostMapping("/resetpassword")
    public String processResetPasswordForm(@RequestParam Map<String, String> requestParams, RedirectAttributes redirectAttributes) {
        Employee employee = employeeService.getEmployeeByToken(requestParams.get("resetToken"));
        if(employee == null){
            redirectAttributes.addFlashAttribute("usedInvalidLink","You're using an invalid password link!!");
            return "redirect:/resetpassword";
        }
        String salt = BCrypt.gensalt();
        employee.setPassword(BCrypt.hashpw(requestParams.get("password"),salt));
        employee.setResetToken(null);
        employeeService.saveEmployee(employee);
        redirectAttributes.addFlashAttribute("successMessage","Password reset successfully!!");
        return "redirect:/login";
    }
}
