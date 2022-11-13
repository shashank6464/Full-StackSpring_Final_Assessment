package com.shop.management.service.shopManagementService.service;


import com.shop.management.service.shopManagementService.model.Category;
import com.shop.management.service.shopManagementService.model.Product;
import com.shop.management.service.shopManagementService.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {


    @Autowired
    private CategoryRepository repository;



    //--------------------------------------------------------------//
                    // Short useful services //

    public List<Category> getCategories(){
        return repository.findAll();
    }
    public Category getCategoryById(int id){
        Optional<Category> cat =  repository.findById(id);

        if(cat.isPresent()) return cat.get();

        return null;
    }
    public boolean checkCategoryExistsById(int id){
        return repository.existsById(id);
    }
    @Transactional
    public List<Category> getCategoryByName(String name){
        return repository.getCategoriesByName(name);
    }

    //--------------------------------------------------------------//



    public Category addCategory(Category category){
        return repository.save(category);
    }

    @Transactional
    public String updateCategoryById(int category_id, String field, String value){
        if(field.equals("description")){
            repository.updateCategoryDescriptionById(category_id, value);

            return "{\"message\" : \"Successfully UPDATED Description !!\"}";
        }

        repository.updateCategoryNameById(category_id, value);

        return "{\"message\" : \"Successfully UPDATED Name !!\"}";

    }

    public String deleteCategoryById(int category_id){
        repository.deleteById(category_id);

        return "{\"message\" : \"Successfully DELETED this category !!\"}";
    }



    //----------- GET Category BY ANY FIELD ------------//
    @Transactional
    public List<Category> getCategoryByField(String field, String value){


        if(field.equals("category_id")){
            Optional<Category> category;
            category = repository.findById(Integer.parseInt(value));
            if(category.isPresent()) return new ArrayList<>(Arrays.asList(category.get()));
            return null;
        }

        List<Category> resList;
        if(field.equals("description")) resList = repository.getCategoriesByDescription(value);
        else resList = repository.getCategoriesByName(value);

        if(resList.size()>0) return resList;
        return null;
    }

}
