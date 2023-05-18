package com.example.ordermanagement.ordermanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.ordermanagement.ordermanagement.dao.ProductDao;
import com.example.ordermanagement.ordermanagement.model.Product;
import com.example.ordermanagement.ordermanagement.request.ProductRequest;



@Service
public class ProductService {

	@Autowired // injecting the productDao class and all its methods into productservice
	private ProductDao productDao;
	
	/*
	 * CRUD operations - create, read/retrieve, update, delete
	 * create - createProduct
	 * read - findProductById, findAllProduct
	 * update - updateProduct
	 * delete - deleteProductById
	 */
	
	public Product createProduct(ProductRequest productRequest) {
		// take all the request fields from productRequest and set it in product object
		// then save product object and return it
		Product product = new Product();
		product.setProductName(productRequest.getProductName());
		product.setProductQuantity(productRequest.getProductQuantity());
		product.setDescription(productRequest.getDescription());
		product.setPrice(productRequest.getPrice());
		
		product = productDao.save(product);

		return product;
	}
	
	public Optional<Product> findProductById(Long productId) {
		// take productId as input and finds that product in table if present else null
		Optional<Product> product = productDao.findById(productId);
		return product;
	}
	
	public List<Product> findAllProduct(){
		// fetch all the list of products from database table product
		List<Product> products = productDao.findAll();
		return products;
	}
	
	public void deleteProductById(Long productId) {
		// takes productId as input and deletes it in database table product
		productDao.deleteById(productId);
	}
	
	public Product updateProduct(Long productId, ProductRequest productRequest) {
		// 1. get old product values from database using findById and store it in product object
		// 2. set product object old data to productRequest new data
		// 3. save product object to database
		
		Product updatedProduct = null;
		// productRequest contains new data that needs to be updated coming from front end
		// product object contains old data from the database that we got using findById 
		// remove old data from product and update it with new data of productRequest
		Optional<Product> product = productDao.findById(productId);// product data in database which is already there - old data
		product.get().setProductName(productRequest.getProductName());// set product object which has old data to new data present in productRequest object
		product.get().setProductQuantity(productRequest.getProductQuantity());
		product.get().setPrice(productRequest.getPrice());
		product.get().setDescription(productRequest.getDescription());
		
		// save updated product object back to database and return result
		updatedProduct = productDao.save(product.get()); 
		return updatedProduct;
	}
	
	//When large data are present in database we need to fetch the records in certain numbers
		// Pagination helps us to do this.
		// We need two parameters pageNumber - number of page you want to see and page size - how many records needs to be stored in each page
		// pageSize - 10
		// total number of records - 36
		// 0th page - 1-10
		// 1st page - 11-20
		// 2nd page - 21-30
		// 3rd page - 31-36
		public List<Product> getProductListWithPagination(Integer pageNumber, Integer pageSize){
			// call findAll method from productDao and pass pageNumber and pagesize as parameter which returns Page of product
			Page<Product> productPage = productDao.findAll(PageRequest.of(pageNumber, pageSize,Sort.by("price").ascending()));
			// adding each product in productPage to productList and return it
			List<Product> productList = new ArrayList<>();
			for(Product product : productPage) {
				productList.add(product);
			}
			return productList;
		}
		//this method will take a list of productRequest as an input and calls createProduct method by passing single productRequest into it
		public List<Product> createListProduct(List<ProductRequest> prodList){
			List<Product> productList =new ArrayList<>();
			for(ProductRequest productRequest : prodList) {
				productList.add(createProduct(productRequest));
				
			}
			return productList;
		}
	
}
