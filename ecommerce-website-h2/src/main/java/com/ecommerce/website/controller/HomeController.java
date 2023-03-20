package com.ecommerce.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.website.service.CategoryService;
import com.ecommerce.website.service.ProductService;

@Controller
public class HomeController {
	
	//Injecting dependencies 
	@Autowired
	CategoryService categoryService; 
	
	@Autowired
	ProductService productService;
	
	
	//This method maps for both the / and /home endpoints; it shows the index (home) page
	//@return the index page
	@GetMapping({"/", "/home"}) 
	public String home(Model model) {
		
		return "index"; 
	}
	
	//This method maps for /shop endpoint. It shows the shop.html page, which initially shows all products for sale
	//The method is @GetMapping because you are getting information from database
	//@param model is used to hold all categories and products from database; this gets sent to shop.html
	//@return the shop page 
	@GetMapping("/shop") 
	public String shop(Model model) {
		
		//Using models to hold all categories and products; these will be sent to shop.html
		//All categories will be shown on the side
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("products", productService.getAllProduct());
		
		//Return shop page, which shows all categories and products 
		return "shop"; 
	}
	
	//This method maps for /shop/category/{id} endpoint. It shows the shop.html page, but this time, it shows all products
		//from a certain category 
	//The method is @GetMapping because you are getting information from database
	//@param model is used to hold all categories and products (from the specified category id) from database; this gets
		//sent to shop.html
	//@return the shop page 
	@GetMapping("/shop/category/{id}") 
	public String shopByCategory(Model model, @PathVariable int id) {
		
		//Using models to hold all categories and products; these will be sent to shop.html
			//All categories will be shown on the side
		model.addAttribute("categories", categoryService.getAllCategories());
		//Holding all products from the specified category id 
		model.addAttribute("products", productService.getAllProductsByCategoryId(id));
		
		//Return shop page, which shows all categories and category-specific products 
		return "shop"; 
	}
	
	//This method maps for /shop/viewproduct/{id} endpoint. It shows the viewProduct.html page, which is a page that shows
		//a specific product based on the id
	//The method is @GetMapping because you are getting information from database
	//@param model is used to hold the product that needs to be shown 
	//@return the viewProduct page to show the product
	@GetMapping("/shop/viewproduct/{id}") 
	public String viewProduct(Model model, @PathVariable long id) {
		
		//Calling productService to get the product by the id, and putting that into the model to be sent to viewProduct
		model.addAttribute("product", productService.getProductById(id).get());
		
		//Show the product
		return "viewProduct"; 
	}
}












