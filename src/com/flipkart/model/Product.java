package com.flipkart.model;

public class Product {
	
	private final int id;
	private final String name;
	private final double price;
	
	public Product(final int id, final String name, final double price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	@Override
	public String toString() {
	    return String.format("Product [id=%d, name=%s, price=%.2f]", id, name, price);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}	
}
 