package com.shop.management.service.shopManagementService.service;


import com.netflix.discovery.converters.Auto;
import com.shop.management.service.shopManagementService.model.Category;
import com.shop.management.service.shopManagementService.model.Product;
import com.shop.management.service.shopManagementService.repository.ProductRepository;
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
public class ProductService {

    @Autowired
    ProductRepository repository;



    // short services
    public List<Product> getProducts(){
        return repository.findAll();
    }
    public boolean checkProductExistsById(int id){
        return repository.existsById(id);
    }
    @Transactional
    public List<Product> getProductByName(String name){
        return repository.getProductsByName(name);
    }


    //add product
    public Product addProduct(Product product){
        return repository.save(product);
    }

    public List<Product> addProducts(List<Product> products) { return repository.saveAll(products); }


    // for 1:many adding
    public Product addCategory(int product_id, Category category){

        Product product = repository.findById(product_id).get();

        product.addCategory(category);

        return repository.save(product);
    }

    // for 1:many removal
    public Product removeCategory(int product_id, Category category){

        Product product = repository.findById(product_id).get();

        product.removeCategory(category);

        return repository.save(product);
    }



    @Transactional
    public String updateProductById(int product_id, String field, String value){

        if(field.equals("description")) {
            repository.updateProductDescriptionById(product_id, value);

            return "{\"message\" : \"Successfully UPDATED Description !!\"}";
        }

        else if(field.equals("price")){
            repository.updateProductPriceById(product_id, Double.parseDouble(value));

            return "{\"message\" : \"Successfully UPDATED Price !!\"}";
        }

        repository.updateProductNameById(product_id, value);

        return "{\"message\" : \"Successfully UPDATED Name !!\"}";
    }

    public String deleteProductById(int product_id){
        repository.deleteById(product_id);

        return "{\"message\" : \"Successfully DELETED this Product !!\"}";
    }



    //GET PRODUCT By Any Field
    @Transactional
    public List<Product> getProductByField(String field, String value){


        if(field.equals("id")){
            Optional<Product> product;
            product = repository.findById(Integer.parseInt(value));
            if(product.isPresent()) return new ArrayList<>(Arrays.asList(product.get()));
            return null;
        }

        List<Product> resList = new ArrayList<>();
        if(field.equals("description")) resList = repository.getProductsByDescription(value);

        else if(field.equals("price")) resList = repository.getProductsByPrice(Double.parseDouble(value));

        else if(field.equals("name")) resList = repository.getProductsByName(value);

            // getting products by category name (value = name)
        else{
            List<Product> all = getProducts();
            for(Product p : all){
                if(!p.getCategories().isEmpty()){
                    for(Category c : p.getCategories()){
                        if(c.getName().equals(value)){
                            resList.add(p);
                        }
                    }
                }
            }
        }

        if(resList.size()>0) return resList;
        return null;
    }




}
