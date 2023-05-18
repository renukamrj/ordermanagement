package com.example.ordermanagement.ordermanagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ordermanagement.ordermanagement.model.Product;
import com.example.ordermanagement.ordermanagement.request.ProductRequest;
import com.example.ordermanagement.ordermanagement.response.ProductResponse;
import com.example.ordermanagement.ordermanagement.service.ProductService;

@RestController // combination of @controller and @responsebody
@RequestMapping(value = "/oms/v1/product") // it maps web requests url/endpoint
public class ProductController {

	// from controller request goes to service and then to dao
	/*
	 * create - post, update - patch and put, delete - delete, read/retrieve - get
	 * 
	 * API - application programming interface - communicate between request and
	 * response REST API - representational state transfer API
	 */
	@Autowired
	private ProductService productService;

	// @RequestBody - it takes the input form the from end in json format and passes
	// it to api
	@PostMapping(value = "/save") // other way @RequestMapping(value="/save", method=RequestMethod.POST)
	public ResponseEntity<?> saveProduct(@RequestBody ProductRequest productRequest) {
		// call service method createProduct and return Product body in responseentity
		Product product = productService.createProduct(productRequest);
		return ResponseEntity.ok().body(product);
	}

	// @PathVariable - it will be specified in the url
	@GetMapping(value = "/find/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable("productId") Long productId) {
		// take ProductId from the url and call findProductById which is in service
		// class and return result
		Optional<Product> product = productService.findProductById(productId);
		return ResponseEntity.ok().body(product);

	}

	@GetMapping(value = "/findall")
	public ResponseEntity<?> fetchAllProduct() {
		// call findAllProduct in service class and return list of Products
		List<Product> productsList = productService.findAllProduct();
		return ResponseEntity.ok().body(productsList);
	}

	@DeleteMapping(value = "/delete/{productId}")
	public ResponseEntity<?> deleteProductById(@PathVariable("productId") Long productId) {
		// call deleteProductById method in service class and return nothing
		// but passing an explicit deleted message in responseentity
		productService.deleteProductById(productId);
		return ResponseEntity.ok().body("The Product with id " + productId + " got deleted!!!");
	}

	@PutMapping(value = "/update/{productId}")
	public ResponseEntity<?> updateProduct(@PathVariable("productId") Long productId,
			@RequestBody ProductRequest productRequest) {
		Product product = productService.updateProduct(productId, productRequest);
		return ResponseEntity.ok().body(product);
	}
	
	// @RequestParam - takes one parameter input from postman or UI
	@GetMapping(value = "/withpage")
	public ResponseEntity<?> getAllProductWithPagination(@RequestParam("pagenumber") Integer pageNumber,
			@RequestParam("pagesize") Integer pageSize) {
		List<Product> productList = productService.getProductListWithPagination(pageNumber, pageSize);
		return ResponseEntity.ok().body(productList);
	}
	
	@PostMapping(value="/saveList")
	public ResponseEntity<?> saveAllProducts(@RequestBody List<ProductRequest> productRequestList){
		// take list of productRequest as input and call createProductList inside service class
		List<Product> productList = productService.createListProduct(productRequestList);
		return ResponseEntity.ok().body(productList);
	}
	
	@GetMapping(value = "/findwithrespbody/{productId}")
	public ProductResponse getProductByIdWithResponseBody(@PathVariable("productId") Long productId) {
		// take ProductId from the url and call findProductById which is in service
		// class and return result
		// create productResponse object 
		// set all fields of productResponse with success or error details
		ProductResponse productResponse = new ProductResponse();
		try {
			Optional<Product> product = productService.findProductById(productId);
			productResponse.setData(product.get());
			productResponse.setMessage("Product is found!");
			productResponse.setCode(HttpStatus.OK.toString());
			return productResponse;
		} catch (Exception e) {
			productResponse.setData(null);
			productResponse.setMessage("Product is not found!");
			productResponse.setCode(HttpStatus.NOT_FOUND.toString());
			return productResponse;
		}
	}
}
