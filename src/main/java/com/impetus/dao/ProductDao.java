package com.impetus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.entity.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

	@Query("SELECT p FROM Product p WHERE p.policyId = ?1")
	public List<Product> getProductByPolicyId(long policyId);

	@Query("SELECT p FROM Product p WHERE p.productId = ?1")
	public Product getProductByProductId(long productId);

	@Query("Select p from Product p where productId = ?1")
	public Product getProductById(long productId);

	@Query("Select p from CustomerPolicyDetails p where productId = ?1")
	public List<CustomerPolicyDetails> getCustomersWithProductId(long productId);

	@Query("SELECT COUNT(p.productId) FROM Product p")
	long totalProducts();

	@Query("Select p from Product p where productId =:pid")
	public List<Product> getProductByProductIdList(@Param("pid") long productId);

}
