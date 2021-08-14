package com.impetus.dto;

public class NomineeDTO {
	private Long nomineeID;
	private String firstName;
	private String lastName;
	private String email;
	private Long phone;
	private Long userId;
	private Long orderId;

	public NomineeDTO() {
		super();
	}

	public NomineeDTO(NomineeDTO nominee) {
		super();
		this.nomineeID = nominee.nomineeID;
		this.firstName = nominee.firstName;
		this.lastName = nominee.lastName;
		this.email = nominee.email;
		this.phone = nominee.phone;
		this.userId = nominee.userId;
		this.orderId = nominee.orderId;
	}

	public Long getNomineeID() {
		return nomineeID;
	}

	public void setNomineeID(Long nomineeID) {
		this.nomineeID = nomineeID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "Nominee [nomineeID=" + nomineeID + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", phone=" + phone + ", userId=" + userId + ", orderId=" + orderId + "]";
	}

}
