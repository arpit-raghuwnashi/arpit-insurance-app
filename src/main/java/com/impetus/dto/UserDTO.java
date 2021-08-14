package com.impetus.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserDTO {
	private long userId;
	private String firstName;
	private String lastName;
	private String email;
	private long phoneNo;
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	private Date dateOfBirth;
	private String gender;
	private Date createdDate;
	private Date modifiedDate;
	private String occupation;
	private String password;
	private String aadharNo;
	private int roleId;
	private int policyCount;
	private int annualIncome;
	private String education;
	private boolean hasBp;
	private int otp;
	private boolean ifDiabetic;
	private boolean ifSmoker;
	private boolean status;

	public UserDTO() {
		super();
	}

	public UserDTO(UserDTO user) {
		super();
		this.userId = user.userId;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.email = user.email;
		this.phoneNo = user.phoneNo;
		this.dateOfBirth = user.dateOfBirth;
		this.gender = user.gender;
		this.createdDate = user.createdDate;
		this.modifiedDate = user.modifiedDate;
		this.occupation = user.occupation;
		this.password = user.password;
		this.aadharNo = user.aadharNo;
		this.roleId = user.roleId;
		this.policyCount = user.policyCount;
		this.annualIncome = user.annualIncome;
		this.education = user.education;
		this.hasBp = user.hasBp;
		this.otp = user.otp;
		this.ifDiabetic = user.ifDiabetic;
		this.ifSmoker = user.ifSmoker;
		this.status = user.status;
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

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
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

	public boolean isHasBp() {
		return hasBp;
	}

	public void setHasBp(boolean hasBp) {
		this.hasBp = hasBp;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
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
