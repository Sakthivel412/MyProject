package com.flipkart.controller;

import java.util.Collection;
import java.util.Map;
import com.flipkart.model.Product;
import com.flipkart.service.ProductService;
import com.flipkart.service.ProductServiceImplementation;

public class ProductController {
	
	private final ProductService productService = new ProductServiceImplementation();

	public Map<Integer, Product> get(int pageNumber, final int productsInPage) {
		return productService.get(pageNumber, productsInPage);
	}
	
    public Collection<Product> get(final String name, final int productsInPage, int pageNumber) {
    	return productService.get(name, productsInPage, pageNumber);
    }
    
	public Collection<Product> sort(final String name, final int productsInPage, int pageNumber, final String sortOrder) {
		return productService.sort(name, productsInPage, pageNumber, sortOrder);		
	}
	
	 public void initializeProducts() {
		 productService.initializeProducts();
	 }
	 public Collection<Product> sort(final int productsInPage, int pageNumber, final String sortOrder) {
		 return productService.sort(productsInPage, pageNumber, sortOrder);
	 }
}
 
