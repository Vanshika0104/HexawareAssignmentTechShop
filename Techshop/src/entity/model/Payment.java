package entity.model;

import exception.PaymentFailedException;

public class Payment {
	private int paymentID;
    private int orderID;
    private double totalAmount;
    private String paymentMethod;
    private String status;

public Payment(int paymentID, int orderID, double totalAmount, String paymentMethod, String status) {
	this.paymentID= paymentID;
	this.orderID= orderID;
	this.totalAmount = totalAmount;
	this.paymentMethod = paymentMethod;
	this.status = status;
}
public int getPaymentID() {
	return paymentID;
}
public void setPaymentID(int paymentID) {
	this.paymentID= paymentID;
}
public int getOrderID() {
	return orderID;
}
public void setOrderID(int orderID) {
	this.orderID= orderID;
}
public double getTotalAmount() {
	return totalAmount;
}
public void setTotalAmount(double totalAmount) {
	this.totalAmount = totalAmount;
}
public String getPaymentMethod() {
	return paymentMethod;
}
public void setPaymentMethod(String paymentMethod) {
	this.paymentMethod = paymentMethod;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
//Method to record a new payment
public void recordPayment(int orderID, String paymentMethod,double totalAmount) {
    // Update  status or other relevant information as needed
	this.orderID= orderID;
	this.paymentMethod = paymentMethod;
	this.totalAmount = totalAmount;
    this.updateStatus("Paid");
    System.out.println("Payment recorded for order " + orderID + ": $" + totalAmount);
}

// Method to update payment status
public void updatePaymentStatus(String newStatus) {
    
    this.updateStatus(newStatus);
    
}

// Method to handle payment errors
public void handlePaymentError(PaymentFailedException e) {
    System.out.println("Payment error for order " + orderID + ": " + e.getMessage());
   
    this.updateStatus("Payment Failed");
    
}
// If Payment Failed then it update the status to Payment Failed
public void updateStatus(String newStatus) {
    this.status = newStatus;
    System.out.println("Payment status updated to: " + newStatus);
}
}