package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.User;
import model.Vehicle;

public class MySQLVehicleDAO implements VehicleDAO{

	
	@Override
	public boolean save(Vehicle vehicle) throws ModelException{
		
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO vehicle VALUES"+
							"(DEFAULT , ?, ? ,?, ?, ?, ?)";
		
		db.prepareStatement(sqlInsert);
		db.setString(1, vehicle.getPlate());
		db.setString(2, vehicle.getModel());
		db.setInt(2, vehicle.getManufactureYear());
		db.setString(3, vehicle.getCategory());
		db.setInt(4, vehicle.getUser().getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Vehicle vehicle) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE vehicles "
						 + " SET plate = ?, "
						 + " model = ?, "
						 + " manufacture_year = ?, "
						 + " category = ?, "
						 + " user_id = ? "
						 + " WHERE id = ?;";
		
		db.prepareStatement(sqlUpdate);
		db.setString(1, vehicle.getPlate());
		db.setString(2, vehicle.getModel());
		db.setInt(3, vehicle.getManufactureYear());
		db.setString(4, vehicle.getCategory());
		db.setInt(5, vehicle.getUser().getId());
		db.setInt(6, vehicle.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Vehicle vehicle) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlDelete = "DELETE FROM vehicles WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1, vehicle.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Vehicle> listAll() throws ModelException {
		DBHandler db = new DBHandler();
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
			
		String sqlQuery = "SELECT v.* FROM vehicles v "
				        + "INNER JOIN users u ON u.id = v.user_id "
				        + "ORDER BY v.plate;";
		
		db.createStatement();
		db.executeQuery(sqlQuery);

		while (db.next()) {
			Vehicle v = createVehicle(db);
			vehicles.add(v);
		}
		
		return vehicles;
	}

	@Override
	public Vehicle findById(int id) throws ModelException {
		DBHandler db = new DBHandler();
				
		String sql = "SELECT * FROM vehicles WHERE id = ?;";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		Vehicle v = null;
		while (db.next()) {
			v = createVehicle(db);
			break;
		}
		
		return v;
	}
	
	private Vehicle createVehicle(DBHandler db) throws ModelException {
		Vehicle v = new Vehicle(db.getInt("id"));
		v.setPlate(db.getString("plate"));
		v.setModel(db.getString("model"));
		v.setManufactureYear(db.getInt("manufacture_year"));
		v.setCategory(db.getString("category"));
		
		UserDAO userDAO = DAOFactory.createDAO(UserDAO.class); 
		User user = userDAO.findById(db.getInt("user_id"));
		v.setUser(user);
		
		return v;
	}
}
