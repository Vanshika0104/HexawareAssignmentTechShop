package entity.model;
import java.util.ArrayList;
import java.util.List;
import exception.UnavailableProductException;
import exception.IncompleteOrderException;
public class OrderDetails {
	 private int orderDetailID;
	    private Orders order;
	    private Products product;
	    private int quantity;
	    private List<OrderDetails> orderDetailsList;

	    

	    public OrderDetails(int orderDetailID, Orders order, Products product, int quantity) {
	        this.orderDetailID = orderDetailID;
	        this.order = order;
	        this.product = product;
	        this.quantity = quantity;
	        this.orderDetailsList = new ArrayList<>();
	    }

	    public int getOrderDetailID() {
	        return orderDetailID;
	    }

	    public void setOrderDetailID(int orderDetailID) {
	        if ( orderDetailID> 0) {
	            this.orderDetailID = orderDetailID;
	        } else {
	            System.out.println("Invalid Order Details ID.");
	        }
	    }

	    public Orders getOrder() {
	        return order;
	    }

	    public void setOrder(Orders order) {
	        if (order != null) {
	            this.order = order;
	        } else {
	            System.out.println(" Invalid order.");
	        }
	    }
	    
	    public void validateOrderDetail() throws IncompleteOrderException {
	        if (product == null) {
	            throw new IncompleteOrderException("Incomplete order detail: Product reference is missing.");
	        }
	        
	    }

	    public Products getProduct() {
	        return product;
	    }

	    public void setProduct(Products product) {
	        if (product != null) {
	            this.product = product;
	        } else {
	            System.out.println("Invalid product.");
	        }
	    }

	    public int getQuantity() {
	        return quantity;
	    }

	    public void setQuantity(int quantity) {
	        if (quantity > 0) {
	            this.quantity = quantity;
	        } else {
	            System.out.println("Invalid quantity. Quantity must be a positive integer.");
	        }
	    }
	    public List<OrderDetails> getOrderDetailsList(){
	        return orderDetailsList;
	    }
	    // Method to Calculate SubTotal
	    public double calculateSubTotal() {
	       if (product != null && quantity >= 0) {
	            return quantity * product.getPrice();
	        } else {
	            
	            System.out.println("Invalid product or quantity.");
	            return 0.0;  
	        }
	    }
	    // Method to Calculate OrderDetailInfo
	    public void getOrderDetailInfo() {
	        System.out.println("Order Detail ID: " + orderDetailID);
	        System.out.println("Order ID: " + order.getOrderID());
	        System.out.println("Product: " + product.getProductName());
	        System.out.println("Quantity: " + quantity);
	        System.out.println("Subtotal: " + calculateSubTotal());
	    }
	    // Method update Quantity
	    public void updateQuantity(int newQuantity) {
	        this.quantity = newQuantity;
	        System.out.println("Quantity updated successfully.");
	    }
	    // Method to add Discount
	    public void addDiscount(double discountPercentage) {
	        double discountedPrice = product.getPrice() * (1 - discountPercentage / 100);
	        System.out.println("Discounted Price: " + discountedPrice);
	    }
	    //Method to validate Product Availability in Inventory
	    public void addOrderDetail(Inventory inventory) throws UnavailableProductException {
	    if (inventory.isProductAvailable( quantity)) {
	        // Product is available, add the order detail
	        order.getOrderDetailsList().add(this);
	        System.out.println("Order detail added successfully.");
	    } else {
	        // Product is not available, throw an exception
	        throw new UnavailableProductException("Product is not available in sufficient quantity.");
	    }
	}
}
