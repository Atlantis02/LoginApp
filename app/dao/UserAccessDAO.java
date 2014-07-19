package dao;

import java.sql.SQLException;
import java.sql.Timestamp;

public interface UserAccessDAO {
	public abstract void createAccessToken(String userId) throws SQLException;
	public abstract String fetchAccessToken(String userId) throws SQLException;
	public abstract Timestamp fetchExpiryTime(String userId) throws SQLException;
	public abstract void removeAccessToken(String userId)  throws SQLException;
}
