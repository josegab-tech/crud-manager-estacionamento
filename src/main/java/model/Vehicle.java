package model;

public class Vehicle {
	
	private int id;
	private String plate;
	private String model;
	private int manufactureYear;
	private String category;
	private User user;
	
	public Vehicle() {
		
	}
	
	public Vehicle( int id) { 
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getPlate() {
		return plate;
	}
	
	public void setPlate(String plate) {
		this.plate = plate;
	}
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	public int getManufactureYear() {
		return manufactureYear;
	}
	
	public void setManufactureYear(int manufactureYear) {
		this.manufactureYear = manufactureYear;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
