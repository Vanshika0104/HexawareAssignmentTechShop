package entity.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import exception.InsufficientStockException;
import exception.IncompleteOrderException;
import exception.PaymentFailedException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class Orders {
	private int orderID;
    private Customers customer;
    private LocalDateTime orderDate;
    private double totalAmount;
    private String orderStatus;
    private List<OrderDetails> orderDetailsList;
    
   

    // Constructors, getters, and setters for Orders class
    public Orders() {
    	
    }
    
    public Orders(int orderID, Customers customer,LocalDateTime orderDate, double totalAmount,  String orderStatus) {
        this.orderID = orderID;
        this.customer = customer;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.orderDetailsList = new ArrayList<>();
        
    }
    

    

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        if ( orderID> 0) {
            this.orderID = orderID;
        } else {
            System.out.println(" Invalid Order ID.");
        }
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        if (customer != null) {
            this.customer = customer;
        } else {
            System.out.println("Invalid customer.");
        }
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
       return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
         if (totalAmount >= 0) {
            this.totalAmount = totalAmount;
        } else {
            System.out.println("Total amount cannot be negative.");
        }
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }
    
    //Method to calculate TotalAmount
    public double calculateTotalAmount(){
        double total =0;
        for (OrderDetails orderDetails: orderDetailsList ) {
            totalAmount += orderDetails.calculateSubTotal();
        }
        return total;
    }
    public void setOrderDetails(ArrayList<OrderDetails> orderDetailsList){
 
        if(orderDetailsList != null){
            this.orderDetailsList = orderDetailsList;
        }else{
            System.out.println("Invalid OrderDetails");
        }
    }
    // Method to getOrderInfo
     public void getOrderInfo() {
        System.out.println("Order ID: " + orderID);
        System.out.println("Customer First Name: " + customer.getFirstName());
        System.out.println("Customer Last Name: " + customer.getLastName());
        System.out.println("Order Date: " + orderDate);
        System.out.println("Total Amount: $" + calculateTotalAmount());
    }
     
    //Method which Handle Incomplete Order Exception
    public void processOrder() {
        for (OrderDetails orderDetail : orderDetailsList) {
            try {
                orderDetail.validateOrderDetail();
                
            } catch (IncompleteOrderException e) {
                
                System.out.println("Error: " + e.getMessage());
                updateOrderStatus("Incomplete Order");
                
                orderDetailsList.remove(orderDetail);
            }
        }
    }
    // Method Handle InsufficientStockException when OrderQuantity is more than it present in Stock
    public void processOrder(Inventory inventory) {
        try {
            
           int OrderQuantity = orderDetailsList.stream().mapToInt(OrderDetails::getQuantity).sum();

           inventory.sellProduct(OrderQuantity);

            
            orderStatus = "Processed";
            System.out.println("Order processed successfully.");
           

        } catch (InsufficientStockException e) {
            
            orderStatus = "Insufficient Stock";
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    // Add new orders
    public void addOrderDetails(OrderDetails orderDetails) {
        orderDetailsList.add(orderDetails);
    }

    // Update order statuses
    public void updateOrderStatus(String newStatus) {
        this.orderStatus = newStatus;
    }

    // Remove canceled orders
    public void removeCanceledOrders() {
    	orderDetailsList.removeIf(orderDetails -> "Cancelled".equals(orderDetails.getOrder().getOrderStatus()));
    }
    // Method to update Order Quantities When Order is Processing
    public void processingOrder(Inventory inventory) {
        try {
            // Check if there is sufficient stock in the inventory
            if (inventory.hasSufficientStock(orderDetailsList)) {
                // If there is, update the inventory and mark the order as processed
                inventory.updateStock(orderDetailsList);
                orderStatus = "Processed";
                System.out.println("Order processed successfully.");
            } else {
                // If there isn't sufficient stock, update the order status accordingly
                orderStatus = "Insufficient Stock";
                System.out.println("Insufficient stock to fulfill the order.");
            }
        } catch (InsufficientStockException e) {
            
            System.out.println("Error: " + e.getMessage());
        }
    }
  //Method to Process Payment
    private void processPayment(double totalAmount) throws PaymentFailedException {
      
        boolean paymentSuccessful = false;

        if (!paymentSuccessful) {
            throw new PaymentFailedException("Payment failed. Please try again or use a different payment method.");
        }

        updateOrderStatus("Processed");
        System.out.println("Payment successful. Thank you for your purchase!");
    }
    // Method to Process Order With Payment
    public void processOrderWithPayment(double totalAmount) {
        try {
            
            processPayment(totalAmount);

            
            orderStatus = "Processed";
            System.out.println("Order processed successfully.");

        } catch (PaymentFailedException e) {
            
            handlePaymentFailure(e);
        }
    }
     // Method to Handle PaymentFailure Exception
    private void handlePaymentFailure(PaymentFailedException e) {
    System.out.println("Payment failed: " + e.getMessage());

    //Here, we initiating Retry method
    int maxRetries = 2;
    int currentRetry = 0;

    while (currentRetry < maxRetries) {
        try {
            
            processPayment(totalAmount);
            System.out.println("Retry successful. Thank you for your purchase!");
            return; 
        } catch (PaymentFailedException retryException) {
            
            System.out.println("Retry #" + (currentRetry + 1) + " failed: " + retryException.getMessage());
            currentRetry++;

        
            try {
                Thread.sleep(1000); // Sleep for 1 second before retrying
            } catch (InterruptedException sleepException) {
                
                System.err.println("Sleep interrupted: " + sleepException.getMessage());
            }
        }
    }

    
    handleCancellation();
}
    
 // Method Which Cancel the order
    public void cancelOrder() {
       System.out.println("Order cancelled.");
   }
    //Method to Handle Cancellation
    private void handleCancellation() {
        orderStatus = "Cancelled";
        System.out.println("Order cancelled after multiple payment failures.");
    }

    

    
    // Sorting by OrderDate Methods
    public static void sortByOrderDateAscending(List<Orders> ordersList) {
        Collections.sort(ordersList, Comparator.comparing(Orders::getOrderDate));
        System.out.println("Orders sorted by order date in ascending order.");
    }
    public static void sortByOrderDateDescending(List<Orders> ordersList) {
        Collections.sort(ordersList, Comparator.comparing(Orders::getOrderDate).reversed());
        System.out.println("Orders sorted by order date in descending order.");
    }
     public static List<Orders> getOrdersInDateRange(List<Orders> ordersList, LocalDateTime startDate, LocalDateTime endDate) {
        List<Orders> ordersInDateRange = new ArrayList<>();

        for (Orders order : ordersList) {
            LocalDateTime orderDate = order.getOrderDate();
            if (orderDate.isAfter(startDate) && orderDate.isBefore(endDate)) {
                ordersInDateRange.add(order);
            }
        }

        return ordersInDateRange;
    }
    
     
}
