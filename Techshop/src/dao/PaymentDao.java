package dao;

public interface PaymentDao {
	 void recordPayment(int orderId, String paymentMethod, double totalAmount, String status);
}
