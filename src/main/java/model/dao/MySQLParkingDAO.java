package model.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Parking;
import model.Vehicle;

public class MySQLParkingDAO implements ParkingDAO {

	private static final DateTimeFormatter SQL_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Override
	public boolean save(Parking parking) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO parkings VALUES "
				+ " (DEFAULT, ?, ?, ?, ?, ?);";
		
		db.prepareStatement(sqlInsert);
		db.setInt(1, parking.getSpotNumber());
		
		if (parking.getEntryTime() != null) {
			db.setString(2, parking.getEntryTime().format(SQL_FORMATTER));
		} else {
			db.setString(2, null);
		}
		
		db.setString(3, parking.getHourlyRate().toString());
		db.setInt(4, parking.isOccupied() ? 1 : 0);
		
		if (parking.getVehicle() != null && parking.getVehicle().getId() > 0) {
			db.setInt(5, parking.getVehicle().getId());
		} else {
			db.setString(5, null);
		}
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Parking parking) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE parkings "
						 + " SET spot_number = ?, "
						 + " entry_time = ?, "
						 + " hourly_rate = ?, "
						 + " is_occupied = ?, "
						 + " vehicle_id = ? "
						 + " WHERE id = ?;";
		
		db.prepareStatement(sqlUpdate);
		db.setInt(1, parking.getSpotNumber());
		
		if (parking.getEntryTime() != null) {
			db.setString(2, parking.getEntryTime().format(SQL_FORMATTER));
		} else {
			db.setString(2, null);
		}
		
		db.setString(3, parking.getHourlyRate().toString());
		db.setInt(4, parking.isOccupied() ? 1 : 0);
		
		if (parking.getVehicle() != null && parking.getVehicle().getId() > 0) {
			db.setInt(5, parking.getVehicle().getId());
		} else {
			db.setString(5, null);
		}
		
		db.setInt(6, parking.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Parking parking) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlDelete = "DELETE FROM parkings WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1, parking.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Parking> listAll() throws ModelException {
		DBHandler db = new DBHandler();
		List<Parking> parkings = new ArrayList<Parking>();
			
		String sqlQuery = "SELECT p.* FROM parkings p "
				        + "LEFT JOIN vehicles v ON v.id = p.vehicle_id "
				        + "ORDER BY p.spot_number;";
		
		db.createStatement();
		db.executeQuery(sqlQuery);

		while (db.next()) {
			Parking p = createParking(db);
			parkings.add(p);
		}
		
		return parkings;
	}

	@Override
	public Parking findById(int id) throws ModelException {
		DBHandler db = new DBHandler();
				
		String sql = "SELECT * FROM parkings WHERE id = ?;";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		Parking p = null;
		while (db.next()) {
			p = createParking(db);
			break;
		}
		
		return p;
	}
	
	private Parking createParking(DBHandler db) throws ModelException {
		Parking p = new Parking(db.getInt("id"));
		p.setSpotNumber(db.getInt("spot_number"));
		
		String rawDate = db.getString("entry_time");
		if (rawDate != null && !rawDate.isEmpty()) {
			p.setEntryTime(LocalDateTime.parse(rawDate, SQL_FORMATTER));
		}
		
		p.setHourlyRate(new BigDecimal(db.getString("hourly_rate")));
		p.setOccupied(db.getInt("is_occupied") == 1);
		
		int vehicleId = db.getInt("vehicle_id");
		if (vehicleId > 0) {
			VehicleDAO vehicleDAO = DAOFactory.createDAO(VehicleDAO.class); 
			Vehicle vehicle = vehicleDAO.findById(vehicleId);
			p.setVehicle(vehicle);
		}
		
		return p;
	}
}