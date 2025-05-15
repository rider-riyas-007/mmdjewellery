package com.original.ecommerce.controller;

import java.io.IOException;
import java.nio.file.Files;
import  java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.original.ecommerce.entity.Orders;
import com.original.ecommerce.entity.Product;
import com.original.ecommerce.repository.CategoryRepository;
import com.original.ecommerce.repository.OrderRepository;
import com.original.ecommerce.repository.ProductRepository;
import com.original.ecommerce.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private final UserRepository userRepository;
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	
	public AdminController(UserRepository userRepository,OrderRepository orderRepository,ProductRepository productRepository,CategoryRepository categoryRepository) {
		this.orderRepository=orderRepository;
		this.productRepository=productRepository;
		this.userRepository=userRepository;
		this.categoryRepository=categoryRepository;
	}
	

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }
    
   

//    @GetMapping("/orders")
//    public String manageOrders(Model model) {
//        model.addAttribute("orders", orderRepository.findAll());
//        return "admin/orders";
//    }

    @GetMapping("/products")
    public String editProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "admin/products";
    }
    @GetMapping("/products/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/product-form";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/product-form";
    }

    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute("product") Product product,
          @RequestParam("imageUrlInput") String imageUrlInput) throws IOException {

         if (!imageUrlInput.isBlank()) {
            product.setImageUrl(imageUrlInput);  
         }
 
         productRepository.save(product);
         return "redirect:/admin/products";
      }


    
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
    	 productRepository.findById(id).ifPresent(product -> {
    	        // Delete image file if it's a local file
    	        String imageUrl = product.getImageUrl();
    	        if (imageUrl != null && imageUrl.startsWith("/images/products/")) {
    	            String fileName = imageUrl.replace("/images/products/", "");
    	            Path imagePath = Paths.get(System.getProperty("user.dir") + "/uploads/images/products/" + fileName);
    	            try {
    	                Files.deleteIfExists(imagePath);
    	            } catch (IOException e) {
    	                e.printStackTrace(); // You can log this properly
    	            } 
    	        }
    	        productRepository.delete(product); 
    	    });
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/users")
    public String userManagement(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/orders")
    public String orderManagement(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "admin/orders";
    }

    @PostMapping("/orders/update-status/{id}")
    public String updateOrderStatus(@PathVariable Long id,
                                   @RequestParam String status) {
        Orders order = orderRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));
        order.setStatus(status);
        orderRepository.save(order);
        return "redirect:/admin/orders";
    }

}
