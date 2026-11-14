package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Message;
import model.User;

public class MySQLMessageDAO implements MessageDAO {

	@Override
	public boolean save(Message message) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO messages VALUES "
				+ " (DEFAULT, ?, CURDATE(), ?);";
		
		db.prepareStatement(sqlInsert);
		db.setString(1, message.getContent());
		db.setInt(2, message.getUser().getId());
		  
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Message message) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE messages "
						 + " SET content = ?,"
						 + " user_id = ?"
						 + " WHERE id = ?";
		
		db.prepareStatement(sqlUpdate);
		
		db.setString(1, message.getContent());
		db.setInt(2, message.getUser().getId());
		db.setInt(3, message.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Message message) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlDelete = " DELETE FROM messages "
		         + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1, message.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Message> listAll() throws ModelException {
	
		DBHandler db = new DBHandler();
		
		List<Message> messages = new ArrayList<Message>();
			
		// Declara uma instrução SQL
		String sqlQuery = " SELECT u.id AS user_id, p.*, "
				        + " p.post_date "
				        + " FROM users u "
				        + " INNER JOIN messages p "
				        + " ON u.id = p.user_id "
				        + " ORDER BY content";
		
		db.createStatement();
	
		db.executeQuery(sqlQuery);

		while (db.next()) {
			Message p = createMessage(db);
			
			messages.add(p);
		}
		
		return messages;
	}

	@Override
	public Message findById(int id) throws ModelException {
		
		DBHandler db = new DBHandler();
				
		String sql = "SELECT * FROM messages WHERE id = ?;";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		Message p = null;
		while (db.next()) {
			p = createMessage(db);
			break;
		}
		
		return p;
	}
	
	private Message createMessage(DBHandler db) throws ModelException {
		Message p = new Message(db.getInt("id"));
		p.setContent(db.getString("content"));
		p.setPostDate(db.getDate("post_date"));
		
		UserDAO userDAO = DAOFactory.createDAO(UserDAO.class); 
		
		User user = userDAO.findById(db.getInt("user_id"));
		p.setUser(user);
		
		return p;
	}
}
