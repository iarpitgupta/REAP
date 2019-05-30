package com.ttn.reap.services;

import com.ttn.reap.entities.Badges;
import com.ttn.reap.repositories.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BadgeService {

    @Autowired
    BadgeRepository badgeRepository;

    public Badges getBadgeByName(String badgeName) {
        return badgeRepository.findByBadgeName(badgeName);
    }
}
