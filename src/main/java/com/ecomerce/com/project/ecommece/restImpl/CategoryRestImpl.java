package com.ecomerce.com.project.ecommece.restImpl;

import com.ecomerce.com.project.ecommece.POJO.Category;
import com.ecomerce.com.project.ecommece.constants.EcomConstants;
import com.ecomerce.com.project.ecommece.rest.CategoryRest;
import com.ecomerce.com.project.ecommece.service.CategoryService;
import com.ecomerce.com.project.ecommece.utils.EcomUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
public class CategoryRestImpl implements CategoryRest {
    @Autowired
    CategoryService categoryService;
    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
    try{
        return  categoryService.addNewCategory(requestMap);

    }
    catch (Exception ex){
        ex.printStackTrace();
    }
    return EcomUtils.getResponseEntity(EcomConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategories(String filterValue) {
        try{

           return categoryService.getAllCategory(filterValue);

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try{
            return  categoryService.updateCategory(requestMap);

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return EcomUtils.getResponseEntity(EcomConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);    }
}
