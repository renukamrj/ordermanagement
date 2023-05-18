package com.example.ordermanagement.ordermanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ordermanagement.ordermanagement.model.Product;

@Repository // it does database operations
//JpaRepository has in built methods for database operations, we are inheriting it and using in our application
public interface ProductDao extends JpaRepository<Product, Long> {

}
