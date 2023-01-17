package com.ecomerce.com.project.ecommece.serviceImpl;

import com.ecomerce.com.project.ecommece.JWT.JwtFilter;
import com.ecomerce.com.project.ecommece.POJO.Category;
import com.ecomerce.com.project.ecommece.constants.EcomConstants;
import com.ecomerce.com.project.ecommece.dao.CategoryDao;
import com.ecomerce.com.project.ecommece.service.CategoryService;
import com.ecomerce.com.project.ecommece.utils.EcomUtils;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    JwtFilter jwtFilter;
    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
        if (jwtFilter.isAdmin()){
            if(validateCategoryMap(requestMap,false)){
                categoryDao.save(getCategoryFromMap(requestMap,false));
                return EcomUtils.getResponseEntity("category added",HttpStatus.OK);
                
            }
        }
        else EcomUtils.getResponseEntity(EcomConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return EcomUtils.getResponseEntity(EcomConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
      try{
        if(!Strings.isNullOrEmpty(filterValue)&& filterValue.equalsIgnoreCase("true"))
        {
            return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(),HttpStatus.OK);
        }
            return new ResponseEntity<>(categoryDao.findAll(),HttpStatus.OK);
      }
      catch (Exception ex){
          ex.printStackTrace();
      }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
                if(validateCategoryMap(requestMap,true)){
                  Optional optional= categoryDao.findById(Integer.parseInt(requestMap.get("id")));
                  if (!optional.isEmpty()){
                        categoryDao.save(getCategoryFromMap(requestMap,true));
                        return EcomUtils.getResponseEntity(("category has been updated"), HttpStatus.OK);
                  }else {
                   return EcomUtils.getResponseEntity(("category id does not exist"), HttpStatus.OK);
                  }
                }
                return EcomUtils.getResponseEntity(EcomConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);
            }
            else EcomUtils.getResponseEntity(EcomConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return EcomUtils.getResponseEntity(EcomConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
    if(requestMap.containsKey("name")){
        if (requestMap.containsKey("id")&& validateId){
            return true;
        }else if (!validateId){
            return true;
        }}
        return false;
    }
    private Category getCategoryFromMap(Map<String,String>requestMap,Boolean isAdd){
        Category category = new Category();
        if(isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }
}
