package com.original.ecommerce.controller;

import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.original.ecommerce.entity.Product;
import com.original.ecommerce.service.CategoryService;
import com.original.ecommerce.service.ProductService;

@Controller
public class HomeController {

   
    private ProductService productService;
    private final CategoryService categoryService;
    HomeController(ProductService productService,CategoryService categoryService){
    	this.productService=productService;
    	this.categoryService=categoryService;
    }

    @GetMapping("/")
    public String home(Model model) {
        // Get all products
        List<Product> products = productService.getAllProducts();

        // Add products to the model
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());
        // Return the view name for home page (index.html)
        return "index";
    }
   


}
