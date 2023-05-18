package com.example.ordermanagement.ordermanagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ordermanagement.ordermanagement.model.Order;
import com.example.ordermanagement.ordermanagement.request.OrderRequest;
import com.example.ordermanagement.ordermanagement.service.OrderService;

@RestController // combination of @controller and @responsebody
@RequestMapping(value = "/oms/v1/order") // it maps web requests url/endpoint
public class OrderController {

	// from controller request goes to service and then to dao
		/*
		 * create - post, update - patch and put, delete - delete, read/retrieve - get
		 * 
		 * API - application programming interface - communicate between request and
		 * response REST API - representational state transfer API
		 */
		@Autowired
		private OrderService orderService;

		// @RequestBody - it takes the input form the from end in json format and passes
		// it to api
		@PostMapping(value = "/save") // other way @RequestMapping(value="/save", method=RequestMethod.POST)
		public ResponseEntity<?> saveOrder(@RequestBody OrderRequest orderRequest) {
			// call service method createOrder and return Order body in responseentity
			Order order = orderService.createOrder(orderRequest);
			return ResponseEntity.ok().body(order);
		}

		// @PathVariable - it will be specified in the url
		@GetMapping(value = "/find/{orderId}")
		public ResponseEntity<?> getOrderById(@PathVariable("orderId") Long orderId) {
			// take OrderId from the url and call findOrderById which is in service
			// class and return result
			Optional<Order> order = orderService.findOrderById(orderId);
			return ResponseEntity.ok().body(order);

		}

		@GetMapping(value = "/findall")
		public ResponseEntity<?> fetchAllOrder() {
			// call findAllOrder in service class and return list of Orders
			List<Order> ordersList = orderService.findAllOrder();
			return ResponseEntity.ok().body(ordersList);
		}

		@DeleteMapping(value = "/delete/{orderId}")
		public ResponseEntity<?> deleteCustomerById(@PathVariable("orderId") Long orderId) {
			// call deleteOrderById method in service class and return nothing
			// but passing an explicit deleted message in responseentity
			orderService.deleteOrderById(orderId);
			return ResponseEntity.ok().body("The Order with id " + orderId + " got deleted!!!");
		}

		@PutMapping(value = "/update/{orderId}")
		public ResponseEntity<?> updateCustomer(@PathVariable("orderId") Long orderId,
				@RequestBody OrderRequest orderRequest) {
			Order order = orderService.updateOrder(orderId, orderRequest);
			return ResponseEntity.ok().body(order);
		}
		@PutMapping(value="/updatewithquery/{orderId}")
		public ResponseEntity<?> updateCustomerWithQuery(@PathVariable("orderId") Long orderId,
				@RequestBody OrderRequest orderRequest) {
			int num= orderService.updateOrderWithQuery(orderId, orderRequest);
			return ResponseEntity.ok().body(num);
		}
}
