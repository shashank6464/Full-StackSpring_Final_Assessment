package com.shop.management.service.shopManagementService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.management.service.shopManagementService.ShopManagementServiceApplication;
import com.shop.management.service.shopManagementService.model.Product;
import com.shop.management.service.shopManagementService.repository.ProductRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@RunWith(SpringJUnit4ClassRunner.class)   // for running this with junit4
@ContextConfiguration(classes = ShopManagementServiceApplication.class)  // context setting for the real data (added in main)
@SpringBootTest // spring test
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductControllerTest {

    // For controller based mocks (for web layer)
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WebApplicationContext applicationContext;


    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();

    }

    // To convert Movie object to JSON content (adding "" and {, } )
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            System.out.println(jsonContent);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // initial no products
    @Test
    public void verifyAllProducts() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/product/get-products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)))
                .andDo(print());
    }

    @Test
    public void verfiyaddProduct() throws Exception{
        Product product = new Product("test_name",1000,"test_description");


        mockMvc.perform(MockMvcRequestBuilders.post("/product/add-product")
                        .content(asJsonString(product))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.name").value("test_name"))
                .andExpect(jsonPath("$.description").value("test_description"))
                .andDo(print());
    }

    @Test
    public void verfiyassignDetails() throws Exception{
        // first save the product (id will be = 1)
        Product product = new Product("test_name",1000,"test_description");
        productRepository.save(product);

        // assigning to first category
        mockMvc.perform(MockMvcRequestBuilders.put("/product/product-with-category/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.categories[0].id").value(1))
                .andExpect(jsonPath("$.categories[0].name").value("Electronic"))
                .andDo(print());
    }


    // only updating name (for test)
    @Test
    public void verfiyupdateProductById() throws Exception{
        // first save the product
        Product product = new Product("test_name",1000,"test_description");
        productRepository.save(product);

        Map<String, Object> map = new HashMap<>();
        map.put("name", "test_name1");

        // checking for the first category
        mockMvc.perform(MockMvcRequestBuilders.post("/product/update-product-by-id/1")
                        .content(asJsonString(map))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.message").value("Successfully UPDATED Name !!"))
                .andDo(print());
    }

    @Test
    public void verfiydeleteProductById() throws Exception{
        // first save the product
        Product product = new Product("test_name",1000,"test_description");
        productRepository.save(product);

        // deleting the first category
        mockMvc.perform(MockMvcRequestBuilders.delete("/product/delete-product-by-id/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.message").value("Successfully DELETED this Product !!"))
                .andDo(print());
    }

    @Test
    public void verfiygetProductByField() throws Exception{
        // first save the product
        Product product = new Product("test_name",1000,"test_description");
        productRepository.save(product);

        Map<String, Object> map = new HashMap<>();
        map.put("name", "test_name");

        // checking for the first product
        mockMvc.perform(MockMvcRequestBuilders.post("/product/get-product-by-field")
                        .content(asJsonString(map))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("test_name"))
                .andExpect(jsonPath("$[0].description").value("test_description"))
                .andDo(print());
    }


}