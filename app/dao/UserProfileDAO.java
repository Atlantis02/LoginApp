package dao;

import java.sql.SQLException;

public interface UserProfileDAO {
	public abstract void saveUserProfile(String userId, String userName, String pic) throws SQLException;
	public abstract String fetchUserName(String userId) throws SQLException;
	public abstract String fetchProfilePic(String userId) throws SQLException;
}
