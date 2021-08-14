package com.impetus.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.impetus.entity.CustomerPolicyDetails;

@Repository
public interface CustomerPolicyDetailsDao extends JpaRepository<CustomerPolicyDetails, Long> {

	@Query("SELECT c from CustomerPolicyDetails c where productId=?1 and userId=?2")
	public List<CustomerPolicyDetails> checkUserWithOrder(long productId, long userId);

	@Query("SELECT c from CustomerPolicyDetails c where userId=?1")
	public List<CustomerPolicyDetails> fetchUserOrders(Long userId);

	@Modifying
	@Transactional
	@Query("UPDATE CustomerPolicyDetails c SET c.status = :status where c.orderId = :orderId")
	public void updatePendingStatus(@Param("status") String status, @Param("orderId") Long orderId);

	@Modifying
	@Transactional
	@Query("UPDATE CustomerPolicyDetails c SET c.claimStatus = :status where c.orderId = :orderId")
	public void updateClaimStatus(@Param("status") String status, @Param("orderId") long orderId);

	@Modifying
	@Transactional
	@Query("UPDATE CustomerPolicyDetails c SET c.amountPaid = :amount,c.modifiedDate = :date where c.orderId = :orderId")
	public void payAmount(@Param("amount") int amount, @Param("orderId") long orderId, @Param("date") Date date);

	@Query("select c from CustomerPolicyDetails c where c.status=:status")
	public List<CustomerPolicyDetails> getPendingCustomerPolicyDetails(@Param("status") String status);
	
	@Query("SELECT COUNT(c.orderId) FROM CustomerPolicyDetails c where c.status=:status")
	public Long getPendingCustomerPolicyCount(@Param("status") String status);

	@Query("SELECT c FROM CustomerPolicyDetails c WHERE c.userId=:uid")
	public List<CustomerPolicyDetails> getPolicyDetailsByUserId(@Param("uid") long userId);

	@Query("SELECT c FROM CustomerPolicyDetails c WHERE c.orderId=:oid")
	public List<CustomerPolicyDetails> fetchUserOrderByOrderId(@Param("oid") long orderId);

	@Query("SELECT COUNT(c.orderId) FROM CustomerPolicyDetails c where c.status=:status")
	public Long getTotalPolices(@Param("status") String status);
	
	@Query("SELECT c.userId from CustomerPolicyDetails c where c.orderId=:orderId")
	public Long getUserIdByOrderId(@Param("orderId") Long orderId);
	
	

}
