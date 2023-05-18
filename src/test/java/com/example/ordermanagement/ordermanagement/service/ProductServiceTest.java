package com.example.ordermanagement.ordermanagement.service;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.example.ordermanagement.ordermanagement.dao.ProductDao;
import com.example.ordermanagement.ordermanagement.model.Product;
import com.example.ordermanagement.ordermanagement.request.ProductRequest;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

	@InjectMocks // injects all methods present in productController
	private ProductService productService;
	
	@Mock
	private ProductDao productDao;// we can mock interface but we cannot write test class for that
	
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
		public void createProductTest() {
			Mockito.when(productDao.save(Mockito.any())).thenReturn(getProduct());
			Product response = productService.createProduct(getProductRequest());
			assertEquals(getProduct(), response);
		}
		
		@Test
		public void findProductByIdTest() {
			Mockito.when(productDao.findById(Mockito.any())).thenReturn(Optional.of(getProduct()));
			Optional<Product> response = productService.findProductById(123L);
			assertEquals(getProduct(), response.get());
		}
		
		@Test
		public void findAllProductTest() {
			List<Product> productList = new ArrayList<>();
			productList.add(getProduct());
			Mockito.when(productDao.findAll()).thenReturn(productList);
			List<Product> response = productService.findAllProduct();
			assertEquals(getProduct(), response.get(0));
		}
		
		@Test
		public void deleteProductByIdTest() {
			productService.deleteProductById(123L);
		}
		
		@Test
		public void updateProductTest() {
			Mockito.when(productDao.findById(Mockito.any())).thenReturn(Optional.of(getProduct()));
			Mockito.when(productDao.save(Mockito.any())).thenReturn(getProduct());
			Product response = productService.updateProduct(123L, getProductRequest());
			assertEquals(getProduct(), response);
		}
		
		@Test
		public void createListProductTest() {
			List<ProductRequest> productRequestsList =  new ArrayList<>();
			productRequestsList.add(getProductRequest());
			Mockito.when(productDao.save(Mockito.any())).thenReturn(getProduct());
			List<Product> response = productService.createListProduct(productRequestsList);
			assertEquals(getProduct(), response.get(0));
		}
		
		//@Test
		public void getProductListWithPaginationTest() {
			//Page<Product> productPage = new PageImpl<>();
			//Mockito.when(productDao.findAll(PageRequest.of(1, 1,Sort.by("price").ascending()))).thenReturn(null)
			List<Product> response =productService.getProductListWithPagination(1, 1);
			assertEquals(getProduct(), response.get(0));
		}
}

