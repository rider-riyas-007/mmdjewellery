package com.original.ecommerce.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.original.ecommerce.entity.CartItem;
import com.original.ecommerce.entity.Product;
import com.original.ecommerce.entity.User;
import com.original.ecommerce.repository.CartItemRepository;
import com.original.ecommerce.repository.ProductRepository;
import com.original.ecommerce.repository.UserRepository;
import com.original.ecommerce.service.UserService;

@Controller
@RequestMapping("/cart")
public class CartController {
	private final UserRepository userRepository;
	private final CartItemRepository cartItemRepository;
	private final ProductRepository productRepository;
	private final UserService userService;
	public CartController(UserService userService,UserRepository userRepository,CartItemRepository cartItemRepository,ProductRepository productRepository) {
		this.userRepository=userRepository;
		this.cartItemRepository=cartItemRepository;
		this.productRepository=productRepository;
		this.userService=userService;
		
	}
	
	@GetMapping
    public String viewCart(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);

        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        model.addAttribute("cartItems", cartItems);

        return "cart";
    }
	
	@GetMapping("/add")
	public String addToCart(@RequestParam("productId") long productId,@RequestParam("quantity") int quantity, Principal principal) {
	    String email = principal.getName();
	    Optional<User> userOptional = userRepository.findByEmail(email);

	    if (userOptional.isPresent()) {
	        User user = userOptional.get();
	        Product product = productRepository.findById(productId).orElse(null);

	        if (product == null) {
	            return "redirect:/products"; // product not found
	        }

	        // Check if cart item already exists for user & product
	        List<CartItem> items = cartItemRepository.findByUserAndProduct(user, product);
	        if (!items.isEmpty()) {
	            CartItem existingItem = items.get(0);
	            existingItem.setQuantity(existingItem.getQuantity() + 1);
	            cartItemRepository.save(existingItem);
	        } else {
	            CartItem cartItem = new CartItem();
	            cartItem.setUser(user);
	            cartItem.setProduct(product);
	            cartItem.setQuantity(quantity);
	            cartItemRepository.save(cartItem);
	        }


	        return "redirect:/cart"; // redirect to cart page
	    }

	    return "redirect:/login"; // if user not found, redirect to login
	}

	

}
