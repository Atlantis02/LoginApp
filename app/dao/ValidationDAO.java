package dao;

import java.sql.SQLException;
import java.sql.Timestamp;

public interface ValidationDAO {
	public abstract void createValidationCode(String userId) throws SQLException;
	public abstract String fetchValidationCode(String userId) throws SQLException;
	public abstract Timestamp fetchExpiryTime(String userId) throws SQLException;
	public abstract void removeValidationCode(String userId) throws SQLException;
}
