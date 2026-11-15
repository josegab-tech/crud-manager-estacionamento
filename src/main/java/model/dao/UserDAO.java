package model.dao;

import java.util.List;

import model.ModelException;
import model.User;
import model.Vehicle;

public interface UserDAO {
	boolean save(User user) throws ModelException ;
	boolean update(User user) throws ModelException;
	boolean delete(User user) throws ModelException;
	List<User> listAll() throws ModelException;
	List<User> listAll(int limit, int offset) throws ModelException;
	int countAll() throws ModelException;
	User findById(int id) throws ModelException;
}
