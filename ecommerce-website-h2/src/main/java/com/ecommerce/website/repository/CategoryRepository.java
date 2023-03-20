package com.ecommerce.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.website.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}


