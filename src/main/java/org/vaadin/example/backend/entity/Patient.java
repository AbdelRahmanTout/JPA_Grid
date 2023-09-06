package org.vaadin.example.backend.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Entity
@Table(name = "PATIENT")
public class Patient extends AbstractEntity{

	public String firstName;

	public String lastName;

	public Integer phoneNumber;

	public String email;

	public LocalDate date;

	public Patient() {}
	
	public Patient(Integer id,String firstName, String lastName, Integer phoneNumber, String email, LocalDate date) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.date=date;
	}

	@Column(name = "firstName", nullable = false)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "lastName", nullable = false)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "email", nullable = false)
	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = this.getFirstName() + "." + this.getLastName() + "@gmail.com";
	}
	
	public void setEmail0(String email) {
		this.email=email;
	}

	@Column(name = "phoneNumber", nullable = false)
	public Integer getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Column(name = "Date", nullable = false)
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phoneNumber="
				+ phoneNumber + "]";
	}
}
