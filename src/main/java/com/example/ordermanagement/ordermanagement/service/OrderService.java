package com.example.ordermanagement.ordermanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ordermanagement.ordermanagement.dao.CustomerDao;
import com.example.ordermanagement.ordermanagement.dao.OrderDao;
import com.example.ordermanagement.ordermanagement.dao.ProductDao;
import com.example.ordermanagement.ordermanagement.enums.OrderStatus;
import com.example.ordermanagement.ordermanagement.model.Customer;
import com.example.ordermanagement.ordermanagement.model.Order;
import com.example.ordermanagement.ordermanagement.model.Product;
import com.example.ordermanagement.ordermanagement.request.OrderRequest;

@Service
public class OrderService {

	@Autowired // injecting the OrderDao class and all its methods into Orderservice
	private OrderDao orderDao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private ProductDao productDao;

	/*
	 * CRUD operations - create, read/retrieve, update, delete create - createOrder
	 * read - findOrderById, findAllOrder update - updateOrder delete -
	 * deleteOrderById
	 */

	public Order createOrder(OrderRequest orderRequest) {
		// take all the request fields from OrderRequest and set it in Order object
		// then save Order object and return it
		Order order = new Order();
		order.setProductPurchaseQuantity(orderRequest.getProductPurchaseQuantity());
		order.setDeliveryAddress(orderRequest.getDeliveryAddress());
		//order.setOrderStatus(orderRequest.getOrderStatus());
		// whenever an order is created for first time it will be always in inprogress state
		order.setOrderStatus(OrderStatus.IN_PROGRESS.toString());
		// take customerid and productid from orderrequest and find corresponding
		// customer and product record from database using findbyid
		Optional<Customer> customer = customerDao.findById(orderRequest.getCustomerId());
		Optional<Product> product = productDao.findById(orderRequest.getProductId());
		// we have to set those customer and product in order
		order.setCustomer(customer.get());
		order.setProduct(product.get());
		
		// Get product price from product object that we have got using findbyid and store it in one string variable productPrice
		String productPrice = product.get().getPrice();
		// get productPurchasequantity from orderrequest and store it in one string variable productPurchaseQuantity
		String productPurchaseQuantity = orderRequest.getProductPurchaseQuantity();
		// converting both the above values to integer and multiplying it and storing it in productTotalPrice variable
		int productTotalPrice = Integer.parseInt(productPrice)*Integer.parseInt(productPurchaseQuantity);
		// take productTotalPrice and convert it to string back and set it in order object
		order.setProductTotalPrice(Integer.toString(productTotalPrice));
		
		order = orderDao.save(order);

		return order;
	}

	public Optional<Order> findOrderById(Long orderId) {
		// take OrderId as input and finds that Order in table if present else null
		Optional<Order> order = orderDao.findById(orderId);
		return order;
	}

	public List<Order> findAllOrder() {
		// fetch all the list of Orders from database table Order
		List<Order> orders = orderDao.findAll();
		return orders;
	}

	public void deleteOrderById(Long orderId) {
		// takes OrderId as input and deletes it in database table Order
		orderDao.deleteById(orderId);
	}

	public Order updateOrder(Long orderId, OrderRequest orderRequest) {
		// 1. get old Order values from database using findById and store it in Order
		// object
		// 2. set Order object old data to OrderRequest new data
		// 3. save Order object to database

		Order updatedOrder = null;
		// OrderRequest contains new data that needs to be updated coming from front end
		// Order object contains old data from the database that we got using findById
		// remove old data from Order and update it with new data of OrderRequest
		Optional<Order> order = orderDao.findById(orderId);// Order data in database which is already there - old data
		order.get().setProductPurchaseQuantity(orderRequest.getProductPurchaseQuantity());// set Order object which has
																							// // object
		order.get().setDeliveryAddress(orderRequest.getDeliveryAddress());
		// get orderstatus from orderrequest and check whether it is equal to orderstatus in enum and set that value to order object
		if(orderRequest.getOrderStatus().equals(OrderStatus.SHIPPED.toString())) {
			order.get().setOrderStatus(OrderStatus.SHIPPED.toString());
		} else if(orderRequest.getOrderStatus().equals(OrderStatus.DELIVERED.toString())) {
			order.get().setOrderStatus(OrderStatus.DELIVERED.toString());
		} else if(orderRequest.getOrderStatus().equals(OrderStatus.RETURN.toString())) {
			order.get().setOrderStatus(OrderStatus.RETURN.toString());
		} else if(orderRequest.getOrderStatus().equals(OrderStatus.CANCEL.toString())) {
			order.get().setOrderStatus(OrderStatus.CANCEL.toString());
		} else {
			order.get().setOrderStatus(OrderStatus.IN_PROGRESS.toString());
		}
		
		/*
		 * OrderStatus os; String shipped = os.SHIPPED.name(); String delivered =
		 * OrderStatus.DELIVERED.toString(); String returned =
		 * OrderStatus.RETURNED.toString(); String cancelled =
		 * OrderStatus.CANCELLED.toString(); String inProgress =
		 * OrderStatus.IN_PROGRESS.toString(); String orderStatus =
		 * orderRequest.getOrderStatus(); switch(orderStatus) { case :
		 * order.get().setOrderStatus(OrderStatus.SHIPPED.toString()); break;
		 * 
		 * }
		 */
		
		
		//order.get().setOrderStatus(orderRequest.getOrderStatus());

		// take customerid and productid from orderrequest and find corresponding
		// customer and product record from database using findbyid
		Optional<Customer> customer = customerDao.findById(orderRequest.getCustomerId());
		Optional<Product> product = productDao.findById(orderRequest.getProductId());
		// we have to set those customer and product in order
		order.get().setCustomer(customer.get());
		order.get().setProduct(product.get());
		
		// Get product price from product object that we have got using findbyid and
		// store it in one string variable productPrice
		String productPrice = product.get().getPrice();
		// get productPurchasequantity from orderrequest and store it in one string
		// variable productPurchaseQuantity
		String productPurchaseQuantity = orderRequest.getProductPurchaseQuantity();
		// converting both the above values to integer and multiplying it and storing it
		// in productTotalPrice variable
		int productTotalPrice = Integer.parseInt(productPrice) * Integer.parseInt(productPurchaseQuantity);
		// take productTotalPrice and convert it to string back and set it in order
		// object
		order.get().setProductTotalPrice(Integer.toString(productTotalPrice));

		// save updated Order object back to database and return result
		updatedOrder = orderDao.save(order.get());
		return updatedOrder;
	}
	public int updateOrderWithQuery(Long orderId, OrderRequest orderRequest) {
		// perform 94-145 line same as it is
		int num = orderDao.updateOrderWithQuery(orderRequest.getProductPurchaseQuantity(), orderRequest.getOrderStatus(),
				orderRequest.getDeliveryAddress(), orderId);
		return num;
	}
}
