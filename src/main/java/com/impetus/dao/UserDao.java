package com.impetus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.impetus.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.email = ?1")
	User findUserByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.email = ?1 and u.password = ?2")
	User login(String email, String password);

	@Query("SELECT u FROM User u WHERE u.userId = ?1")
	User getUserById(Long userId);

	@Query("SELECT u FROM User u where u.email=:email")
	User findByEmail(@Param("email") String email);

	@Query("SELECT u FROM User u WHERE u.email=:email and u.roleId=:rid")
	public User findUnderwriterByEmail(@Param("email") String email, @Param("rid") long roleId);

	@Query("SELECT u FROM User u WHERE u.email=:email and u.roleId=:rid")
	public User findAdminByEmail(@Param("email") String email, @Param("rid") long roleId);

	@Query("SELECT u FROM User u where u.roleId=:rid")
	public List<User> findByRoleId(@Param("rid") long roleId);

	@Query("SELECT u FROM User u WHERE u.userId=:uid and u.roleId=:rid")
	public User findUnderwriterById(@Param("uid") long userId, @Param("rid") long roleId);

	@Query("SELECT COUNT(u.userId) FROM User u where u.roleId=:rid")
	long totalUsers(@Param("rid") long roleId);

	@Query("SELECT COUNT(u.userId) FROM User u where u.roleId=:rid and u.status=:s")
	long totalUnderwriters(@Param("rid") long roleId, @Param("s") boolean status);

	@Query("SELECT COUNT(u.userId) FROM User u where u.roleId=:rid and u.policyCount>0")
	long totalCustomers(@Param("rid") long roleId);

	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.status = :status where u.userId = :userId")
	public void deactiveUnderwriterStatus(@Param("status") Boolean status, @Param("userId") Long userId);

	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.password = :password where u.email = :email")
	public void resetPassword(@Param("email") String email, @Param("password") String password);
	
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.policyCount = :policyCount where u.userId = :userId")
	public void updatePolicyCount(@Param("policyCount") int policyCount, @Param("userId") long userId);

	@Query("SELECT u FROM User u where u.roleId=:rid and u.status=:s")
	public List<User> getAllUnderwriter(@Param("rid") Long roleId, @Param("s") Boolean status);
	
}
