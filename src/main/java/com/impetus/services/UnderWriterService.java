package com.impetus.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.impetus.dto.PendingPolicyStatusResponseDTO;
import com.impetus.dto.UnderWriterDTO;
import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.entity.User;

@Service
public interface UnderWriterService {

	public User updateUnderWriter(User user);
	
	public User getUnderWriter(String email);
	
	public List<CustomerPolicyDetails> getPendingCustomerPolicyDetails();
	
	public List<CustomerPolicyDetails> getDeclineCuustomerPolicyDetails();
	
    public void deactiveUnderwriter(Long userid);
	
	public void updatePendingStatus(String status,Long orderId);
	
	public Long findUserRole(String email);
	
	public UnderWriterDTO getTotalCount();
	
	public void sendPolicyStatusChangeMail(PendingPolicyStatusResponseDTO statusResponse);
	
		
}
