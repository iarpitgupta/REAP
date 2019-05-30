package com.ttn.reap.controllers;

import com.ttn.reap.email.MailSender;
import com.ttn.reap.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/private")
public class PostController {

    @Autowired
    PostsService postsService;

    @Autowired
    MailSender mailSender;

    @PostMapping("/createpost")
    public String processPostForm(@RequestParam Map<String, String> postData, HttpSession session
            , RedirectAttributes redirectAttributes) {
        String receiverEmail = postData.get("email");
        String badge = postData.get("badge");
        String comment = postData.get("comment");
        String senderEmail = (String) session.getAttribute("email");
        if(receiverEmail.equals("")){
            redirectAttributes.addFlashAttribute("error", "Please enter e-mail id first!!");
            return "redirect:/private/dashboard";
        }
        else if(senderEmail.equals(receiverEmail)){
            redirectAttributes.addFlashAttribute("error", "Sorry, You cannot recognize yourself!!");
            return "redirect:/private/dashboard";
        }
        else if (postsService.savePost(senderEmail, receiverEmail, badge, comment)) {
            redirectAttributes.addFlashAttribute("success", "Badge given successfully!!");
            String subject = "Received a badge";
            String text = "Congratulations you have received a " + badge + " from " + senderEmail + " for " + comment;
            mailSender.sendMail(receiverEmail, subject, text);
            return "redirect:/private/dashboard";
        }
        redirectAttributes.addFlashAttribute("error", "Email Id entered is incorrect!!");
        return "redirect:/private/dashboard";


    }

}
