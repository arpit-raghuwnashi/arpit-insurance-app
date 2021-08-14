package com.impetus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.impetus.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
