package com.ecommerce.website.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.website.model.Category;
import com.ecommerce.website.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	//Method to get all products in a category by category id 
	List<Product> findAllByCategory_Id(int id); 
}
