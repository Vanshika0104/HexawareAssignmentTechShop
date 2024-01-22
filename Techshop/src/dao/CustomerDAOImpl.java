package dao;


import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.SQLException;

import exception.DuplicateEmailException;


import entity.model.Customers;

public class CustomerDAOImpl implements CustomerDao  {
	private Connection connection;

    // Constructor that takes a database connection
	
    public CustomerDAOImpl(Connection connection) {
        this.connection = connection;
    }
    
    
       
        

	public CustomerDAOImpl() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public void insertCustomer(Customers customer) {
		// TODO Auto-generated method stub
		try {
            // Check if the email already exists
            if (getCustomerByEmail(customer.getEmail()) != null) {
                
                throw new DuplicateEmailException("Email address already exists");
            }
            String sql = "INSERT INTO customers (customerID, firstName, lastName, email, phone, address) VALUES (?, ?, ?, ?, ?,?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
            	statement.setLong(1, customer.getCustomerID());
                statement.setString(2, customer.getFirstName());
                statement.setString(3, customer.getLastName());
                statement.setString(4, customer.getEmail());
                statement.setString(5, customer.getPhone());
                statement.setString(6, customer.getAddress());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
		
	}

	

	@Override
	public void updateCustomer(Customers customer) {
		// TODO Auto-generated method stub
		try {
            
            if (connection == null) {
                throw new IllegalStateException("Connection is null. Make sure to set the connection before using the DAO.");
            }
	
        String sql = "UPDATE Customers SET  firstName = ?, lastName=?, email = ?, phone = ?, address= ? WHERE customerID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        	statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());  
            statement.setString(4, customer.getPhone());
            statement.setString(5, customer.getAddress());
            statement.setInt(6, customer.getCustomerID());
            

            statement.executeUpdate();
            System.out.println("Customer Details Updated Successfully");
            
        }
    } catch (SQLException e) {
      
        e.printStackTrace();
    }
	 
		
		
	}


	@Override
	public Customers getCustomerByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}


	
	

}
