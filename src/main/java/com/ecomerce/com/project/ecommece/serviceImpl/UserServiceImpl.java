package com.ecomerce.com.project.ecommece.serviceImpl;

import com.ecomerce.com.project.ecommece.POJO.User;
import com.ecomerce.com.project.ecommece.constants.EcomConstants;
import com.ecomerce.com.project.ecommece.dao.UserDao;
import com.ecomerce.com.project.ecommece.service.UserService;
import com.ecomerce.com.project.ecommece.utils.EcomUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public ResponseEntity<String> signup(Map<String, String> requestMap) {
        log.info("Inside signup {}",requestMap);
        try{if (validateSignUpMap(requestMap)){
            User user=userDao.findByEmailId(requestMap.get("email"));
                    if(Objects.isNull(user)){
                        userDao.save(getUserFromMap(requestMap));
                        return EcomUtils.getResponseEntity("Successfully Registered",HttpStatus.OK);
                    }
                    else  {
                        return  EcomUtils.getResponseEntity("Email Already Exist",HttpStatus.BAD_REQUEST);
                    }
        }
        else {
            return EcomUtils.getResponseEntity(EcomConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }}
        catch (Exception ex){
            ex.printStackTrace();
        }
    return EcomUtils.getResponseEntity(EcomConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private boolean  validateSignUpMap (Map<String,String> requestMap){
      if  (requestMap.containsKey("name")&& requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password"))
      {
          return true;
      }
      else
      {
          return false;
      }
    }
    private  User getUserFromMap(Map<String,String>requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }
}
