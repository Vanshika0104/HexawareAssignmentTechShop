package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import entity.model.Products;


public class InventoryDAOImpl implements InventoryDao {
	
	private Connection connection;
	
	public InventoryDAOImpl(Connection connection) {
        
		this.connection = connection;
    }

	@Override
	public void addNewProduct(Products product)  {
		// TODO Auto-generated method stub
		if (connection == null) {
            System.out.println("Database connection is null.");
        }

        // SQL query to insert a new product into the 'products' table
        String sql = "INSERT INTO products (productName, description, price, category, quantity) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setString(4, product.getCategory());
            statement.setInt(5, product.getQuantity());

            
            statement.executeUpdate();
            
            System.out.println("Product added successfully.");
        } catch (SQLException e) {
           
            e.printStackTrace();
            
        }
    }
        
		
	

	@Override
	public void updateProductQuantity(int productID, int newQuantity) {
		// TODO Auto-generated method stub
		 if (connection == null) {
	            System.out.println("Database connection is null.");
	        }

	        // SQL query to update the quantity of a product in the 'products' table
	        String sql = "UPDATE products SET quantity = ? WHERE productId = ?";

	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            
	            statement.setInt(1, newQuantity);
	            statement.setInt(2, productID);

	            
	            int rowsAffected = statement.executeUpdate();

	            if (rowsAffected > 0) {
	                System.out.println("Product quantity updated successfully.");
	            } else {
	                System.out.println("No product found with the given ID.");
	            }
	        } catch (SQLException e) {
	           
	           e.printStackTrace();
	            
	        }
        
		
	}

	@Override
	public void removeDiscontinuedProduct(int productID) {
		// TODO Auto-generated method stub
		if (connection == null) {
            System.out.println("Database connection is null.");
            return;
        }

        // SQL query to delete a product from the 'products' table
        String sql = "DELETE FROM products WHERE productID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, productID);

            
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product removed successfully.");
            } else {
                System.out.println("No product found with the given ID.");
            }
        } catch (SQLException e) {
            
            e.printStackTrace();
            
        }
    }
		
	}


