package com.ttn.reap.repositories;

import com.ttn.reap.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {

    Role findByRole(String role);

    List<Role> findAll();
}
