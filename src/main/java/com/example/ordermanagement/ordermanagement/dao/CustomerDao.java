package com.example.ordermanagement.ordermanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ordermanagement.ordermanagement.model.Customer;

@Repository // it does database operations
// JpaRepository has in built methods for database operations, we are inheriting it and using in our application
public interface CustomerDao extends JpaRepository<Customer, Long>{
	// @query - annotation used to write user defined queries, nativeQuery=true tells us that this we have our own query to run
		// value will be query statement and it returns int output
		@Query(nativeQuery = true, value="select count(*) from customer")
		public int countCustomer();
		
		
		// this will get customer from the database with customerId
		@Query(nativeQuery = true, value="select * from customer where customer_id = :customerId")
		public Customer findByCustomerId(Long customerId);

}
