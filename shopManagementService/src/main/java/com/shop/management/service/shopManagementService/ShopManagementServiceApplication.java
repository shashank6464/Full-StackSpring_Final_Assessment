package com.shop.management.service.shopManagementService;

import com.shop.management.service.shopManagementService.model.Category;
import com.shop.management.service.shopManagementService.model.Product;
import com.shop.management.service.shopManagementService.repository.CategoryRepository;
import com.shop.management.service.shopManagementService.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
public class ShopManagementServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ShopManagementServiceApplication.class, args);
	}

	private final CategoryRepository categoryRepository;
	private final ProductRepository productRepository;


	public ShopManagementServiceApplication(CategoryRepository userRepository, ProductRepository productRepository) {
		this.categoryRepository = userRepository;
		this.productRepository = productRepository;
	}

	@Override
	public void run(String... args) throws Exception {

		categoryRepository.save(new Category("Electronic", "Electronic items"));
		categoryRepository.save(new Category("Clothing", "Clothes item"));
		categoryRepository.save(new Category("Health", "Health items"));


		categoryRepository.findAll().forEach(System.out::println);


		System.out.println("***************************");


		productRepository.save(new Product("Oneplus 9", 50000, "4G Phone",null));
		productRepository.save(new Product("Oneplus 10", 60000, "4G Phone", null));
		productRepository.save(new Product("Oneplus 6", 30000, "4G Phone", null));
		productRepository.save(new Product("Jockey T-Shirt", 1500, "Grey", null));
		productRepository.save(new Product("Jockey Pant", 1000, "Black", null));
		productRepository.save(new Product("Garnier Facewash", 80, "100ml", null));
		productRepository.save(new Product("Nivea Facewash", 100, "100ml", null));
		productRepository.save(new Product("Nivea Cream", 200, "50ml", null));



		productRepository.findAll().forEach(System.out::println);

	}
}
