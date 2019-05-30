package com.ttn.reap.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

    @RequestMapping("/logout")
    public String logoutUser(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("loggedOut", "Logged Out Successfully!!");
        return "redirect:/login";
    }

   /* @RequestMapping("/logoutcheck")
    public static Integer logoutCheck(HttpSession session){
        if(session.getAttribute("email")==null){
            return 0;
        }
        return 1;
    }*/
}
