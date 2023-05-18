package com.example.ordermanagement.ordermanagement.request;

import lombok.Data;

@Data
public class CustomerRequest {

	//Here we need to have all the fields/columns of customer that is coming from front-end request
	
	private String customerName;
	private String email;
	private String mobile;
	private String address;
}


