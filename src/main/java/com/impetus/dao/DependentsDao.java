package com.impetus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.impetus.entity.Dependent;

@Repository
public interface DependentsDao extends JpaRepository<Dependent, Long> {

	@Query("SELECT d FROM Dependent d WHERE d.orderId=:oid")
	public List<Dependent> getDependentsByOrderId(@Param("oid") long orderId);

}
