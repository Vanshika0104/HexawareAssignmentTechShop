package dao;
import java.util.List;
import entity.model.Orders;
public interface OrdersDao {
	void placeOrder(Orders order);
    List<Orders> getAllOrders();
	String getOrderStatus(int orderID) ;
}
