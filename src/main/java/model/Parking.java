package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Parking {

	private int id;
	private int spotNumber;
	private LocalDateTime entryTime;
	private BigDecimal hourlyRate;
	private boolean occupied;
	private Vehicle vehicle;
	
	public Parking() {
		
	}
	
	public Parking(int id) {
		this.id=id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getSpotNumber() {
		return spotNumber;
	}
	
	public void setSpotNumber(int spotNumber) {
		this.spotNumber = spotNumber;
	}
	
	public LocalDateTime getEntryTime() {
		return entryTime;
	}
	
	public void setEntryTime(LocalDateTime entryTime) {
		this.entryTime = entryTime;
	}
	
	public BigDecimal getHourlyRate() {
		return hourlyRate;
	}
	
	public void setHourlyRate(BigDecimal hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	public boolean isOccupied() {
		return occupied;
	}
	
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
}
