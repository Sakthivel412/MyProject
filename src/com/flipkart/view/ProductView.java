package com.flipkart.view;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import com.flipkart.model.User;
import com.flipkart.model.Product;
import com.flipkart.controller.UserController;
import com.flipkart.controller.CartController;
import com.flipkart.controller.OrderController;
import com.flipkart.controller.ProductController;

public class ProductView {
    private final ProductController productControl = new ProductController();
    private final CartController cartController = new CartController();
    private final UserController userController = new UserController();
    private final OrderController orderController = new OrderController();
    private final Scanner scanner = new Scanner(System.in);
    final int productsInPage = 3;
    int pageNumber = 1;
    
    public void viewDetails() {
       
        while (true) {
        		 System.out.print("1. Sign Up\n2. Log In\n3. Exit\nEnter your choice: ");
                 final int choice = scanner.nextInt();

                 switch (choice) {
                 case 1 ->  signUp();
           
                 case 2 ->  logIn();
               
                 case 3 -> {
                         System.out.println("Exiting...");
                         
                         return;
                 }
                 
                 default -> System.out.println("Invalid choice. Please try again.");
                 }
        }
    }

    private void signUp() {
        System.out.print("Enter EmailId: ");
        final String emailId = scanner.next();
        
        if (!isValidEmail(emailId)) {
            System.out.println("Invalid Emailid format.");
            
            return;
        }
        System.out.print("Enter Password: ");
        final String password = scanner.next();
        
        if (!isValidPassword(password)) {
            System.out.println("Password must be at least 6 characters long and contain at least one special character.");
            
            return;
        }
        System.out.print("Enter Name: ");
        final String name = scanner.next();
        
        if (!isValidName(name)) {
            System.out.println("Invalid Name. It should only contain letters and spaces and be at least 2 characters long.");
            
            return;
        }
        System.out.print("Enter Mobile Number: ");
        final String mobileNumber = scanner.next();
        
        if (!isValidMobileNumber(mobileNumber)) {
            System.out.println("Invalid mobile number format.");
            
            return;
        }
        System.out.print("Enter Address: ");
        final String address = scanner.nextLine();
        
        if (!isValidAddress(address)) {
            System.out.println("Invalid Address. It should be at least 10 characters long and contain alphanumeric characters, spaces, and special characters like commas or periods.");
            
            return;
        }
        final User user = new User(emailId, password, name, mobileNumber, address);
        final User signedUser = userController.signUp(user);
        
        if(Objects.nonNull(signedUser)) {
            System.out.println("Sign Up successful!");
            viewProducts(pageNumber, signedUser);
        } else {
        	System.out.println("Email already exits");	
        }
    }
    
    private boolean isValidEmail(final String email) {
        final String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(email);
        
        return matcher.matches();
    }
      
    private boolean isValidPassword(final String password) {
        final String regex = "^(?=.*[!@#$%^&*(),.?\":{}|<>]).{6,}$";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(password);
        
        return matcher.matches();
    }
    
    private boolean isValidName(final String name) {
        final String regex = "^[a-zA-Z\\s]{2,}$";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(name);
        
        return matcher.matches();
    }
 
    private boolean isValidMobileNumber(final String mobileNumber) {
        final String regex = "^[0-9]{10}$";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(mobileNumber);
        
        return matcher.matches();
    }
    
    private boolean isValidAddress(final String address) {
        final String regex = "^[a-zA-Z0-9\\s,.-]{10,}$";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(address);
        
        return matcher.matches();
    }


    private void logIn() {
        System.out.println("Enter EmailId: ");
        final String emailId = scanner.next();

        System.out.println("Enter Password: ");
        final String password = scanner.next();
        final User user = userController.logIn(emailId, password);

        if (Objects.nonNull(user)) {
            System.out.println("Logged in successfully!");
            viewProducts(productsInPage, user);
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private void UserAction(final int productsInPage, final User user) {
        while (true) {
        	System.out.print("1.search\n2.Cart\n3.Settings\n4.Profile\n5.Back\n.Enter your choice: ");
        	final int option = scanner.nextInt();
        	
        	switch (option) {
        	case 1 -> {
        		System.out.print("Enter the product name :");
             	final String name = scanner.next();
             	final Collection<Product> product = productControl.get(name, productsInPage, pageNumber);
             	
             	
               	if (Objects.nonNull(product)) {
                 	System.out.println(product);
             		System.out.print("1.Add to Cart\n2.Buy now\n3.FilterPrice\n4.Exit\nEnter your choice: ");
             		final int choice = scanner.nextInt();
             		
             		switch (choice) {
                    case 1 ->{
                    	System.out.print("Enter the ProductId to add into the cart :");
                    	final int productId = scanner.nextInt();
                    	
                        addToCart(productId, user);
                    }
                    
                    case 2 ->{
                    	System.out.print("Enter the ProductId to Order :");
                    	final int productId = scanner.nextInt();

                    	buy(productId, user);
                    }
                    
                    case 3-> {
                    	while (true) {
                    	System.out.print("1.low to high\n2.high to low\n3.exit\n.Enter your choice");
                    	final int select = scanner.nextInt();
                 
                    	switch (select) {
                    	case 1 -> {
                    		final String sortOrder = "Asc";
                    		final Collection<Product> products = productControl.sort(name, productsInPage, pageNumber, sortOrder);
                    		
                    		System.out.println(products);	
                    	}
                    	
                    	case 2 -> {
                    		final String sortOrder = "Desc";
                    		final Collection<Product> products = productControl.sort(name, productsInPage, pageNumber, sortOrder);
                    		
                    		System.out.println(products);
                    	}
                    	
                    	 case 3 -> {
                             System.out.println("Returning to main menu..."); 
                             break;
                         }

                    	}	
                    }
                    }
                    
                    case 4 -> {
                        System.out.println("Returning to main menu...");
                        break;
                    } 
                    default -> System.out.println("Invalid choice. Please try again.");
             		}             		   
             	} else {
             		System.out.println("No Produccts found");
             	}
        	 }	 
             case 2 -> viewCart(productsInPage, user);
         
             case 3 -> {
             	System.out.print("1.Change Password\n2.cahnge Mobile Number\n3.Change address\n4change Name\n5.Exit\nEnter your choice");
             	final int choice =scanner.nextInt();
             	
             	switch (choice) {
             	
             	case 1 -> changePassword();
               
                case 2 -> changeMobileNumber();
                   
                case 3 -> changeAddress();
        
             	case 4 -> changeName();
             	 
             	case 5 ->  {
                    System.out.println("Returning to main menu...");
                    break;
             	}
             	}
             }
             
             case 4 -> {
            	 final User userDetail =userController.getUserDetails();
            	 
            	 if (Objects.nonNull(user)) {
            		 System.out.println("Profile :"+ " "+ userDetail);
            	 } else {
            		 System.out.println("Unable to find Profile");
            	 } 	 
             }
                                
             case 5 -> {
                 System.out.println("Returning to main menu...");
                 
                 return;
             }
             
             default -> System.out.println("Invalid choice. Please try again.");
         }
     }    	
   }
 
	private void changeName() {
    	System.out.print("Enter your NewName:");
    	final String name = scanner.next();
    	final int rowsUpdated =userController.changeName(name);
    	
    	if (rowsUpdated > 0) {
    		System.out.println("your Name has been updated");
    	} else {
    		System.out.println("Error Occured Name not updated");
    	}			
	}

	private void changeAddress() {
		System.out.print("Enter your NewAddress:");
		scanner.nextLine();
    	final String newAddress = scanner.nextLine();
    	final int rowsUpdated =userController.changeAddress(newAddress);
    	
    	if (rowsUpdated > 0) {
    		System.out.println("your Address has been updated");
    	} else {
    		System.out.println("Error Occured Address not updated");
    	}			
	}

	private void changeMobileNumber() {
		System.out.print("Enter your NewMobileNumber:");
    	final String newMobieNumber = scanner.next();
    	final int rowsUpdated =userController.changeMobileNumber(newMobieNumber);
    	
    	if (rowsUpdated > 0) {
    		System.out.println("your MobileNumber has been updated");
    	} else {
    		System.out.println("Error Occured MobileNumber not updated");
    	}			
	}

	private void changePassword() {
		System.out.print("Enter your NewPassword:");
    	final String newPassword = scanner.next();
    	final int rowsUpdated =userController.changePassword(newPassword);
    	
    	if (rowsUpdated > 0) {
    		System.out.println("your Password has been updated");
    	} else {
    		System.out.println("Error Occured Password not updated");
    	}			
	}
	
	private void viewProducts(final int productsInPage, final User user) {
		productControl.initializeProducts();
	    while (true) {
	        final Map<Integer, Product> products = productControl.get(pageNumber, productsInPage);

	        if (products.isEmpty()) {
	            System.out.println("No more products available.");
	        }

	        System.out.println(pageNumber + " " + products);
	        System.out.print("1. Next Page\n2. Previous Page\n3. Add to Cart\n4. Buy Now\n5. Filter Price\n6. Main Menu\n7.log out\n.Enter your choice: ");
	        final int choice = scanner.nextInt();

	        if (1 == choice) {
	            pageNumber++;
	        } else if (2 == choice && pageNumber > 1) {
	            pageNumber--;
	        } else if (3 == choice) {
	            System.out.println("Enter the ProductId to add into the cart");
	            final int productId = scanner.nextInt();
	            
	            addToCart(productId, user);
	        } else if (4 == choice) {
	            System.out.println("Enter the ProductId to BuyNow");
	            final int productId = scanner.nextInt();
	            
	            buy(productId, user);
	        } else if (5 == choice) {
	            while (true) {
	                System.out.print("1. Low to High\n2. High to Low\n3. Back\nEnter your choice: ");
	                final int select = scanner.nextInt();

	                switch (select) {
	                    case 1 -> {
	                        final String sortOrder = "Asc";
	                        final Collection<Product> product = productControl.sort(productsInPage, pageNumber, sortOrder);
	                        
	                        System.out.println(product);
	                    }

	                    case 2 -> {
	                        final String sortOrder = "Desc";
	                        final Collection<Product> product = productControl.sort(productsInPage, pageNumber, sortOrder);
	                        
	                        System.out.println(product);
	                    }

	                    case 3 -> {
	                        System.out.println("Returning to product view...");
	                        
	                        break;  
	                    }

	                    default -> System.out.println("Invalid choice. Please try again.");
	                }  
	            }
	        } else if (6 == choice) {
	            System.out.println("Returning to main menu...");
	            UserAction(productsInPage, user);
	        } else if (7 == choice) {
	            System.out.println("logged out");
	            
	            return;
	        } else {
	        	System.out.println("Invalid choice. Please try again.");
	        }
	    }
	}
	
    private void addToCart(final int productId, final User user) {
        if (cartController.add(productId, user)) {
        	System.out.println("Product added to the cart");
        } else {
        	System.out.println("product not added");
        }         
    }

    private void viewCart(final int productsInPage, final User user) {
        while (true) {
            final Map<Integer, Product> products = cartController.view(user);

            if (products.isEmpty()) {
                System.out.print("No products in the cart.");
                break;
            }

            System.out.println("Cart : "+" "+products);
            System.out.print("1.Place Order\n2.Not Now\n3.Main Menu\nEnter your choice: ");
            final int choice = scanner.nextInt();
            
            if (1 == choice) {
                placeOrder(products);
            } else if (2 == choice) {
                System.out.print("Returning to main menu...");
            } else {
                break;
            }
        }
    }

    private void placeOrder(final Map<Integer, Product> products) {
    	System.out.print("Which cart do you want to order Enter the cartId:");
    	final Integer cartId = scanner.nextInt();
    	
    	System.out.print("How many products do you want?:");
        final int quantity = scanner.nextInt();
        final Map<Integer, Product> orderDetails = orderController.placeOrder(products, cartId, quantity);

        if (Objects.nonNull(orderDetails)) {
        	System.out.print("Order placed successfully Your products: " + orderDetails + "No of Quantity:" +  quantity);
            cartController.remove();   
        } else {
        	System.out.println("Cart is empty. Unable to place order.");
        }
    }
    
    private void buy(final int productId, final User user) {
   	    System.out.print("How many products do you want?:");
        final int quantity = scanner.nextInt();
        final Map<Integer, Product> orderDetails = orderController.buy(productId, user, quantity);

        if (Objects.nonNull(orderDetails)) {
            System.out.print("Order placed successfully Your products: " + orderDetails + "No of Quantity:" +  quantity);
        } else {
            System.out.println("Unable to place order.");
        }		
	}

}

