package com.impetus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.impetus.entity.Policy;

@Repository
public interface PolicyDao extends JpaRepository<Policy, Long>{

}
