package com.ecomerce.com.project.ecommece.serviceImpl;

import com.ecomerce.com.project.ecommece.JWT.JwtFilter;
import com.ecomerce.com.project.ecommece.POJO.Category;
import com.ecomerce.com.project.ecommece.POJO.Product;
import com.ecomerce.com.project.ecommece.constants.EcomConstants;
import com.ecomerce.com.project.ecommece.dao.ProductDao;
import com.ecomerce.com.project.ecommece.service.ProductService;
import com.ecomerce.com.project.ecommece.utils.EcomUtils;
import com.ecomerce.com.project.ecommece.wrapper.ProductWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl  implements ProductService {
    @Autowired
    ProductDao productDao;
    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateProductMap(requestMap, false)) {
                    productDao.save(getProductFromMap(requestMap,false));
                    return EcomUtils.getResponseEntity("product added successfully",HttpStatus.OK);
                }
                return EcomUtils.getResponseEntity(EcomConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);

            }
            return EcomUtils.getResponseEntity(EcomConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EcomUtils.getResponseEntity(EcomConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
    try {
        return new ResponseEntity<>(productDao.getAllProduct(),HttpStatus.OK);
    }
    catch (Exception ex){
        ex.printStackTrace();
    }
    return  new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
    try{
        if (jwtFilter.isAdmin()){
            if(validateProductMap(requestMap,true)){
            Optional<Product> optional= productDao.findById(Integer.parseInt(requestMap.get("id")));
            if(!optional.isEmpty()){
                Product product=getProductFromMap(requestMap,true);
                product.setStatus(optional.get().getStatus());
                productDao.save(product);
                return  EcomUtils.getResponseEntity("Product has been updated"+product,HttpStatus.OK);
            }else
            {
                return EcomUtils.getResponseEntity("Product id does not exist",HttpStatus.OK);
            }
            }
            else {
                EcomUtils.getResponseEntity(EcomConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);
            }
        }else {
            EcomUtils.getResponseEntity(EcomConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
        return EcomUtils.getResponseEntity(EcomConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }
    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd ) {
        log.info("ingesdfkoshofsg");
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));
        Product product=new Product();
        if (isAdd){
            product.setId(Integer.parseInt(requestMap.get("id")));
        }
        else {
            product.setStatus("true");
        }
        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));
        return product;
    }
}
