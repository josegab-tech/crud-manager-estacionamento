package model.dao;

import java.util.List;

import model.ModelException;
import model.Vehicle;

public interface VehicleDAO {
	boolean save(Vehicle vehicle) throws ModelException ;
	boolean update(Vehicle vehicle) throws ModelException;
	boolean delete(Vehicle vehicle) throws ModelException;
	List<Vehicle> listAll() throws ModelException;
	Vehicle findById(int id) throws ModelException;

}