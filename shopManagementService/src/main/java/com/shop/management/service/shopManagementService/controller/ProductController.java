package com.shop.management.service.shopManagementService.controller;


import com.shop.management.service.shopManagementService.exceptionHandling.BadRequestException;
import com.shop.management.service.shopManagementService.exceptionHandling.EntityNotFoundException;
import com.shop.management.service.shopManagementService.model.Category;
import com.shop.management.service.shopManagementService.model.PayloadValidation;
import com.shop.management.service.shopManagementService.model.Product;
import com.shop.management.service.shopManagementService.service.CategoryService;
import com.shop.management.service.shopManagementService.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;


    @RequestMapping(value = "/get-products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getProduct(){
        return productService.getProducts();
    }


    @RequestMapping(value = "/add-product", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product addProduct(@RequestBody Product product) throws BadRequestException {

        if(!PayloadValidation.createdPayloadIdValidation(product)) throw new BadRequestException("PAYLOAD MALFORMED. PRODUCT ID MUST NOT BE DEFINED !!!");

        if(!productService.getProductByName(product.getName()).isEmpty()) throw new BadRequestException("Product with this name is already present!!!");

        return productService.addProduct(product);
    }


    @RequestMapping(value = "/product-with-category/{product_id}/{category_id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product assignDetails(@PathVariable("product_id") int product_id, @PathVariable("category_id") int category_id) throws  EntityNotFoundException{

        if(!productService.checkProductExistsById(product_id)) throw new EntityNotFoundException("Product with this ID Doesn't Exists !!");

        Category category = categoryService.getCategoryById(category_id);
        if(category==null) throw new EntityNotFoundException("Category with this Id Doesn't Exist!! Cannot Assign to Product!!");

        //System.out.println(category);

        return productService.assignCategory(product_id, category);
    }



    @RequestMapping(value = "/update-product-by-id/{id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateProductById(@PathVariable("id") int product_id, @RequestBody Map<String, Object> map) throws EntityNotFoundException, BadRequestException{

        if(map.size()!=1) throw new BadRequestException("PAYLOAD MALFORMED. You MUST UPDATE Only One field at a time");

        if(!PayloadValidation.createdPayloadProductField(map)) throw new BadRequestException("PAYLOAD MALFORMED. Either (only) description or price or name MUST be PROVIDED !!!");

        if(!productService.checkProductExistsById(product_id)) throw new EntityNotFoundException("Product with this id NOT FOUND");



        if(map.containsKey("description")) return productService.updateProductById(product_id,"description", map.get("description").toString());

        else if(map.containsKey("price")) return productService.updateProductById(product_id,"price" , map.get("price").toString());

        return productService.updateProductById(product_id, "name", map.get("name").toString());

    }


    @RequestMapping(value = "/delete-product-by-id/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteProductById(@PathVariable("id") int product_id) throws EntityNotFoundException {

        if(!productService.checkProductExistsById(product_id)) throw new EntityNotFoundException("Product with this id NOT FOUND");

        return productService.deleteProductById(product_id);
    }


    @RequestMapping(value = "/get-product-by-field", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getProductByField(@RequestBody Map<String, Object> map) throws EntityNotFoundException, BadRequestException{

        if(map.size()!=1) throw new BadRequestException("PAYLOAD MALFORMED. You MUST INPUT One field at a time");

        if(!PayloadValidation.createdPayloadProductField(map) && !map.containsKey("product_id")) throw new BadRequestException("PAYLOAD MALFORMED. Either (only) product_id or description or price or name MUST be PROVIDED !!!");

        List<Product> res;
        if(map.containsKey("product_id")) res = productService.getProductByField("product_id",map.get("product_id").toString());
        else if(map.containsKey("description")) res = productService.getProductByField("description", map.get("description").toString());
        else if(map.containsKey("price")) res = productService.getProductByField("price", map.get("price").toString());
        else res = productService.getProductByField("name", map.get("name").toString());

        if(res==null) throw new EntityNotFoundException("NO SUCH Product(s) Found"); //404
        return res;
    }




}
