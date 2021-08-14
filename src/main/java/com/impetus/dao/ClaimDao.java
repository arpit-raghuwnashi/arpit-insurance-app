package com.impetus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.impetus.entity.Claim;

@Repository
public interface ClaimDao extends JpaRepository<Claim, Long> {

	@Query("SELECT c from Claim c where c.claimId=:cid")
	public Claim getClaimRecord(@Param("cid") long claimId);

	@Query("SELECT COUNT(c.claimStatus) FROM Claim c where c.claimStatus=:status")
	long totalSuccessfulClaims(@Param("status") String status);
}
