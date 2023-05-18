package com.example.ordermanagement.ordermanagement.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // generate getter and setters
@Entity // refers that this is an entity class
@Table(name="order") // this is the table customer in the database
@AllArgsConstructor // all fields constructors
@NoArgsConstructor // no field constructors or default constructor
public class Order {
	
	@Id //refers to primary key of the table
	@Column(name="order_id", nullable=false)// column corresponds to table column
	@GeneratedValue(strategy = GenerationType.AUTO)// automatically generates id value
	private Long orderId;
	
	@Column(name="product_purchase_quantity", nullable=false)
	private String productPurchaseQuantity;
	
	@Column(name="product_totalprice", nullable=false)
	private String productTotalPrice;
	
	@Column(name="order_status", nullable=false)
	private String orderStatus;
	
	@Column(name="deleivery_address", nullable=false)
	private String deliveryAddress;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="product_id", nullable = false)
	private Product product;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="customer_id", nullable = false)
	private Customer customer;

}
