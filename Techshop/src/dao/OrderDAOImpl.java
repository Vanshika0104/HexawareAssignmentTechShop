package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;



import entity.model.Orders;




public class OrderDAOImpl implements OrdersDao {
	private Connection connection;
	public OrderDAOImpl(Connection connection) {
        this.connection = connection;
    }
	@Override
	public String getOrderStatus(int orderID) {
		// TODO Auto-generated method stub
		String sql = "SELECT OrderStatus FROM orders WHERE orderID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("OrderStatus");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
       
		return null;
	}
	@Override
	public void placeOrder(Orders order) {
		// TODO Auto-generated method stub
		try {
	        // Insert order
			
	        String orderSql = "INSERT INTO Orders( orderID,CustomerID, orderDate, TotalAmount, OrderStatus) VALUES (?,?, CURRENT_DATE, ?,?)";
	        try (PreparedStatement orderStatement = connection.prepareStatement(orderSql)) {
	            orderStatement.setInt(1, order.getOrderID());
	        	orderStatement.setInt(2, order.getCustomer().getCustomerID());
	        	
	        	
	            
	            orderStatement.setDouble(3, order.getTotalAmount());
	            orderStatement.setString(4, order.getOrderStatus());

	            orderStatement.executeUpdate();

	            System.out.println("Order Recorded Successfully");
	            
                 
	            
	            
	            }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        
	    }
		
	}
	@Override
	public List<Orders> getAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}

}
