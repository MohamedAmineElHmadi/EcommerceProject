package com.ecomerce.com.project.ecommece.restImpl;

import com.ecomerce.com.project.ecommece.constants.EcomConstants;
import com.ecomerce.com.project.ecommece.rest.ProductRest;
import com.ecomerce.com.project.ecommece.service.ProductService;
import com.ecomerce.com.project.ecommece.utils.EcomUtils;
import com.ecomerce.com.project.ecommece.wrapper.ProductWrapper;
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
public class ProductRestImpl implements ProductRest {
    @Autowired
    ProductService productService;
    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
        return productService.addNewProduct(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();        }
        return EcomUtils.getResponseEntity(EcomConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
    try{
        return productService.getAllProduct();
    }
    catch (Exception ex){
        ex.printStackTrace();
    }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);

        }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
    try{
        return productService.updateProduct(requestMap);
    }
    catch (Exception ex)
    {
        ex.printStackTrace();
    }
        return EcomUtils.getResponseEntity(EcomConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try{
            return productService.deleteProduct(id);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return EcomUtils.getResponseEntity(EcomConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
