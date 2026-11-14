package model.dao;

import java.util.List;

import model.ModelException;
import model.Message;

public interface MessageDAO {
	boolean save(Message message) throws ModelException;
	boolean update(Message message) throws ModelException;
	boolean delete(Message message) throws ModelException;
	List<Message> listAll() throws ModelException;
	Message findById(int id) throws ModelException;
}
