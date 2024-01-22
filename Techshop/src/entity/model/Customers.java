package entity.model;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exception.InvalidDataException;

import java.util.ArrayList;
import java.util.List;
public class Customers {
	private int customerID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private List<Orders> orders;

    public Customers() {
    	
    }

    public Customers   (int customerID, String firstName, String lastName, String email, String phone, String address) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.orders = new ArrayList<>();
    }

    public int getCustomerID() {
        return customerID;
    }
    // Data validation method for setters
    public void setCustomerID(int customerID) {
        if (customerID> 0) {
            this.customerID = customerID;
        } else {
            System.out.println(" Invalid customer ID.");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
       if (firstName != null && !firstName.isEmpty()) {
            this.firstName = firstName;
        } else {
            System.out.println("Invalid first name.");
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName != null && !lastName.isEmpty()) {
            this.lastName = lastName;
        } else {
            System.out.println("Invalid last name.");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (isValidEmail(email)) {
            this.email = email;
        } else {
            throw new InvalidDataException("Invalid email address.");
        }
    }
    
    private boolean isValidEmail(String email) {
        return email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (isValidPhone(phone)) {
            this.phone = phone;
        } else {
            System.out.println("Invalid phone number.");
        }
    }
    
    private  boolean isValidPhone(String phoneNumber) {
        String phoneRegex = "^[0-9]{10}$"; // Assumes a 10-digit phone number
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
        }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void addOrder(Orders order) {
        this.orders.add(order);
    }
    //Method to Calculate Total Orders
    public int calculateTotalOrders() {
        return orders.size();
    }
    // Method to Calculate CustomerInfo
    public void getCustomerDetails() {
        System.out.println("Customer ID: " + customerID);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Address: " + address);
        System.out.println("Total Orders: " + calculateTotalOrders());
    }
    // Method to Calculate Update Customer Info
    public void updateCustomerInfo(String newEmail, String newPhone, String newAddress) {
        this.email = newEmail;
        this.phone = newPhone;
        this.address = newAddress;
        System.out.println("Customer information updated successfully.");
    }
}


