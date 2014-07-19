package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import play.db.DB;

public class UserAccessDAOImpl implements UserAccessDAO {

	private Connection conn = DB.getConnection();	
	private String sql;
	private PreparedStatement stmt;
	
	@Override
	public void createAccessToken(String userId) throws SQLException {
		String accessToken = UUID.randomUUID().toString();
		Timestamp t = new Timestamp(new Date().getTime() + 24*60*60*1000);
		sql = "Insert into tb_user_access(userId, accessToken, expiryTime) values(?, ?, ?)";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, userId);
		stmt.setString(2, accessToken);
		stmt.setTimestamp(3, t);
		stmt.executeUpdate();
	}

	@Override
	public String fetchAccessToken(String userId) throws SQLException {
		sql = "Select accessToken from tb_user_access where userid = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, userId);
		String accessToken = stmt.executeQuery().getString(1);
		return accessToken;
	}

	@Override
	public Timestamp fetchExpiryTime(String userId) throws SQLException {
		sql = "Select expiryTime from tb_user_access where userid = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, userId);
		Timestamp t = stmt.executeQuery().getTimestamp(1);
		return t;
	}

	@Override
	public void removeAccessToken(String userId) throws SQLException {
		sql = "Delete from tb_user_access where userId = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, userId);
		stmt.executeUpdate();
	}
	
}
