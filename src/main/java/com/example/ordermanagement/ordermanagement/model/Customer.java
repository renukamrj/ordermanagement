package com.example.ordermanagement.ordermanagement.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//getter and setters generate
@Entity//this refers that entity class
@Table(name="customer")// this is the database 
@AllArgsConstructor//all fields constructors
@NoArgsConstructor//no field constructors
public class Customer {
	@Id //refers primary key
	@Column(name="customer_Id",nullable=false)//column corresponds to table column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long customerId;
	@Column(name="customer_name",nullable=false)//column corresponds to table column
	private String customerName;
	@Column(name="email",unique=true)
	private String email;
	@Column(name="mobile")
	private String mobile;
	@Column(name="address")
	private String address;
	//@OneToMany(mappedBy="customer",fetch = FetchType.EAGER)
	//private Set<Order> order;

}
