package dao;

import entity.model.Products;

public interface InventoryDao {
	void addNewProduct(Products product);
    void updateProductQuantity(int productID, int newQuantity);
    void removeDiscontinuedProduct(int productID);
}
