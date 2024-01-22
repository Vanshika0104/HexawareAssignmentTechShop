package main;
import util.DBConnUtil;
import util.DBPropertyUtil;
import dao.DatabaseConnector;
import java.util.List;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Scanner;

//import entity.model.*;
import dao.CustomerDao;
import dao.CustomerDAOImpl;
import entity.model.Customers;
import dao.ProductsDao;
import entity.model.Products;
import dao.ProductDAOImpl;
import dao.InventoryDao;
import dao.InventoryDAOImpl;
import dao.OrdersDao;
import entity.model.Orders;
import dao.OrderDAOImpl;

import dao.PaymentDao;
import dao.PaymentDAOImpl;




public class Main {
	
	public static void main(String[] args) {
		Properties properties = new Properties();
		try (InputStream input = Main.class.getClassLoader().getResourceAsStream("util/config.properties")) {
		    if (input != null) {
		        properties.load(input);
		    } else {
		        System.out.println("config.properties not found");
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
        
        // Instantiate DatabaseConnector
        DatabaseConnector connector = new DatabaseConnector();
        String connectionStrings = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter 1 for Insert Customer:");
        System.out.println("Enter 2 for Updating Product Details :");
        System.out.println("Enter 3 for Recording Customer Orders:");
        System.out.println("Enter 4 for Viewing Order Status:");
        System.out.println("Enter 5 for Adding New Product:");
        System.out.println("Enter 6 for Updating Product Quantity:");
        System.out.println("Enter 7 for Removing Product:");
        System.out.println("Enter 8 for Updating Customer Details:");
        System.out.println("Enter 9 for Recording Payment Details:");
        System.out.println("Enter 10 for Search Product:");
        
        
        
        
        int choice = sc.nextInt();

        try {
            // Open a database connection
            Connection connection = connector.openConnection();
            System.out.println("Connected to the Database");
            CustomerDao customerDao = new CustomerDAOImpl(connection);
            ProductsDao productDao  = new ProductDAOImpl(connection);
            OrdersDao orderDao = new OrderDAOImpl(connection);
            InventoryDao inventoryDao = new InventoryDAOImpl(connection);
            Orders order = new Orders();
            Products product = new Products();
            switch(choice) {
            case 1:
            	// Inserting a  new customer
            	System.out.println("Enter CustomerID:");
                int customerID =sc.nextInt();
                System.out.println("Enter FirstName:");
                String firstName = sc.next();
                System.out.println("Enter LastName:");
                String lastName = sc.next();
                System.out.println("Enter email:");
                String email = sc.next();
                System.out.println("Enter phone:");
                String phone = sc.next();
                System.out.println("Enter address:");
                String address = sc.next();
                Customers newCustomer = new Customers(customerID, firstName,lastName ,email , phone, address);
            	customerDao.insertCustomer(newCustomer);
            	break;
            	
                 
                
             
            case 2:
            	 
               // Updating Product Details of Specified Product
            	 
            	 System.out.println("Enter productName :");
            	 String productName = sc.next();
            	 product.setProductName(productName);
            	 System.out.println("Enter Description of Product:");
            	 String Description = sc.next();
            	 product.setDescription(Description);
            	 System.out.println("Enter Product Price:");
            	 double price = sc.nextDouble();
            	 product.setPrice(price);
            	 System.out.println("Enter Category:");
            	 String Category = sc.next();
            	 product.setCategory(Category);
            	 System.out.println("Enter Quantity:");
            	 int quantity = sc.nextInt();
            	 product.setQuantity(quantity);
            	 System.out.println("Enter ProductId which you want to update:");
            	 int productID = sc.nextInt();
            	 product.setProductID(productID);
            	 productDao.updateProduct(product);
            	 System.out.println("Product Updated Successfully");
                break;
               	
            case 3:

                //Updating Orders
            	 
            	 System.out.println("Enter order Id:");
                 int orderID = sc.nextInt();  
                 order.setOrderID(orderID);
                 Customers Customer = new Customers(2, "Pari","Khan" ,"pari@gmail.com" , "568-980-7889", "148 Bhopal");
                 System.out.println("Enter TotalAmount:");
                 double totalAmount = sc.nextDouble();
                 order.setTotalAmount(totalAmount);
                 System.out.println("Enter OrderStatus:");
                 String orderStatus = sc.next();  
                 order.setOrderStatus(orderStatus);
                 LocalDateTime orderDate = LocalDateTime.now();
                 order.setOrderDate(orderDate);
                 Orders orders = new Orders(orderID, Customer,orderDate, totalAmount, orderStatus);
                 orderDao.placeOrder(orders);
                 System.out.println("Order Details updated Successfully");
                 break;
            	
            case 4:
            	 //View Order Status
            	System.out.println("Enter Order Id which you want to view:");
                int orderId = sc.nextInt();
                String ordersStatus = orderDao.getOrderStatus(orderId);

                if (ordersStatus != null) {
                    System.out.println("Order Status: " + ordersStatus);
                } else {
                    System.out.println("Order not found or status not available.");
                }
                break;
                
            case 5:
            	// Adding a new Product in Inventory
            	System.out.println("Enter ProductId:");
            	int productId = sc.nextInt();
            	System.out.println("Enter productName :");
           	    String ProductName = sc.next();
           	    product.setProductName(ProductName);
           	    System.out.println("Enter Description of Product:");
           	    String description = sc.next();
           	    product.setDescription(description);
           	    System.out.println("Enter Product Price:");
           	    double Price = sc.nextDouble();
           	    product.setPrice(Price);
           	    System.out.println("Enter Category:");
           	    String category = sc.next();
           	    product.setCategory(category);
           	    System.out.println("Enter Quantity:");
           	    int Quantity = sc.nextInt();
           	    product.setQuantity(Quantity);
            	
            	Products newProduct = new Products( productId,ProductName,description,Price,category,Quantity);
            	
                inventoryDao.addNewProduct(newProduct);
                break;
            case 6:
            	// Update Product Quantities in Inventory
            	System.out.println("Enter ProductId:");
            	int pId = sc.nextInt();
            	System.out.println("Enter Updated Product Quantity ");
            	int productQuantity = sc.nextInt();
            	inventoryDao.updateProductQuantity(pId, productQuantity);
            	break;
            case 7:
            	//Deleting Product
            	System.out.println("Enter the productId which you want to Remove");
            	int proId = sc.nextInt();
                inventoryDao.removeDiscontinuedProduct(proId);
                break;
            case 8:
            	// Update Customer Details
            	System.out.println("Enter CustomerID:");
                int customerId =sc.nextInt();
                System.out.println("Enter FirstName:");
                String FirstName = sc.next();
                System.out.println("Enter LastName:");
                String LastName = sc.next();
                System.out.println("Enter email:");
                String Email = sc.next();
                System.out.println("Enter phone:");
                String Phone = sc.next();
                System.out.println("Enter address:");
                String Address = sc.next();
                Customers customer = new Customers(customerId, FirstName,LastName ,Email , Phone, Address);
                customerDao.updateCustomer(customer);
                break;
            	
            case 9:
            	//Record Payment
                PaymentDao paymentDao = new PaymentDAOImpl(connection);
                System.out.println("Enter OrderId:");
                int ordersId = sc.nextInt();  
                System.out.println("Enter Payment Method:");
                String paymentMethod = sc.next();
                System.out.println("Enter TotalAmount:");
                double TotalAmount = sc.nextDouble(); 
                System.out.println("Enter Status:");
                String status = sc.next();
                paymentDao.recordPayment(ordersId, paymentMethod, TotalAmount, status);
                break;
            case 10:
            	//Search Product
            	System.out.println("Enter ProductName:");
            	String ProductsName = sc.next();
            	System.out.println("Enter Category:");
            	String ProductCategory = sc.next();
                List<Products> searchResults = productDao.searchProducts(ProductsName, ProductCategory);
                System.out.println("Search Results: " + searchResults);
                break;
            default:
                System.out.println("Exit");
        }
        sc.close();  
           

        } catch (SQLException e) {
            // Handle SQLException
            e.printStackTrace();
            System.err.println("Error connecting to the database: " + e.getMessage());
            
        } 
        finally {
            // Close the database connection in the 'finally' block
            connector.closeConnection();
        }
        String connectionString = DBPropertyUtil.getConnectionString("config.properties");
        System.out.println("Retrieved Connection String: " + connectionString);

        if (connectionString != null) {
            Connection connection = DBConnUtil.getConnection(connectionStrings, username, password);
            System.out.println("Database Connection String: " + connectionString );
        
         if(connection !=null){
             System.out.println("Connected to the Database");  
         }
         else{
             System.out.println("Failed to Establish A database Connection");
         }
            
        }
        
        else {
            System.out.println("Failed to retrieve the database connection string.");
        }
        
        
         }
}
