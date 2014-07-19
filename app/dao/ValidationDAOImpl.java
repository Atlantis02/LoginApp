package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import play.db.DB;

public class ValidationDAOImpl implements ValidationDAO {

	private Connection conn = DB.getConnection();
	private String sql;
	private PreparedStatement stmt;
	
	@Override
	public void createValidationCode(String userId) throws SQLException {
		String validationCode = UUID.randomUUID().toString();
		Timestamp t = new Timestamp(new Date().getTime() + 5*60+60*1000);
		sql = "Insert into tb_validation(userId, expiryTime, validationCode) values(?, ?, ?)";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, userId);
		stmt.setTimestamp(2, t);
		stmt.setString(3, validationCode);
		stmt.executeUpdate();
	}

	@Override
	public String fetchValidationCode(String userId) throws SQLException {
		sql = "Select validationCode from tb_validation where userId = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, userId);
		String validationCode = stmt.executeQuery().getString(1);
		return validationCode;
	}

	@Override
	public Timestamp fetchExpiryTime(String userId) throws SQLException {
		sql = "Select expiryTime from tb_validation where userId = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, userId);
		Timestamp t = stmt.executeQuery().getTimestamp(1);
		return t;
	}

	@Override
	public void removeValidationCode(String userId) throws SQLException {
		sql = "Delete from tb_validation where userId = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, userId);
		stmt.executeUpdate();
	}

}
