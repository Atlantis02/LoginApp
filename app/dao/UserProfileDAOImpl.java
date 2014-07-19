package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import play.db.DB;

public class UserProfileDAOImpl implements UserProfileDAO {

	private Connection conn = DB.getConnection();
	private String sql;
	private PreparedStatement stmt;
	
	@Override
	public void saveUserProfile(String userId, String userName, String pic) throws SQLException {
		sql = "Delete from tb_user_profile where userId = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, userId);
		stmt.executeUpdate();
		sql = "Insert into tb_user_profile(userId, userName, pic) values(?, ?, ?)";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, userId);
		stmt.setString(2, userName);
		stmt.setString(3, pic);
		stmt.executeUpdate();
	}

	@Override
	public String fetchUserName(String userId) throws SQLException {
		sql = "Select userName from tb_user_profile where userId = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, userId);
		String userName = stmt.executeQuery().getString(1);
		return userName;
	}

	@Override
	public String fetchProfilePic(String userId) throws SQLException {
		sql = "Select pic from tb_user_profile where userId = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, userId);
		String pic = stmt.executeQuery().getString(1);
		return pic;
	}

}
