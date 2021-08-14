package com.impetus.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.impetus.dto.DependentDTO;

@Entity
@Table(name = "dependent")
public class Dependent {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long depenedentId;
	private String firstName;
	private String lastName;
	private String middleName;
	private String email;
	private long phone;
	private int age;
	private String gender;
	private Long userId;
	private Long orderId;

	public Dependent() {
		super();
	}

	public Dependent(DependentDTO dependent) {
		super();
		this.depenedentId = dependent.getDepenedentId();
		this.firstName = dependent.getFirstName();
		this.lastName = dependent.getLastName();
		this.middleName = dependent.getMiddleName();
		this.age = dependent.getAge();
		this.gender = dependent.getGender();
		this.userId = dependent.getUserId();
		this.orderId = dependent.getOrderId();
		this.email = dependent.getEmail();
		this.phone = dependent.getPhone();
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

	public long getDepenedentId() {
		return depenedentId;
	}

	public void setDepenedentId(long depenedentId) {
		this.depenedentId = depenedentId;
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

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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
		return "Dependent [depenedentId=" + depenedentId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", middleName=" + middleName + ", email=" + email + ", phone=" + phone + ", age=" + age + ", gender="
				+ gender + ", userId=" + userId + ", orderId=" + orderId + "]";
	}

}
