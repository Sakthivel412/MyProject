package com.flipkart.controller;

import java.util.Map;
import com.flipkart.model.Product;
import com.flipkart.model.User;
import com.flipkart.service.OrderServiceImpl;

public class OrderController {
	
	private final OrderServiceImpl orderService = new OrderServiceImpl();
	
	public Map<Integer, Product> placeOrder(final Map<Integer, Product> products, final Integer cartId, final int quantity) {
		return  orderService.placeOrder(products, cartId, quantity);
	}
	
    public Map<Integer, Product> buy(final int productId, final User user, final int quantity) {
	    return orderService.buy(productId, user, quantity);
    }
}
