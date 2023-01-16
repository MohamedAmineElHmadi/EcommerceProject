package com.ecomerce.com.project.ecommece.restImpl;

import com.ecomerce.com.project.ecommece.POJO.User;
import com.ecomerce.com.project.ecommece.constants.EcomConstants;
import com.ecomerce.com.project.ecommece.rest.UserRest;
import com.ecomerce.com.project.ecommece.service.UserService;
import com.ecomerce.com.project.ecommece.utils.EcomUtils;
import com.ecomerce.com.project.ecommece.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
public class UserRestImpl implements UserRest {
@Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> SignUp(Map<String, String> requestMap) {
        try{
            return userService.signup(requestMap);
             }
        catch (Exception ex){
            ex.printStackTrace();
             }
        return EcomUtils.getResponseEntity(EcomConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<String> login(@RequestBody(required = true)Map<String,String> requestMap ){
        try{
            return userService.login(requestMap);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return EcomUtils.getResponseEntity(EcomConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {

            return userService.getAllUser();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return  new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            return userService.update(requestMap);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return EcomUtils.getResponseEntity(EcomConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        try{
            return userService.checkToken();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return EcomUtils.getResponseEntity(EcomConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
     try{
         return  userService.changePassword(requestMap);
     }catch (Exception ex)
     {ex.printStackTrace();}
        return EcomUtils.getResponseEntity(EcomConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            return userService.forgotPassword(requestMap);
        }catch (Exception ex)
        {ex.printStackTrace();}
        return  EcomUtils.getResponseEntity(EcomConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
