package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class PaymentDAOImpl  implements PaymentDao{
	private Connection connection;

    public PaymentDAOImpl(Connection connection) {
        this.connection = connection;
    }

	@Override
	public void recordPayment(int orderID, String paymentMethod, double totalAmount, String status) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO Payments( orderID, paymentMethod, totalAmount, status) VALUES (?, ?, ?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        	
            statement.setInt(1, orderID);
            statement.setString(2, paymentMethod);
            statement.setDouble(3, totalAmount);
            statement.setString(4, status);
            statement.executeUpdate();
            System.out.println("Payment recorded successfully.");
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
		
	}

}
