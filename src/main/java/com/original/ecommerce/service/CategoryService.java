package com.original.ecommerce.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.original.ecommerce.entity.Category;
import com.original.ecommerce.repository.CategoryRepository;
import com.original.ecommerce.repository.ProductRepository;
@Service
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	private final ProductRepository productRepository;
	
	CategoryService(CategoryRepository categoryRepository,ProductRepository productRepository){
		
		this.categoryRepository=categoryRepository;
		this.productRepository=productRepository;
		
		
		
	}
	
	public List<Category> getAllCategories(){
		
		return categoryRepository.findAll();
	}
	
	
		public Category getCategoryById(long id) {
		    return categoryRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
		}
	

	

}
