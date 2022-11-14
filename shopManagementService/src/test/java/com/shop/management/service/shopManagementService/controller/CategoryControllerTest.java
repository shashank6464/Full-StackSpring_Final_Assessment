package com.shop.management.service.shopManagementService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.management.service.shopManagementService.ShopManagementServiceApplication;
import com.shop.management.service.shopManagementService.model.Category;
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
public class CategoryControllerTest {

    // For controller based mocks (for web layer)
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext; // autowired the configuration


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

    @Test
    public void verifyAllCategories() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/category/get-categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andDo(print());
    }

    @Test
    public void verfiyaddCategory() throws Exception{
        Category category = new Category( "test_name", "test_description");

        mockMvc.perform(MockMvcRequestBuilders.post("/category/add-category")
                        .content(asJsonString(category))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.name").value("test_name"))
                .andExpect(jsonPath("$.description").value("test_description"))
                .andDo(print());
    }

    // only updating name (for test)
    @Test
    public void verfiyupdateCategoryById() throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("name", "test_name");

        // checking for the first category
        mockMvc.perform(MockMvcRequestBuilders.post("/category/update-category-by-id/1")
                        .content(asJsonString(map))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.message").value("Successfully UPDATED Name !!"))
                .andDo(print());
    }

    @Test
    public void verfiydeleteCategoryById() throws Exception{

        // deleting the first category
        mockMvc.perform(MockMvcRequestBuilders.delete("/category/delete-category-by-id/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.message").value("Successfully DELETED this category !!"))
                .andDo(print());
    }

    @Test
    public void verfiygetCategoryByField() throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Electronics");

        // checking for the first category
        mockMvc.perform(MockMvcRequestBuilders.post("/category/get-category-by-field")
                        .content(asJsonString(map))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Electronics"))
                .andExpect(jsonPath("$[0].description").value("Electronic items"))
                .andDo(print());
    }


}