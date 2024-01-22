package entity.model;
import java.util.Collections;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import exception.InsufficientStockException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;
import java.util.List;
public class Inventory {
	 private int inventoryID;
	    private Products product; 
	    private int quantityInStock;
	    private String lastStockUpdate;
	    private SortedMap<Integer, Inventory> inventoryManager;


	    

	    public Inventory(int inventoryID, Products product, int quantityInStock, String lastStockUpdate) {
	        this.inventoryID = inventoryID;
	        this.product = product;
	        this.quantityInStock = quantityInStock;
	        this.lastStockUpdate = lastStockUpdate;
	        
	    }
	    public Inventory() {
	        this.inventoryManager = new TreeMap<>();
	    }

	    public int getInventoryID() {
	        return inventoryID;
	    }

	    public void setInventoryID(int inventoryID) {
	        if (inventoryID > 0) {
	            this.inventoryID= inventoryID;
	        } else {
	            System.out.println(" Invalid Inventory ID.");
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

	    public int getQuantityInStock() {
	        return quantityInStock;
	    }

	    public void setQuantityInStock(int quantityInStock) {
	        if (quantityInStock >= 0) {
	            this.quantityInStock = quantityInStock;
	        } else {
	            System.out.println("Quantity in stock cannot be negative.");
	        }
	    }

	    public String getLastStockUpdate() {
	        return lastStockUpdate;
	    }

	    public void setLastStockUpdate(String lastStockUpdate) {
	        this.lastStockUpdate = lastStockUpdate;
	    }
	    // Method to add quantity To Inventory
	    public void addToInventory(int quantity) {
	        this.quantityInStock += quantity;
	        System.out.println(quantity + " " + product.getProductName() + "(s) added to inventory.");
	        updateLastStockUpdate();
	    }
	    //Method to Remove quantity from Inventory
	    public void removeFromInventory(int quantity) {
	        if (quantity <= quantityInStock) {
	            this.quantityInStock -= quantity;
	            System.out.println(quantity + " " + product.getProductName() + "(s) removed from inventory.");
	            updateLastStockUpdate();
	        } else {
	            System.out.println("Error: Insufficient quantity in stock.");
	        }
	    }
	    // Method to Check That quantity to sell is lesser than Quantity in Stock 
	    public void sellProduct(int quantityToSell) throws InsufficientStockException {
	        if (quantityInStock >= quantityToSell) {
	            
	            quantityInStock -= quantityToSell;
	        } else {
	            
	            throw new InsufficientStockException("Insufficient stock to fulfill the order.");
	        }
	    }
	    // Method to Update Stock Quantity
	    public void updateStockQuantity(int newQuantity) {
	        this.quantityInStock = newQuantity;
	        System.out.println("Stock quantity updated to " + newQuantity + ".");
	        updateLastStockUpdate();
	    }
	    // Method of Product Available in Inventory
	    public boolean isProductAvailable(int quantityToCheck) {
	        return quantityToCheck <= quantityInStock;
	    }
	    // Calculate the Value Of Product
	    public double getInventoryValue() {
	        return product.getPrice() * quantityInStock;
	    }
	    // Method to list products with quantities below a specified threshold

	    public void listLowStockProducts(int threshold) {
	        if (quantityInStock < threshold) {
	            System.out.println(product.getProductName() + " is low in stock. Quantity: " + quantityInStock);
	        }
	    }
	    //  Method to list products that are out of stock
	    public void listOutOfStockProducts() {
	        if (quantityInStock == 0) {
	            System.out.println(product.getProductName() + " is out of stock.");
	        }
	    }
	    // Method to list all products in the inventory, along with their quantities
	    public void listAllProducts() {
	        System.out.println("Product: " + product.getProductName() + ", Quantity: " + quantityInStock);
	    }
	    // Method to Update the Last Stock 
	    private void updateLastStockUpdate() {
	        LocalDateTime currentTime = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String formattedTimestamp = currentTime.format(formatter);
	        this.lastStockUpdate = formattedTimestamp;
	        System.out.println("Last stock update timestamp updated.");
	    }
	    // Method to Add Product in Inventory
	    public void addProductToInventory(int productID, Products product, int initialQuantity) {
	        Inventory newInventory = new Inventory();
	        inventoryManager.put(productID, newInventory);
	        System.out.println("Product added to inventory: " + newInventory);
	    }
	    // Method to remove Product from Inventory
	    public void removeProductFromInventory(int productID) {
	        Inventory removedInventory = inventoryManager.remove(productID);
	        if (removedInventory != null) {
	            System.out.println("Product removed from inventory: " + removedInventory);
	        } else {
	            System.out.println("Product not found in inventory. Product ID: " + productID);
	        }
	    }
	    // Method to Update the quantity in Inventory
	    public void updateInventoryQuantity(int productID, int newQuantity) {
	        Inventory inventory = inventoryManager.get(productID);
	        if (inventory != null) {
	            inventory.setQuantityInStock(newQuantity);
	            System.out.println("Inventory quantity updated: " + inventory);
	        } else {
	            System.out.println("Product not found in inventory. Product ID: " + productID);
	        }
	    }
	     public Inventory getInventoryInfo(int productID) {
	        return inventoryManager.get(productID);
	    }
        // Method to sort the List of Products according to the ProductId
	    public SortedSet<Integer> getAllProductID() {
	        return Collections.unmodifiableSortedSet(new TreeSet<>(inventoryManager.keySet()));
	    }
	    // To get Product  and it's quantity available in Inventory
	    @Override
	    public String toString() {
	            return "Inventory [product=" + product + ", quantityInStock=" + quantityInStock + "]";
	        }
	    public boolean hasSufficientStock(List<OrderDetails> orderDetailsList) {
	    int totalOrderQuantity = orderDetailsList.stream().mapToInt(OrderDetails::getQuantity).sum();
	    return quantityInStock >= totalOrderQuantity;
	}
	     public void updateStock(List<OrderDetails> orderDetailsList) throws InsufficientStockException {
	        // Check if there is sufficient stock before updating
	        if (hasSufficientStock(orderDetailsList)) {
	            // If there is, update the stock
	            int orderQuantity = orderDetailsList.stream().mapToInt(OrderDetails::getQuantity).sum();
	            quantityInStock -= orderQuantity;
	        } else {
	            // If there isn't sufficient stock, throw an InsufficientStockException
	            throw new InsufficientStockException("Insufficient stock to fulfill the order.");
	        }
	    }

}
