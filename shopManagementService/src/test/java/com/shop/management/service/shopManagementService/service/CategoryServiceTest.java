package com.shop.management.service.shopManagementService.service;

import com.shop.management.service.shopManagementService.model.Category;
import com.shop.management.service.shopManagementService.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    // dummy data
    @Mock
    private CategoryRepository repository;

    // dummy service for injecting data
    @InjectMocks
    private CategoryService service;


    // before each test case, ready the mocked data
    @Before
    public void setup(){

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllCategories(){
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(10,"test_name", "test_description"));
        categoryList.add(new Category(11,"test_name", "test_description"));
        categoryList.add(new Category(12,"test_name", "test_description"));


        when(repository.findAll()).thenReturn(categoryList);

        List<Category> res = service.getCategories();

        assertEquals(3, res.size());
    }

    @Test
    public void addCategory(){


        Category category = new Category(10,"test_name", "test_description");

        when(repository.save(category)).thenReturn(category);

        Category savedCategory = service.addCategory(category);

        assertEquals(10, savedCategory.getId());
        assertEquals("test_name", savedCategory.getName());
    }


    @Test
    public void updateCategoryById() {
        // first save a category
        Category category = new Category(10, "test_name", "test_description");

        when(repository.save(category)).thenReturn(category);

        Category savedCategory = service.addCategory(category);

        // then update on it (any field, here updating name)
        repository.updateCategoryNameById(savedCategory.getId(), "test_name1");

        String res = service.updateCategoryById(10, "name", "test_name1");

        assertEquals("{\"message\" : \"Successfully UPDATED Name !!\"}", res);

    }

    @Test
    public void deleteCategoryById() {
        // first save a category
        Category category = new Category(10, "test_name", "test_description");

        when(repository.save(category)).thenReturn(category);

        Category savedCategory = service.addCategory(category);

        // then delete the category
        repository.deleteById(10);

        String res = service.deleteCategoryById(10);

        assertEquals("{\"message\" : \"Successfully DELETED this category !!\"}", res);

    }

    // get category by any field, here getting by name (for test)
    @Test
    public void getCategoryById() {
        // first save a category
        Category category = new Category(10, "test_name", "test_description");

        when(repository.save(category)).thenReturn(category);

        Category savedCategory = service.addCategory(category);

        // then get the category by field
        List<Category> tempList = new ArrayList<>();
        tempList.add(savedCategory);
        when(repository.getCategoriesByName("test_name")).thenReturn(tempList);

        List<Category> res = service.getCategoryByField("name", "test_name");

        assertEquals(savedCategory, res.get(0));

    }



}