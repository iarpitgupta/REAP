package com.ttn.reap.services;

import com.ttn.reap.email.MailSender;
import com.ttn.reap.entities.Badges;
import com.ttn.reap.entities.Employee;
import com.ttn.reap.entities.EmployeeReceivedStar;
import com.ttn.reap.entities.Post;
import com.ttn.reap.repositories.PostRepositories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostsService {

    @Autowired
    PostRepositories postRepositories;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeReceivedStarService employeeReceivedStarService;

    @Autowired
    MailSender mailSender;

    @Autowired
    BadgeService badgeService;

//    Logger log = LoggerFactory.getLogger(PostsService.class);


    public List<Post> getAllPosts() {
        return postRepositories.findAll(new Sort(Sort.Direction.DESC, "dateOfCreation"));
    }

    /*public List<Post> findAll(Optional<Integer> pageNumber, Optional<Integer> pageSize) {
        Page<Post> page = postRepositories.findAllBy(PageRequest.of(pageNumber.get(), pageSize.get()));


        log.info("--------Page Size ====  " + pageSize.get());
        log.info("--------Number Of  Elements ======  " + page.getNumberOfElements());
        log.info("--------Total Elements ======  " + page.getTotalElements());
        System.out.println();
        System.out.println();

        log.info("Page Size  =======  "+page.getPageable().getPageSize());
        return page.getNumberOfElements() > 0 ? page.getContent() :
                postRepositories.findAllBy(PageRequest.of(pageNumber.get(), (int) page.getTotalElements())).getContent();
        if (pageSize.get() > postRepositories.countByIsActive(true)) {
            return postRepositories.findAllByIsActive(PageRequest.of(pageNumber.get(), postRepositories.countByIsActive(true)), true).getContent();
        }
        return page.getContent();
    }*/

    public List<Post> getUserPosts(Employee receiver, Employee sender) {
        return postRepositories.findAllByReceiverOrSender(receiver, sender)
                .stream().filter(e -> e.getIsActive()).collect(Collectors.toList());
    }

    public List<Post> getReceiverPosts(Employee receiver) {
        return postRepositories.findAllByReceiver(receiver)
                .stream().filter(e -> e.getIsActive()).collect(Collectors.toList());
    }

    public List<Post> getSenderPosts(Employee sender) {
        return postRepositories.findAllBySender(sender)
                .stream().filter(e -> e.getIsActive()).collect(Collectors.toList());
    }

    public List<Post> getPostsByDateBetweeen(Date start, Date end) {
        return postRepositories.findAllByDateOfCreationBetweenAndIsActive(start, end, true);
    }

    public boolean savePost(String senderEmail, String receiverEmail, String badge, String comment) {
        Employee senderEmployee = employeeService.getEmployeeByEmail(senderEmail);
        Employee receiverEmployee = employeeService.getEmployeeByEmail(receiverEmail);
        EmployeeReceivedStar employeeReceivedStar = employeeReceivedStarService.getEmployeeByEmployeeId(receiverEmployee);
        if (receiverEmployee == null) {
            return false;
        }
        int flag = 0;
        switch (badge) {
            case "Gold":
                if (senderEmployee.getGold() > 0) {
                    flag = 1;
                    Badges goldBadge = badgeService.getBadgeByName("Gold");
                    senderEmployee.setGold(senderEmployee.getGold() - 1);
                    employeeReceivedStar.setGold(employeeReceivedStar.getGold() + 1);
                    employeeReceivedStar.setTotalPoints(employeeReceivedStar.getTotalPoints() + goldBadge.getValue());
                }
                break;

            case "Silver":
                if (senderEmployee.getSilver() > 0) {
                    flag = 1;
                    Badges silverBadge = badgeService.getBadgeByName("Silver");
                    senderEmployee.setSilver(senderEmployee.getSilver() - 1);
                    employeeReceivedStar.setSilver(employeeReceivedStar.getSilver() + 1);
                    employeeReceivedStar.setTotalPoints(employeeReceivedStar.getTotalPoints() + silverBadge.getValue());
                }
                break;
            case "Bronze":
                if (senderEmployee.getBronze() > 0) {
                    flag = 1;
                    Badges bronzeBadge = badgeService.getBadgeByName("Bronze");
                    senderEmployee.setBronze(senderEmployee.getBronze() - 1);
                    employeeReceivedStar.setBronze(employeeReceivedStar.getBronze() + 1);
                    employeeReceivedStar.setTotalPoints(employeeReceivedStar.getTotalPoints() + bronzeBadge.getValue());
                }
                break;
        }
        if (flag == 1) {
            employeeReceivedStarService.saveEmployee(employeeReceivedStar);
            employeeService.saveEmployee(senderEmployee);
            Post post = new Post();
            post.setSender(senderEmployee);
            post.setReceiver(receiverEmployee);
            post.setDateOfCreation(new Date());
            post.setBadge(badge);
            post.setIsActive(true);
            post.setComment(comment);
            postRepositories.save(post);
            return true;
        }
        return false;
    }

    public boolean revokePost(Integer postId, String reason) {
        Post post = postRepositories.findById(postId).get();
        Employee senderEmployee = post.getSender();
        Employee receiver = post.getReceiver();
        EmployeeReceivedStar employeeReceivedStar = employeeReceivedStarService.getEmployeeByEmployeeId(receiver);
        String badge = post.getBadge();
        switch (badge) {
            case "Gold":
                if (employeeReceivedStar.getGold() < 1 && employeeReceivedStar.getTotalPoints() < 30) {
                    return false;
                }
                Badges goldBadge = badgeService.getBadgeByName("Gold");
                senderEmployee.setGold(senderEmployee.getGold() + 1);
                employeeReceivedStar.setGold(employeeReceivedStar.getGold() - 1);
                employeeReceivedStar.setTotalPoints(employeeReceivedStar.getTotalPoints() - goldBadge.getValue());
                break;
            case "Silver":
                if (employeeReceivedStar.getSilver() < 1 && employeeReceivedStar.getTotalPoints() < 20) {
                    return false;
                }
                Badges silverBadge = badgeService.getBadgeByName("Silver");
                senderEmployee.setSilver(senderEmployee.getSilver() + 1);
                employeeReceivedStar.setSilver(employeeReceivedStar.getSilver() - 1);
                employeeReceivedStar.setTotalPoints(employeeReceivedStar.getTotalPoints() - silverBadge.getValue());
                break;
            case "Bronze":
                if (employeeReceivedStar.getBronze() < 1 && employeeReceivedStar.getTotalPoints() < 10) {
                    return false;
                }
                Badges bronzeBadge = badgeService.getBadgeByName("Bronze");
                senderEmployee.setBronze(senderEmployee.getBronze() + 1);
                employeeReceivedStar.setBronze(employeeReceivedStar.getBronze() - 1);
                employeeReceivedStar.setTotalPoints(employeeReceivedStar.getTotalPoints() - bronzeBadge.getValue());
                break;
        }
        employeeService.saveEmployee(senderEmployee);
        employeeReceivedStarService.saveEmployee(employeeReceivedStar);
        post.setIsActive(false);
        post.setRevokeReason(reason);
        postRepositories.save(post);
        System.out.println(reason);
        String senderText = "Badge given by you to " + receiver.getEmail() + " is revoked by Admin for reason: " + reason;
        mailSender.sendMail(senderEmployee.getEmail(), "Recognition Revoked", senderText);
        String receiverText = "Badge received by you from " + senderEmployee.getEmail() + " is revoked by Admin for reason: " + reason;
        mailSender.sendMail(receiver.getEmail(), "Recognition Revoked", receiverText);
        return true;
    }


}
