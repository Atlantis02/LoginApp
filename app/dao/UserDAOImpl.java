package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import play.db.DB;

public class UserDAOImpl implements UserDAO {
	
	private Connection conn = DB.getConnection();
	private String sql;
	private PreparedStatement stmt;

	@Override
	public void createUser(String emailId, String password) throws SQLException {
		sql = "Insert into tb_user(emailId, Password, isValid) values(?, ?, ?)";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, emailId);
		stmt.setString(2, password);
		stmt.setBoolean(3, false);
		stmt.executeUpdate();
	}

	@Override
	public String fetchUserId(String emailId) throws SQLException {
		sql = "Select userId from tb_user where emailId = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, emailId);
		String userId = stmt.executeQuery().getString(1);
		return userId;
	}

	@Override
	public Boolean fetchIsValid(String emailId) throws SQLException {
		sql = "Select isValid from tb_user where emailId = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, emailId);
		Boolean valid = stmt.executeQuery().getBoolean(1); 
		return valid;
	}

	@Override
	public String fetchPassword(String emailId) throws SQLException {
		sql = "Select password from tb_user where emailId = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, emailId);
		String password = stmt.executeQuery().getString(1);
		return password;
	}

	@Override
	public int countEmails(String emailId) throws SQLException {
		sql = "Select count(emailId) from tb_user where emailId = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, emailId);
		int count = stmt.executeQuery().getInt(1);
		return count;
	}

	@Override
	public void validate(String emailId) throws SQLException {
		sql = "Update isValid=true where emailId = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, emailId);
		stmt.executeUpdate();
	}
}
