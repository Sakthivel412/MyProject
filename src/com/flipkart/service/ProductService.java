package com.flipkart.service;

import java.util.Collection;
import java.util.Map;
import com.flipkart.model.Product;

public interface ProductService {
	
	Collection<Product> get(final String name, final int productsInPage, int pageNumber);
	 
	Map<Integer, Product> get(int pageNumber, final int productsInPage);	

	Collection<Product> sort(final String name, final int productsInPage, int pageNumber, final String sortOrder);
	
    void initializeProducts();
    
    Collection<Product> sort(final int productsInPage, int pageNumber, final String sortOrder) ;
}
