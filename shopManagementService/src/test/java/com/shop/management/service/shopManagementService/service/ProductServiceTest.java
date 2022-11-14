package com.shop.management.service.shopManagementService.service;

import com.shop.management.service.shopManagementService.model.Product;
import com.shop.management.service.shopManagementService.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    // dummy data
    @Mock
    private ProductRepository repository;

    // dummy service for injecting data
    @InjectMocks
    private ProductService service;


    // before each test case, ready the mocked data
    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllProducts(){
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(10,"test_name",1000,"test_description", null));
        productList.add(new Product(10,"test_name",1000,"test_description", null));
        productList.add(new Product(10,"test_name",1000,"test_description", null));


        when(repository.findAll()).thenReturn(productList);

        List<Product> res = service.getProducts();

        assertEquals(3, res.size());
    }

    @Test
    public void addProduct(){


        Product product = new Product(10,"test_name",1000,"test_description", null);

        when(repository.save(product)).thenReturn(product);

        Product savedProduct = service.addProduct(product);

        assertEquals(10, savedProduct.getId());
        assertEquals("test_name", savedProduct.getName());
    }

    @Test
    public void updateProductById() {
        // first save a product
        Product product = new Product(10,"test_name",1000,"test_description", null);

        when(repository.save(product)).thenReturn(product);

        Product savedProduct = service.addProduct(product);

        // then update on it (any field, here updating name)
        repository.updateProductNameById(savedProduct.getId(), "test_name1");

        String res = service.updateProductById(10, "name", "test_name1");

        assertEquals("{\"message\" : \"Successfully UPDATED Name !!\"}", res);

    }

    @Test
    public void deleteProductById() {
        // first save a product
        Product product = new Product(10,"test_name",1000,"test_description", null);

        when(repository.save(product)).thenReturn(product);

        Product savedProduct = service.addProduct(product);

        // then delete the product
        repository.deleteById(10);

        String res = service.deleteProductById(10);

        assertEquals("{\"message\" : \"Successfully DELETED this Product !!\"}", res);

    }

    // get product by any field, here getting by name (for test)
    @Test
    public void getProductById() {
        // first save a product
        Product product = new Product(10,"test_name",1000,"test_description", null);

        when(repository.save(product)).thenReturn(product);

        Product savedProduct = service.addProduct(product);

        // then get the product by field
        List<Product> tempList = new ArrayList<>();
        tempList.add(savedProduct);
        when(repository.getProductsByName("test_name")).thenReturn(tempList);

        List<Product> res = service.getProductByField("name", "test_name");

        assertEquals(savedProduct, res.get(0));

    }



}