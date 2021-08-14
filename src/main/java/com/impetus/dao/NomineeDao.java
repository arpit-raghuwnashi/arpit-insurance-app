package com.impetus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.impetus.entity.Nominee;

@Repository
public interface NomineeDao extends JpaRepository<Nominee, Long> {

	@Query("SELECT n FROM Nominee n WHERE n.userId = ?1")
	Nominee findNomineeByUserId(Long userId);

	@Query("SELECT n FROM Nominee n WHERE n.orderId=:oid")
	public List<Nominee> getNomineesByOrderId(@Param("oid") long orderId);
	
	@Query("SELECT n FROM Nominee n WHERE n.orderId=:oid")
	public Nominee getOneNomineesByOrderId(@Param("oid") long orderId);
	
	@Query("SELECT n FROM Nominee n WHERE n.nomineeID=:nomineeID")
	public Nominee getOneNomineesByNomineeId(@Param("nomineeID") long nomineeID);

}
