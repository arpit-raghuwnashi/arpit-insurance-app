package com.impetus.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.impetus.dto.UserDTO;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	private String firstName;
	private String lastName;
	@Column(unique = true)
	@NotNull
	private String email;
	private long phoneNo;
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	private Date dateOfBirth;
	private String gender;
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	private Date createdDate;
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	private Date modifiedDate;
	private String occupation;
	private String password;
	private String aadharNo;
	private long roleId;
	private int policyCount;
	private int annualIncome;
	private String education;
	private boolean ifDiabetic;
	private boolean ifSmoker;
	private boolean hasBp;
	private boolean status;

	public User() {
	}

	public User(UserDTO user) {
		this.userId = user.getUserId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.phoneNo = user.getPhoneNo();
		this.dateOfBirth = user.getDateOfBirth();
		this.gender = user.getGender();
		this.createdDate = user.getCreatedDate();
		this.modifiedDate = user.getModifiedDate();
		this.occupation = user.getOccupation();
		this.password = user.getPassword();
		this.aadharNo = user.getAadharNo();
		this.roleId = user.getRoleId();
		this.policyCount = user.getPolicyCount();
		this.annualIncome = user.getAnnualIncome();
		this.education = user.getEducation();
		this.ifDiabetic = user.isIfDiabetic();
		this.ifSmoker = user.isIfSmoker();
		this.hasBp = user.isHasBp();
		this.status = user.isStatus();
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public int getPolicyCount() {
		return policyCount;
	}

	public void setPolicyCount(int policyCount) {
		this.policyCount = policyCount;
	}

	public int getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(int annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public boolean isIfDiabetic() {
		return ifDiabetic;
	}

	public void setIfDiabetic(boolean ifDiabetic) {
		this.ifDiabetic = ifDiabetic;
	}

	public boolean isIfSmoker() {
		return ifSmoker;
	}

	public void setIfSmoker(boolean ifSmoker) {
		this.ifSmoker = ifSmoker;
	}

	public boolean isHasBp() {
		return hasBp;
	}

	public void setHasBp(boolean hasBp) {
		this.hasBp = hasBp;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", phoneNo=" + phoneNo + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", createdDate="
				+ createdDate + ", modifiedDate=" + modifiedDate + ", occupation=" + occupation + ", password="
				+ password + ", aadharNo=" + aadharNo + ", roleId=" + roleId + ", policyCount=" + policyCount
				+ ", annualIncome=" + annualIncome + ", education=" + education + ", ifDiabetic=" + ifDiabetic
				+ ", ifSmoker=" + ifSmoker + ", hasBp=" + hasBp + ", status=" + status + "]";
	}

}
