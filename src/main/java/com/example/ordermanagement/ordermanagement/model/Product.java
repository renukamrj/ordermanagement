package com.example.ordermanagement.ordermanagement.model;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//getter and setters generate
@Entity//this refers that entity class
@Table(name="product")// this is the database 
@AllArgsConstructor//all fields constructors
@NoArgsConstructor//no field constructors
public class Product {

		@Id //refers primary key
		@Column(name="product_Id",nullable=false)//column corresponds to table column
		@GeneratedValue(strategy=GenerationType.AUTO)
		private Long productId;
		@Column(name="product_name",nullable=false)//column corresponds to table column
		private String productName;
		@Column(name="price")
		private String price;
		@Column(name="product_Quantity")
		private String productQuantity;
		@Column(name="description")
		private String description;
		//@OneToMany(mappedBy="product",fetch=FetchType.EAGER)
		//private Set<Order> order;

	}

	


