package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.model.Products;

public class ProductDAOImpl implements ProductsDao {
	private Connection connection;

    // Constructor that takes a database connection
    public ProductDAOImpl(Connection connection) {
        this.connection= connection;
    }

	@Override
	public void updateProduct(Products product) {
		// TODO Auto-generated method stub
		 try {
	            // Check if the connection is null before proceeding
	            if (connection == null) {
	                throw new IllegalStateException("Connection is null. Make sure to set the connection before using the DAO.");
	            }
		
            String sql = "UPDATE Products SET  productName = ?,  description = ?, price = ?, category= ? , quantity =? WHERE productID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
            	statement.setString(1, product.getProductName());
                statement.setString(2, product.getDescription());
                statement.setDouble(3, product.getPrice());  
                statement.setString(4, product.getCategory());
                statement.setInt(5, product.getQuantity());
                statement.setInt(6, product.getProductID());
                

                statement.executeUpdate();
                
            }
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
		 
    }

	
	

	@Override
	public List<Products> searchProducts(String keyword, String category) {
		// TODO Auto-generated method stub
		List<Products> products = new ArrayList<>();
		 String sql = "SELECT * FROM Products WHERE ProductName LIKE ? AND Category = ?";
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setString(1, "%" + keyword + "%");
	            statement.setString(2, category);
	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    products.add(mapResultSetToProduct(resultSet));
	                }
	            }
	        } catch (SQLException e) {
	            // Handle database-related exceptions
	            e.printStackTrace();
	        }
	        return products;
	}
	private Products mapResultSetToProduct(ResultSet resultSet) throws SQLException {
        // Mapping logic to convert ResultSet to Product object
        
        int productId = resultSet.getInt("ProductID");
        String productName = resultSet.getString("ProductName");
        String description = resultSet.getString("Description");
        double price = resultSet.getDouble("Price");
        String category = resultSet.getString("Category");
        int quantity = resultSet.getInt("Quantity");
        return new Products(productId, productName, description, price, category,quantity);
    }

	
		
	}


