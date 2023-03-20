package com.ecommerce.website;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ecommerce.website.controller.AdminController;
import com.ecommerce.website.repository.CategoryRepository;
import com.ecommerce.website.service.CategoryService;


/**This is the main class. It allows the user to run the project */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan
public class EcommerceWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceWebsiteApplication.class, args);
	}

}

