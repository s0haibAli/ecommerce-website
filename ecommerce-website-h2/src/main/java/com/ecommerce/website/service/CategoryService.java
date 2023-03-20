package com.ecommerce.website.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.website.model.Category;
import com.ecommerce.website.repository.CategoryRepository;

@Service
public class CategoryService {

	//Injecting dependency 
	@Autowired
	CategoryRepository categoryRepository;
	
	//This method calls categoryRepository to add a category to the database  
	//@param category is the category that is being added 
	public void addCategory(Category category) {
		
		//Saving the category to the database 
		categoryRepository.save(category);
	}
	
	//This method calls categoryRepository to retrieve a list of categories 
	//@return the list of categories  
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	
	//This method calls categoryRepository to remove a category by its id
	//@param id is the id of the category that needs to be deleted 
	public void removeCategoryById(int id) {
		
		//Deleting the category by id 
		categoryRepository.deleteById(id);	
	}
	
	//This method calls categoryRepository to get a category by its id (returns Optional<Category>
	//in case the category is not found) 
	//@param id is the id of the category that needs to be retrieved  
	//@return the category by its id 
	public Optional<Category> getCategoryById(int id) {
		
		//Returning the category by id 
		return categoryRepository.findById(id);
	}
	

}


