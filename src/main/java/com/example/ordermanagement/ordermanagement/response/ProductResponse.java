package com.example.ordermanagement.ordermanagement.response;

import com.example.ordermanagement.ordermanagement.model.Product;

import lombok.Data;

@Data
public class ProductResponse {
	// this class will be returned whenever we call getProductByIdWithResponseBody 
	// data will be the returned product 
	// message and code are for understanding it's success or failure
	private Product data;
	private String message;
	private String code;
	// 200 - success(ok)
	// 400 - badrequest
	// 404 - not found
	// 500 - internal server error
	// 201 - created
}
