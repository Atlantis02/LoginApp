package dao;

import java.sql.SQLException;

public interface UserDAO {
	
	public abstract void createUser(String emailId, String password) throws SQLException;
	public abstract String fetchUserId(String emailId) throws SQLException;
	public abstract Boolean fetchIsValid(String userId) throws SQLException;
	public abstract String fetchPassword(String emailId) throws SQLException;
	public abstract int countEmails(String emailId) throws SQLException; 
	public abstract void validate(String emailId) throws SQLException;
}
