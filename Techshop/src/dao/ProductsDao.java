package dao;
import entity.model.Products;
import java.util.List;
public interface ProductsDao {
	
	void updateProduct(Products product);
	
	List<Products> searchProducts(String keyword, String category);

	

}
