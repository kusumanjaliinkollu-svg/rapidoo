package com.alpha.RapidoCustomerService.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
private String name;
private long mobile;
private String email;
private String gender;
private String otp;
public Customer() {
	super();
}
public Customer(String name, long mobile, String email, String gender, String otp) {
	super();
	this.name = name;
	this.mobile = mobile;
	this.email = email;
	this.gender = gender;
	this.otp = otp;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public long getMobile() {
	return mobile;
}
public void setMobile(long mobile) {
	this.mobile = mobile;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getOtp() {
	return otp;
}
public void setOtp(String otp) {
	this.otp = otp;
}
@Override
public String toString() {
	return "Customer [id=" + id + ", name=" + name + ", mobile=" + mobile + ", email=" + email + ", gender=" + gender
			+ ", otp=" + otp + "]";
}


}
