package controllers;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import play.mvc.Controller;
import play.mvc.Http.Request;
import play.mvc.Result;
import dao.UserAccessDAO;
import dao.UserAccessDAOImpl;
import dao.UserDAO;
import dao.UserDAOImpl;
import dao.UserProfileDAO;
import dao.UserProfileDAOImpl;
import dao.ValidationDAO;
import dao.ValidationDAOImpl;

public class Application extends Controller {
	
    public static Result index() {
    	Request req = request();
    	String abc = req.getQueryString("abc");
    	String cde = req.getQueryString("cde");
    	return ok("value of abc & cde " +abc + " " + cde);
    	//return ok(index.render("Your new application is ready."));
    	
    }

    public static Result registerUser() {
    	Request req = request();
    	String emailId = req.getQueryString("emailId");
    	String password = req.getQueryString("password");
    	UserDAO userDAO = new UserDAOImpl();
    	ValidationDAO validationDAO = new ValidationDAOImpl();
    	try {
    		int countEmail = userDAO.countEmails(emailId);
    		if(countEmail == 0) {
    			userDAO.createUser(emailId, password);
    			String userid = userDAO.fetchUserId(emailId);
    			validationDAO.createValidationCode(userid);
    			return ok("Registered user.");
    		} else
    			return ok("User already exists.");
    	} catch(SQLException e) {
    		return ok("SQL Excepton : " + e.toString());
    	}
    }
    
    public static Result loginUser() {
    	Request req = request();
    	String emailId = req.getQueryString("emailId");
    	String password = req.getQueryString("password");
    	UserDAO userDAO = new UserDAOImpl();
    	UserAccessDAO userAccessDAO = new UserAccessDAOImpl();
    	try {
	    	int countEmail = userDAO.countEmails(emailId);
	    	if(countEmail == 1) {
	    		if(password.equals(userDAO.fetchPassword(emailId))) {
	    			String userId = userDAO.fetchUserId(emailId);
	    			userAccessDAO.removeAccessToken(userId);
	    			userAccessDAO.createAccessToken(userId);
	    			return ok("Logged in sucessfully....");
	    		}
	    		else
	    			return ok("Incorrect Password....");
	    	}
	    	else
	    		return ok("Not a valid user. Please register.");
    	} catch(SQLException e) {
    		return ok("SQL Exception : " + e.toString());
    	}
    }
    
    public static Result validateEmail() {
    	Request req = request();
    	String emailId = req.getQueryString("emailId");
    	String validationCode = req.getQueryString("validationCode");
    	ValidationDAO validationDAO = new ValidationDAOImpl();
    	UserDAO userDAO = new UserDAOImpl();
    	try {
    		String userId = userDAO.fetchUserId(emailId);
    		String validCode = validationDAO.fetchValidationCode(userId);
    		Timestamp t = validationDAO.fetchExpiryTime(userId);
    		if(new Timestamp(new Date().getTime()).compareTo(t) == -1) {
    			if(validationCode.equals(validCode)) {
    				userDAO.validate(emailId);
    				return ok("Validation successful.");
    			}
    			else
    				return ok("Validation unsuccessful.");
    		} else
    			return ok("Reached expiry time. Please ask for another validation mail.");
    	} catch(SQLException e) {
    		return ok("SQL Exception : " + e.toString());
    	}
    }
    
    public static Result saveUserProfile() {
    	Request req = request();
    	String emailId = req.getQueryString("emailId");
    	String userName = req.getQueryString("userName");
    	String pic = req.getQueryString("pic");
    	String accessToken = req.getQueryString("accessToken");
    	UserDAO userDAO = new UserDAOImpl();
    	UserAccessDAO userAccessDAO = new UserAccessDAOImpl();
    	UserProfileDAO userProfileDAO = new UserProfileDAOImpl();
    	try {
    		String userId = userDAO.fetchUserId(emailId);
    		if(accessToken.equals(userAccessDAO.fetchAccessToken(userId))) {
    			userProfileDAO.saveUserProfile(userId, userName, pic);
    			return ok("Profile saved successfully.");
    		} else 
    			return ok("Access Denied.");
    	} catch(SQLException e) {
    		return ok("SQL Exception : " + e.toString());
    	}
    }
    
    public static Result resendValidationMail() {
    	Request req = request();
    	String emailId = req.getQueryString("emailId");
    	String accessToken = req.getQueryString("accessToken");
    	UserDAO userDAO = new UserDAOImpl();
    	ValidationDAO validationDAO = new ValidationDAOImpl();
    	UserAccessDAO userAccessDAO = new UserAccessDAOImpl();
    	try {
    		String userId = userDAO.fetchUserId(emailId);
    		if(accessToken.equals(userAccessDAO.fetchAccessToken(userId))) {
    			validationDAO.removeValidationCode(userId);
    			validationDAO.createValidationCode(userId);
    			return ok("Sent Validation email.");
    		} else
    			return ok("Access Denied.");
    	} catch(SQLException e) {
    		return ok("SQL Exception : " + e.toString());
    	}
    }

}
