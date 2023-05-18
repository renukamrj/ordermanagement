package com.example.ordermanagement.ordermanagement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.ordermanagement.ordermanagement.model.Product;
import com.example.ordermanagement.ordermanagement.request.ProductRequest;
import com.example.ordermanagement.ordermanagement.response.ProductResponse;
import com.example.ordermanagement.ordermanagement.service.ProductService;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

	@InjectMocks // injects all methods present in productController
	private ProductController productController;
	
	@Mock
	private ProductService productService;
	
	// optional
	@BeforeEach // before execution of each test case
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	// test data request that will be sent by postman or UI
	private ProductRequest getProductRequest() {
		ProductRequest productRequest = new ProductRequest();
		productRequest.setProductName("smartwatch");
		productRequest.setDescription("teststttt");
		productRequest.setPrice("5000");
		productRequest.setProductQuantity("1");
		return productRequest;
		
	}
	// response back from database
	private Product getProduct() {
		Product product = new Product();
		product.setProductId(123L);
		product.setProductName("smartwatch");
		product.setDescription("teststttt");
		product.setPrice("5000");
		product.setProductQuantity("1");
		return product;
	}
	
	@Test
	public void saveProductTestPositive() {
		// if we are calling other class inside that method and returning something then use Mockito.when().thenreturn()
		// Only inside mockito we can use Mockito.any()
		Mockito.when(productService.createProduct(Mockito.any())).thenReturn(getProduct());
		// call the actual class of main productcontroller by passing actual parameters
		ResponseEntity<?> response = productController.saveProduct(getProductRequest());
		// assert your expected and actual result
		assertEquals(getProduct(), response.getBody());
	}
	
	@Test
	public void getProductByIdTesPositive() {
		Mockito.when(productService.findProductById(Mockito.any())).thenReturn(Optional.of(getProduct()));
		ResponseEntity<?> response = productController.getProductById(123L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void fetchAllProductTestPositive() {
		List<Product> productList = new ArrayList<>();
		productList.add(getProduct());
		Mockito.when(productService.findAllProduct()).thenReturn(productList);
		ResponseEntity<?> response = productController.fetchAllProduct();
		assertEquals(productList, response.getBody());
	}
	
	@Test
	public void deleteProductByIdTestPositive() {
		// delete is not returning anything from other class hence we are not doing mockito.when
		ResponseEntity<?> repsponse = productController.deleteProductById(123L);
		assertEquals("The Product with id 123 got deleted!!!", repsponse.getBody());
	}
	
	@Test
	public void updateProductTestPositive() {
		Mockito.when(productService.updateProduct(Mockito.any(), Mockito.any())).thenReturn(getProduct());
		ResponseEntity<?> response = productController.updateProduct(123L, getProductRequest());
		assertEquals(getProduct(), response.getBody());
	}
	
	@Test
	public void getAllProductWithPaginationTestPositive() {
		List<Product> productList = new ArrayList<>();
		productList.add(getProduct());
		Mockito.when(productService.getProductListWithPagination(Mockito.any(), Mockito.any())).thenReturn(productList);
		ResponseEntity<?> response = productController.getAllProductWithPagination(1, 1);
		assertEquals(productList, response.getBody());
	}
	
	@Test
	public void saveAllProductsTestPositive() {
		List<Product> productList = new ArrayList<>();
		productList.add(getProduct());
		List<ProductRequest> productRequests = new ArrayList<>();
		productRequests.add(getProductRequest());
		Mockito.when(productService.createListProduct(Mockito.any())).thenReturn(productList);
		ResponseEntity<?> response = productController.saveAllProducts(productRequests);
		assertEquals(productList, response.getBody());
	}
	
	@Test
	public void getProductByIdWithResponseBodyTestPositive() {
		Mockito.when(productService.findProductById(Mockito.any())).thenReturn(Optional.of(getProduct()));
		ProductResponse response = productController.getProductByIdWithResponseBody(123L);
		assertEquals(getProduct(), response.getData());
	}
	
	// for exception write normal test case for positive scenario and then 
	// instead of thenreturn we need to use thenthrow
	@Test
	public void getProductByIdWithResponseBodyTestException() {
		Mockito.when(productService.findProductById(Mockito.any())).thenThrow(new RuntimeException());
		ProductResponse response = productController.getProductByIdWithResponseBody(123L);
		assertEquals(null, response.getData());
	}
}
