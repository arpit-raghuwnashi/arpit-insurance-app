package com.impetus.dto;

public class PendingPolicyStatusResponseDTO {

	private Long orderId;

	private String status;

	private String message;

	public PendingPolicyStatusResponseDTO() {
	}

	public PendingPolicyStatusResponseDTO(Long orderId, String status, String message) {
		this.orderId = orderId;
		this.status = status;
		this.message = message;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "PendingPolicyStatusResponse [orderId=" + orderId + ", status=" + status + ", message=" + message + "]";
	}

}
