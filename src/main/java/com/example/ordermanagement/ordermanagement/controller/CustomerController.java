package com.example.ordermanagement.ordermanagement.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ordermanagement.ordermanagement.model.Customer;
import com.example.ordermanagement.ordermanagement.request.CustomerRequest;
import com.example.ordermanagement.ordermanagement.service.CustomerService;

@RestController // combination of @controller and @responsebody
@RequestMapping(value = "/oms/v1/customer") // it maps web requests url/endpoint
public class CustomerController {
	Logger logger=LoggerFactory.getLogger(CustomerController.class);

	// from controller request goes to service and then to dao
	/*
	 * create - post, 
	 * update - patch and put, 
	 * delete - delete,
	 * read/retrieve - get
	 * 
	 * API - application programming interface - communicate between request and
	 * response REST API - representational state transfer API
	 */
	@Autowired
	private CustomerService customerService;

	// @RequestBody - it takes the input form the from end in json format and passes
	// it to api
	@PostMapping(value = "/save") // other way @RequestMapping(value="/save", method=RequestMethod.POST)
	public ResponseEntity<?> saveCustomer(@RequestBody CustomerRequest customerRequest) {
		
		try {
			// call service method createcustomer and return customer body in responseentity
			logger.info("saveCustomer API has started!");
		Customer customer = customerService.createCustomer(customerRequest);
		return ResponseEntity.ok().body(customer);
	}catch(Exception e) {
		logger.error("Some exception occured while saving customer");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}
	}

	// @PathVariable - it will be specified in the url
	@GetMapping(value = "/find/{customerId}")
	public ResponseEntity<?> getCustomerById(@PathVariable("customerId") Long customerId) {
		try {
		// take customerId from the url and call findCustomerById which is in service class and return result
		Optional<Customer> customer = customerService.findCustomerById(customerId);
		return ResponseEntity.ok().body(customer);

	} catch(Exception e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	}
	
	@GetMapping(value="/findall")
	public ResponseEntity<?> fetchAllCustomer(){
		try {
		// call findAllCustomer in service class and return list of customers
		List<Customer> customersList =  customerService.findAllCustomer();
		return ResponseEntity.ok().body(customersList);
	}catch(Exception e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	}
	
	@DeleteMapping(value="/delete/{customerId}")
	public ResponseEntity<?> deleteCustomerById(@PathVariable("customerId") Long customerId){ 
		try {
		// call deleteCustomerById method in service class and return nothing
		// but passing an explicit deleted message in responseentity
		customerService.deleteCustomerById(customerId);
		return ResponseEntity.ok().body("The customer with id "+customerId+" got deleted!!!");
	} catch(Exception e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	}
	
	@PutMapping(value="/update/{customerId}")
	public ResponseEntity<?> updateCustomer(@PathVariable("customerId") Long customerId,
											@RequestBody CustomerRequest customerRequest){
		Customer customer = customerService.updateCustomer(customerId, customerRequest);
		return ResponseEntity.ok().body(customer);
	}
	// counts number of customer in database
			@GetMapping(value="/count")
			public ResponseEntity<?> countCustomer(){
				logger.info("countcustomer api stated");
				int custCount = customerService.countCustomer();
				logger.info("number of customers in the database:"+custCount);
				return ResponseEntity.ok().body(custCount);
			}
			// find customer by customerId but uses our own user defined query
			@GetMapping(value="/findbyidwithquery/{customerId}")
			public ResponseEntity<?> findCustomerByIdWithQuery(@PathVariable("customerId") Long customerId){
				Customer customer = customerService.findCustomerByIdQuery(customerId);
				return ResponseEntity.ok().body(customer);
			}
}
