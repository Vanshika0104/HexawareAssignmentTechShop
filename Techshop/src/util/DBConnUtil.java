package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnUtil {
	 public static Connection getConnection(String connectionString, String username, String password) {
	        try {
	            // Load the JDBC driver
	            Class.forName("com.mysql.cj.jdbc.Driver");  

	            // Establish the connection
	           return DriverManager.getConnection(connectionString,username, password);
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
}
