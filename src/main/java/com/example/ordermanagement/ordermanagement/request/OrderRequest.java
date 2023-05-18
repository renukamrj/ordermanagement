package com.example.ordermanagement.ordermanagement.request;

import lombok.Data;

@Data
public class OrderRequest {

	private String productPurchaseQuantity;
	private String orderStatus;
	private String deliveryAddress;
	private Long productId;
	private Long customerId;
}


