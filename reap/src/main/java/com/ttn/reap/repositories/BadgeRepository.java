package com.ttn.reap.repositories;

import com.ttn.reap.entities.Badges;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends CrudRepository<Badges, Integer> {
    Badges findByBadgeName(String badgeName);
}
