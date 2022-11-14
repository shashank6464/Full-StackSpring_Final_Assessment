package com.shop.management.service.shopManagementService.controller;


import com.shop.management.service.shopManagementService.exceptionHandling.BadRequestException;
import com.shop.management.service.shopManagementService.exceptionHandling.EntityNotFoundException;
import com.shop.management.service.shopManagementService.model.Category;
import com.shop.management.service.shopManagementService.model.PayloadValidation;
import com.shop.management.service.shopManagementService.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService service;


    @GetMapping("/check")
    public String check(){
        return "CHECK";
    }

    @RequestMapping(value = "/get-categories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> getCategories(){
        return service.getCategories();
    }



    @RequestMapping(value = "/add-category", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Category addCategory(@RequestBody Category category) throws BadRequestException {

        if(!PayloadValidation.createdPayloadIdValidation(category)) throw new BadRequestException("PAYLOAD MALFORMED. CATEGORY ID MUST NOT BE DEFINED !!!");

        if(!service.getCategoryByName(category.getName()).isEmpty()) throw new BadRequestException("Category with this name is already present!!!");

        return service.addCategory(category);
    }


    @RequestMapping(value = "/update-category-by-id/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateCategoryById(@PathVariable("id") int category_id, @RequestBody Map<String, Object> map) throws EntityNotFoundException, BadRequestException{

        if(map.size()!=1) throw new BadRequestException("PAYLOAD MALFORMED. You MUST UPDATE Only One field at a time");

        if(!PayloadValidation.createdPayloadCategoryField(map)) throw new BadRequestException("PAYLOAD MALFORMED. Either (only) description or name MUST be PROVIDED !!!");

        if(!service.checkCategoryExistsById(category_id)) throw new EntityNotFoundException("Category with this id NOT FOUND");

        if(map.containsKey("description")) return service.updateCategoryById(category_id, "description", map.get("description").toString());
        return service.updateCategoryById(category_id, "name", map.get("name").toString());
    }


    @RequestMapping(value = "/delete-category-by-id/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteCategoryById(@PathVariable("id") int category_id) throws EntityNotFoundException, BadRequestException {

        if(!service.checkCategoryExistsById(category_id)) throw new EntityNotFoundException("Category with this id NOT FOUND");


        try{
            return service.deleteCategoryById(category_id);
        }
        catch (Exception e){
            throw new BadRequestException("Category Can't be deleted !! Because it is linked with some Products !!");
        }

    }



    @RequestMapping(value = "/get-category-by-field", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> getCategoriesByField(@RequestBody Map<String, Object> map) throws EntityNotFoundException, BadRequestException{

        if(map.size()!=1) throw new BadRequestException("PAYLOAD MALFORMED. You MUST INPUT One field at a time");

        if(!PayloadValidation.createdPayloadCategoryField(map) && !map.containsKey("id")) throw new BadRequestException("PAYLOAD MALFORMED. Either (only) id or description or name MUST be PROVIDED !!!");

        List<Category> res;
        if(map.containsKey("id")) res = service.getCategoryByField("id",map.get("id").toString());
        else if(map.containsKey("description")) res = service.getCategoryByField("description", map.get("description").toString());
        else res = service.getCategoryByField("name", map.get("name").toString());

        if(res==null) throw new EntityNotFoundException("NO SUCH Category(s) Found");
        return res;
    }



}
