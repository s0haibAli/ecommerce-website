package com.ecommerce.website.dto;


/**This is needed because the category object inside of 
 * the Product POJO cannot be directly handled in the 
 * front end/view section. So, a categoryId field will be
 * created here so that the font end can access categoryId
 * 
 * A data transfer object (DTO) is an object that carries 
 * data between processes
 * */
public class ProductDTO {
	
	private Long id;
	
	private String name; 
	
	private int categoryId; 
	private double price;
	private double weight;
	private String description;
	
	//Saving image name only, actual pictures will be in /static/productImages
	private String imageName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getcategoryId() {
		return categoryId;
	}

	public void setcategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

}
