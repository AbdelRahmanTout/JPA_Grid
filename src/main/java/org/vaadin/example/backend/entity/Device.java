package org.vaadin.example.backend.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "DEVICE")
public class Device extends AbstractEntity{

	public String serialNumber;

	public String deviceName;

	public String assosId;
	
	public LocalDate date;

	public Device() {}

	public Device(Integer id,String serialNumber, String deviceName, String assosId, LocalDate date) {
		super();
		this.serialNumber = serialNumber;
		this.deviceName = deviceName;
		this.assosId = assosId;
		this.date=date;
	}

	@Column(name = "serialNumber", nullable = false)
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Column(name = "deviceName", nullable = false)
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@Column(name = "assosId", nullable = false)
	public String getAssosId() {
		return assosId;
	}

	public void setAssosId(String assosId) {
		this.assosId = assosId;
	}

	@Column(name="Date", nullable = false)
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Device [serialNumber=" + serialNumber + ", deviceName=" + deviceName + ", assosId=" + assosId + "]";
	}
}