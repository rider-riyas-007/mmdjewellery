package com.original.ecommerce.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.original.ecommerce.entity.Category;
import com.original.ecommerce.entity.Product;
import com.original.ecommerce.service.CategoryService;
import com.original.ecommerce.service.ProductService;
@Controller
@RequestMapping("/products")
public class ProductController {
	 
	    private final ProductService productService;
	    private final CategoryService categoryService;
	    
	    public ProductController(ProductService productService,CategoryService categoryService) {
	    	this.categoryService=categoryService;
	    	this.productService=productService;
	    }
	    

	@GetMapping("/{id}")
	    public String productDetails(@PathVariable("id") Long id, Model model) {
	        Product product = productService.getProductById(id);
	        if (product == null) {
	            return "redirect:/?error=productNotFound";
	        }
	        model.addAttribute("product", product);
	        return "product-details";
	    }
	@GetMapping("/categories/{categoryId}")
	public String getProductsByCategory(@PathVariable Long categoryId, Model model) {
	    Category category = categoryService.getCategoryById(categoryId);
	    List<Product> products = productService.getProductsByCategoryId(categoryId);
	    
	    model.addAttribute("category", category);
	    model.addAttribute("products", products);
	    return "category-products"; // Thymeleaf template name
	}
	

}
