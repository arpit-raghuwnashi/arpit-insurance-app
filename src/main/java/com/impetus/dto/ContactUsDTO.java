package com.impetus.dto;

public class ContactUsDTO {
	private String name;
	private String email;
	private long phone;
	private String message;

	public ContactUsDTO() {
	}

	public ContactUsDTO(String name, String email, long phone, String message) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "contactUs [name=" + name + ", email=" + email + ", phone=" + phone + ", message=" + message + "]";
	}

}
