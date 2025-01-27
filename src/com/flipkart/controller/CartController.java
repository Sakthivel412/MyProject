package com.flipkart.controller;

import java.util.Map;
import com.flipkart.service.CartImplementation;
import com.flipkart.model.Product;
import com.flipkart.model.User;

public class CartController {
	
	private final CartImplementation cartImpl = new CartImplementation();
		
	public boolean add(final int productId, final User user) {
		return cartImpl.add(productId, user);	 
	}
	 
	public Map<Integer, Product> view(final User user) {
	    return cartImpl.view(user);
	}
	 
    public void remove() {
    	cartImpl.remove();
    }
}
 
