package com.ecomerce.com.project.ecommece.serviceImpl;

import com.ecomerce.com.project.ecommece.JWT.CustomerUserDetailsService;
import com.ecomerce.com.project.ecommece.JWT.JwtFilter;
import com.ecomerce.com.project.ecommece.JWT.JwtUtil;
import com.ecomerce.com.project.ecommece.POJO.User;
import com.ecomerce.com.project.ecommece.constants.EcomConstants;
import com.ecomerce.com.project.ecommece.dao.UserDao;
import com.ecomerce.com.project.ecommece.service.UserService;
import com.ecomerce.com.project.ecommece.utils.EcomUtils;
import com.ecomerce.com.project.ecommece.utils.EmailUtils;
import com.ecomerce.com.project.ecommece.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    AuthenticationManager   authenticationManager;
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    EmailUtils emailUtils;
    @Override
    public ResponseEntity<String> signup(Map<String, String> requestMap) {
        log.info("Inside signup {}",requestMap);
        try{
                if (validateSignUpMap(requestMap))
                {
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
                 }
        }
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
    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try{
            Authentication auth= authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password"))
            );
            if (auth.isAuthenticated()) {
                if (customerUserDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true"))
                {
                    return new ResponseEntity<String>("{\"token\":\""+
                            jwtUtil.generateToken(customerUserDetailsService.getUserDetails().getEmail(),
                                    customerUserDetailsService.getUserDetails().getRole()) +"\"}" ,
                HttpStatus.OK);}
                else {
                    return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval."+"\"}",HttpStatus.BAD_REQUEST);
                }
            }


        }
        catch(Exception ex)
        {
            log.error("{}",ex);
        }
        return new ResponseEntity<String>("{\"message\":\""+"Bad Credential."+"\"}",HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try{
            if(jwtFilter.isAdmin()){
                return  new ResponseEntity<>(userDao.getAllUser(),HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();

        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
             Optional<User> optional= userDao.findById(Integer.parseInt(requestMap.get("id")));
                if(!optional.isEmpty()){
                    userDao.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                    sendMailToAllAdmin(requestMap.get("status"),optional.get().getEmail(),userDao.getAllAdmin());
                    return EcomUtils.getResponseEntity("User Status updated successfully",HttpStatus.OK);
                }

                else {
                   return EcomUtils.getResponseEntity("User id doesn't exist",HttpStatus.OK);
                }
            }
            else{
                return  EcomUtils.getResponseEntity(EcomConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return  EcomUtils.getResponseEntity(EcomConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        return  EcomUtils.getResponseEntity("true",HttpStatus.OK);


    }

    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {

        allAdmin.remove(jwtFilter.getCurrentUser());
        if(status!=null&& status.equalsIgnoreCase("true")){
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account Approved","USER:-"+user+"\n is approved by \n ADMIN:-"+ jwtFilter.getCurrentUser(),allAdmin);
        }else{
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account disabled","USER:-"+user+"\n is disabled by \n ADMIN:-"+ jwtFilter.getCurrentUser(),allAdmin);

        }
    }
}
