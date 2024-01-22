package entity.model;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import exception.DuplicateProductException;
import exception.InvalidProductException;
import exception.ProductInUseException;
public class Products {
	
	private int productID;
    private String productName;
    private String description;
    private double price;
    private boolean inStock;
    private String category;
    private int quantity;
    private List<Products> productList;
    private List<OrderDetails> orderDetailsList;

    // Constructors, getters, and setters for Product class
    public Products() {
    	
    }
    public Products( int productID,String productName, String description, double price, String category, int quantity) {
        // Initialize fields with the provided values
    	this.productID= productID;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
        
        this.productList = new ArrayList<>();
        this.orderDetailsList = new ArrayList<>();
    }
    public Products(boolean inStock) {
    	this.inStock = inStock;
    }
    
	public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        if (productID > 0) {
            this.productID = productID;
        } else {
            System.out.println(" Invalid product ID.");
        }

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
       if (productName != null && !productName.isEmpty()) {
            this.productName = productName;
        } else {
            System.out.println("Invalid product name.");
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            System.out.println("Price cannot be negative.");
        }
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
       if (category != null && !category.isEmpty()) {
            this.category = category;
        } else {
            System.out.println("Invalid Category.");
        }
    }
    public int getQuantity()
    {
    	return quantity;
    }
    public void setQuantity(int quantity) {
    	this.quantity = quantity;
    }
    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
    public List<Products> getProductList() {
        return productList;
    }
    
    // Method to Calculate Product Details
    public void getProductDetails() {
        System.out.println("Product ID: " + productID);
        System.out.println("Product Name: " + productName);
        System.out.println("Description: " + description);
        System.out.println("Price: $" + price);
        System.out.println("In Stock: " + (inStock ? "Yes" : "No"));
    }
    // Method to Update Product Info
    public void updateProductInfo(double newPrice, String newDescription, boolean newInStock) {
        this.price = newPrice;
        this.description = newDescription;
        this.inStock = newInStock;
        System.out.println("Product information updated successfully.");
    }
    // Method to Check Product In Stock
    public boolean isProductInStock() {
        return inStock;
    }
    public List<OrderDetails> getOrderDetailsList(){
        return orderDetailsList;
    }
    
    // Method to add a new product
    public void addProduct(Products newProduct) throws DuplicateProductException {
        // Check for duplicate products
        if (productList.contains(newProduct)) {
            throw new DuplicateProductException("Product with same details already exists.");
        }

        // Add the new product
        productList.add(newProduct);
    }

    // Method to update an existing product
    public void updateProduct(Products updatedProduct) throws InvalidProductException {
        // Check if the product exists
        if (!productList.contains(updatedProduct)) {
            throw new InvalidProductException("Product not found for updating.");
        }

        // Update the product details
        int index = productList.indexOf(updatedProduct);
        productList.set(index, updatedProduct);
    }

    // Method to remove a product
    public void removeProduct(Products productToRemove) throws ProductInUseException {
        // Check if the product is associated with any order (considering product in use)
        if (isProductInUse(productToRemove)) {
            throw new ProductInUseException("Cannot remove product associated with existing orders.");
        }

        // Remove the product
        productList.remove(productToRemove);
    }

    // Method to check if a product is associated with any order
    private boolean isProductInUse(Products product) {
        for (Products existingProduct : productList) {
            // Assuming each product has a list of associated order details
            List<OrderDetails> orderDetailsList = existingProduct.getOrderDetailsList();

            // Check if the current product is present in any order details
            for (OrderDetails orderDetail : orderDetailsList) {
                if (orderDetail.getProduct().equals(product)) {
                    return true; // Product is associated with an order
                }
            }
        }

        return false; // Product is not associated with any order
    }

        
    // To synchronize it with Inventory updates
    @Override
    public String toString() {
        return "Product [productID=" + productID + ", productName=" + productName + "]";
    }
    // Custom search method based on product name
    public List<Products> searchProductsByName(String keyword) {
        return productList.stream()
                .filter(product -> product.getProductName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
    // Custom Search Method based On Category
    public List<Products> searchProductsByCategory(String category) {
        return productList.stream()
                .filter(product -> product.getCategory().toLowerCase().equals(category.toLowerCase()))
                .collect(Collectors.toList());
    }
     public List<Products> searchProducts(String keyword, String category) {
        try {
            if ((keyword == null || keyword.isEmpty()) && (category == null || category.isEmpty())) {
                throw new Exception("At least one search criteria (keyword or category) must be provided.");
            }

            // Perform search based on the provided criteria
            if (keyword != null && !keyword.isEmpty()) {
                return searchProductsByName(keyword);
            } else {
                return searchProductsByCategory(category);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            
            return List.of();
        }
    }

}
