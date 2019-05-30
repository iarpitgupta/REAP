package com.ttn.reap.repositories;

import com.ttn.reap.entities.Employee;
import com.ttn.reap.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepositories extends CrudRepository<Post, Integer> {

    List<Post> findAll(Sort sort);

    List<Post> findAllByReceiver(Employee receiver);

    List<Post> findAllBySender(Employee sender);

    List<Post> findAllByReceiverOrSender(Employee receiver, Employee sender);

    List<Post> findAllByDateOfCreationBetweenAndIsActive(Date start, Date end, Boolean active);

    /*Page<Post> findAllByIsActive(Pageable pageable, Boolean active);

    Page<Post> findAllBy(Pageable pageable);

    Integer countByIsActive(Boolean active);*/

}
