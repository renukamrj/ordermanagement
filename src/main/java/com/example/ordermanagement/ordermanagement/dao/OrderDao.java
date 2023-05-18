package com.example.ordermanagement.ordermanagement.dao;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ordermanagement.ordermanagement.model.Order;

@Repository // it does database operations
//JpaRepository has in built methods for database operations, we are inheriting it and using in our application
public interface OrderDao extends JpaRepository<Order, Long> {
    @Modifying//updating the database
    @Transactional//update transaction
	@Query(nativeQuery = true, value="update order set order_status = :orderStatus,product_purchase_quantity=:productPurchaseQuantity,deleivery_address=:deliveryAddress where order_id=:orderId")
	public int updateOrderWithQuery(String productPurchaseQuantity, String orderStatus,String deliveryAddress,Long orderId);
    
    @Query(nativeQuery = true, value ="select * from order_management.order where customer_id = :customerId and order_status = \"INPROGRESS\" or order_status=\"SHIPPED\";")
	public List<Order> getOrderWithInprogressAndShippedStatus(Long customerId);

}


