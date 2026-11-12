package model.dao;

import java.util.List;

import model.ModelException;
import model.Parking;

public interface ParkingDAO {
	boolean save(Parking parking) throws ModelException ;
	boolean update(Parking parking) throws ModelException;
	boolean delete(Parking parking) throws ModelException;
	List<Parking> listAll() throws ModelException;
	Parking findById(int id) throws ModelException;
}