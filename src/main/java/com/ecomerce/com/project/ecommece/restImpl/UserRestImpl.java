package com.ecomerce.com.project.ecommece.restImpl;

import com.ecomerce.com.project.ecommece.constants.EcomConstants;
import com.ecomerce.com.project.ecommece.rest.UserRest;
import com.ecomerce.com.project.ecommece.service.UserService;
import com.ecomerce.com.project.ecommece.utils.EcomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
}
