package com.ecommerce.website.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.website.dto.ProductDTO;
import com.ecommerce.website.model.Category;
import com.ecommerce.website.model.Product;
import com.ecommerce.website.service.CategoryService;
import com.ecommerce.website.service.ProductService;

@Controller
public class AdminController {
	
	//Variable to hold the exact location where files (images) need to be saved 
	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
	
	//Injecting dependency
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService; 
	
	//This method maps for /admin endpoint. It shows the adminHome.html page. The method is @GetMapping because you are getting information 
	//@return the adminHome page
	@GetMapping("/admin")
	public String adminHome() {
		
		//Display html page
		return "adminHome";
	}
	
	//This method maps for /admin/categories endpoint. It shows the categories.html page. The method is @GetMapping because you are getting information
	//@return the categories page 
	@GetMapping("/admin/categories")
	public String getCat(Model model) {
		
		model.addAttribute("categories", categoryService.getAllCategories());
		
		//Display html page
		return "categories";
	}
	
	//This method maps for /admin/categories endpoint. It shows the categoriesAdd.html page, but before that, it creates
	//a model that is sent to the html file via Thymeleaf. The categoriesAdd.html page is a form for adding a category
	//The method is @GetMapping because you are getting information
	//@param model will hold the category object so that the form can add information to it 
	//@return the categoriesAdd page 
	@GetMapping("/admin/categories/add")
	public String getCatAdd(Model model) {
		
		//Adding the model name "category", and creating a new Category object and putting it in the model
		model.addAttribute("category", new Category());
		
		//Display html page
		return "categoriesAdd";
	}
	
	//This method maps for /admin/categories endpoint. It allows the user to add a category to the list of categories
	//The method is @PostMapping because you are sending information
	//@param category gets its value from the ModelAttribute, which gets its value from the form
	//@return the categories page 
	@PostMapping("/admin/categories/add")
	public String postCatAdd(@ModelAttribute("category") Category category) {
		
		//Calling categoryService to add the category
		categoryService.addCategory(category);
		
		//Redirecting to page that shows all categories 
		return "redirect:/admin/categories";
	}
	
	//This method maps for /admin/categories/delete/{id} endpoint. It allows the user to delete a category by its id 
	//@param id gets its value from {id} in the endpoint 
	//@return the categories page 
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCat(@PathVariable int id) {
		
		//Calling categoryService to remove the category by id
        categoryService.removeCategoryById(id);

		//Redirecting to page that shows all categories 
		return "redirect:/admin/categories";
	}
	
	//This method maps for /admin/categories/update/{id} endpoint. It allows the user to update a category by its id 
	//The method is @GetMapping because you are getting information (category to update) 
	//@param id gets its value from {id} in the endpoint 
	//@param model is passed in because 
	//@return the categoriesAdd or 404 page 
	@GetMapping("/admin/categories/update/{id}")
	public String updateCat(@PathVariable int id, Model model) {
		
		//Calling categoryService to get the category by its id
		//Using Optional in case the category is not found 
		Optional<Category> category = categoryService.getCategoryById(id); 
		
		//If loop for if the id matches with a category from the database  
		if(category.isPresent()) {
			
			//Adding the category to a model so that its fields can pre-populate the form 
			model.addAttribute("category", category.get());
			
			//Returning categoriesAdd page
			return "categoriesAdd";
		}
		
		//Else block for if the category is not found 
		else {
			//Return 404 error page 
			return "404"; 
		}
	}
	
	//Product Section
	
	//This method maps for /admin/products endpoint. It shows the products.html page
	//products.html shows all products 
	//The method is @GetMapping because you are getting information from the database 
	//@param model is used to get all products from the database and pass that over 
	//to the products.html page
	//@return the products page 
	@GetMapping("/admin/products")
	public String products(Model model) {
		
		//Create a model, with name "products"; products.html will use it to access the
		//products inside the model
		//Calling productService to get all products from the database 
		model.addAttribute("products", productService.getAllProduct()); 
		
		//Returning products.html page
		return "products";
	}
	
	//This method maps for /admin/products/add endpoint. It shows the productsAdd.html page
	//productsAdd.html shows the page that allows you to add a product 
	//The method is @GetMapping because you are getting information from the database 
	//@param model is used to hold a new ProductDTO and all categories from the database  
	//to the productsAdd.html page
	//@return the productsAdd page
	@GetMapping("/admin/products/add")
	public String productAddGet(Model model) {
		
		//Create a model, with name "productDTO"; productsAdd.html will use it to access the
		//new ProductDTO inside the model
		model.addAttribute("productDTO", new ProductDTO()); 
		
		//Create a model, with name "categories"; productsAdd.html will use it to access the
		//categories inside the model
		//Calling categoryService to get all categories from the database 
		model.addAttribute("categories", categoryService.getAllCategories());
		
		//Returning productsAdd.html page
		return "productsAdd";
	}
	
	//This method maps for /admin/products/add endpoint. It converts the productDTO from productsAdd.html
		//to a product object; in the end, the user is redirected to the products.html 
	//products.html shows the page with all the products
	//The method is @PostMapping because you are sending information to the database (the new product)
	//IOException is thrown when attempting to access a file that does not exist at the specified location
	//@param productDTO is the product that is being added and needs to be converted to a product
		//@ModelAttribute() binds the "productDTO" from productsAdd.html to the productDTO parameter
	//@param file is the productDTO's image that was inputted in the form; using @RequestParam()
		//to get productImage and bind it to file 
		//MultipartFile type is always used when you are requesting a file from front end 
	//@param imgName is the productDTO's imageName; using @RequestParam() to get imgName from 
		//productsAdd.html and bind it to imgName
	//@return the products page
	@PostMapping("/admin/products/add")
	public String productAddPost(@ModelAttribute("productDTO") ProductDTO productDTO,
								 @RequestParam("productImage") MultipartFile file,
								 @RequestParam("imgName") String imgName) throws IOException {

		//Creating a product and sending productDTO's information to product
		//This is to convert the productDTO to product
		Product product = new Product(); 
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		//Using productDTO.getcategoryId() to get productDTO's id
			//Then, pass that into categoryService.getCategoryById() to get the specific category,
			//which will be set as product's category
			//A value may not be present, so also add .get()
		product.setCategory(categoryService.getCategoryById(productDTO.getcategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		
		//Variable to hold file's name
		String imageUUID;
		
		//If loop to check if an image was submitted
		if (!file.isEmpty()) {
			
			//Assigning file's original name to imageUUID
			imageUUID = file.getOriginalFilename();
			
			//Holding file's name and path to fileNameAndPath
			//Paths.get() first takes in file's path (where it needs to be saved) and 
				//its name (uploadDir will hold the path)
			Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
			
			//Getting the file's bytes to save it to the specified path and name
			Files.write(fileNameAndPath, file.getBytes());
		}
		//Else block for if an image was not submitted 
		else {
			//Assigning imgName to imageUUID
			imageUUID = imgName;
		}
		
		//Setting imageUUID as the product's name and saving the product to database
		product.setImageName(imageUUID);
		productService.addProduct(product);
		
		//Returning to products page (shows all products) 
		return "redirect:/admin/products";

	}
	
	//This method maps for /admin/product/delete/{id} endpoint. It allows the user to delete a product by its id 
	//@param id gets its value from {id} in the endpoint 
	//@return the products page 
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable long id) {
			
		//Calling productService to remove the product by id
	    productService.removeProductById(id);

		//Redirecting to page that shows all products 
		return "redirect:/admin/products";
	}
	
	//This method maps for /admin/product/update/{id} endpoint. We are wanting to send a product to the productsAdd.html
	//so we can update it. However, the front end cannot work with product (cannot access its categories). So we need to
	//send product's information to a productDTO object, and send that productDTO object to productsAdd.html
		//In the end, the method shows us productsAdd.html page (this is the page where the user can add a product)
		//But in this case, the user is updating a product
	//@param id the id of the product that is being updated (using @PathVariable to bind {id} to id parameter)
	//@param model to hold all categories and the productDTO to be sent over to the productsAdd.html page
	//@return the productsAdd page
	@GetMapping("/admin/product/update/{id}")
	public String updateProductGet(@PathVariable long id, Model model) {
		
		//Getting a product by its id (using .get() in case it is not found)
		Product product = productService.getProductById(id).get();
		
		//Creating productDTO object to hold product's info and assigning product's fields to productDTO
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setcategoryId((product.getCategory().getId()));
		productDTO.setPrice(product.getPrice());
		productDTO.setWeight(product.getWeight());	
		productDTO.setDescription(product.getDescription());	
		productDTO.setImageName(product.getImageName());
		
		//Using models to send all categories and productDTO to productsAdd.html page
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("productDTO", productDTO);
		
		//Show page
		return "productsAdd";
	}
	
}




















