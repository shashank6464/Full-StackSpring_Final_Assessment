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
		categoryRepository.save(new Category("Electronics", "Electronic items"));
		categoryRepository.save(new Category("Clothing", "Clothes"));
		categoryRepository.save(new Category("Household", "Household items"));

		categoryRepository.findAll().forEach(System.out::println);


		System.out.println("----------------------------------------------------------------------------------");


		productRepository.save(new Product("Samsung S21", 100000, "5G Mobile",null));
		productRepository.save(new Product("Iphone 14+", 80000, "IOS 16 Mobile", null));
		productRepository.save(new Product("IKEA Table", 10000, "5ft Table", null));
		productRepository.save(new Product("Puma Shirt", 3000, "Red M", null));

		productRepository.findAll().forEach(System.out::println);

	}
}
