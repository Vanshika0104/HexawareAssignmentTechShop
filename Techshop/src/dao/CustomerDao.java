package dao;
import entity.model.Customers;

public interface CustomerDao {
	void insertCustomer(Customers customer);
	Customers getCustomerByEmail(String email);
    
    void updateCustomer(Customers customer);
    
}
