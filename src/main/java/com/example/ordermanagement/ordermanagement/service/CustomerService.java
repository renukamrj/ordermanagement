package com.example.ordermanagement.ordermanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ordermanagement.ordermanagement.dao.CustomerDao;
import com.example.ordermanagement.ordermanagement.dao.OrderDao;
import com.example.ordermanagement.ordermanagement.model.Customer;
import com.example.ordermanagement.ordermanagement.request.CustomerRequest;

@Service // it does business logic
public class CustomerService {
	
	@Autowired // injecting the customerdao class and all its methods into customerservice
	private CustomerDao customerDao;
	@Autowired
	private OrderDao orderDao;
	/*
	 * CRUD operations - create, read/retrieve, update, delete
	 * create - createCustomer
	 * read - findCustomerById, findAllCustomer
	 * update - updateCustomer
	 * delete - deleteCustomerById
	 */
	
	public Customer createCustomer(CustomerRequest customerRequest) {
		// take all the request fields from customerrequest and set it in customer object
		// then save customer object and return it
		Customer customer = new Customer();
		customer.setCustomerName(customerRequest.getCustomerName());
		customer.setEmail(customerRequest.getEmail());
		customer.setMobile(customerRequest.getMobile());
		customer.setAddress(customerRequest.getAddress());
		
		customer = customerDao.save(customer);

		return customer;
	}
	
	public Optional<Customer> findCustomerById(Long customerId) {
		// take customerId as input and finds that customer in table if present else null
		Optional<Customer> customer = customerDao.findById(customerId);
		return customer;
	}
	
	public List<Customer> findAllCustomer(){
		// fetch all the list of customers from database table customer
		List<Customer> customers = customerDao.findAll();
		return customers;
	}
	
	public void deleteCustomerById(Long customerId) {
		// takes customerId as input and deletes it in database table customer
		customerDao.deleteById(customerId);
	}
	
	public Customer updateCustomer(Long customerId, CustomerRequest customerRequest) {
		// 1. get old customer values from database using findById and store it in customer object
		// 2. set customer object old data to customerrequest new data
		// 3. save customer object to database
		
		Customer updatedCustomer = null;
		// customerRequest contains new data that needs to be updated coming from front end
		// customer object contains old data from the database that we got using findById 
		// remove old data from customer and update it with new data of customerRequest
		Optional<Customer> customer = customerDao.findById(customerId);// customer data in database which is already there - old data
		customer.get().setCustomerName(customerRequest.getCustomerName());// set customer object which has old data to new data present in customerrequest object
		customer.get().setEmail(customerRequest.getEmail());
		customer.get().setMobile(customerRequest.getMobile());
		customer.get().setAddress(customerRequest.getAddress());
		
		// save updated customer object back to database and return result
		updatedCustomer = customerDao.save(customer.get()); 
		return updatedCustomer;
	}
	// calling countCustomer() in customerDao and returning int result
		public int countCustomer() {
			int countCust = customerDao.countCustomer();
			return countCust;
		}
		
		public Customer findCustomerByIdQuery(Long customerId) {
			// take customerId as input and finds that customer in table if present else null
			Customer customer = customerDao.findByCustomerId(customerId);
			return customer;
		}

}
