package com.ecomerce.com.project.ecommece.JWT;

import com.ecomerce.com.project.ecommece.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
@Service
@Slf4j
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    UserDao userDao;
    private  com.ecomerce.com.project.ecommece.POJO.User userDetails;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       log.info("inside load userByUsername{}",username);
        userDetails= userDao.findByEmailId(username);
        if(!Objects.isNull(userDetails))
            return  new User(userDetails.getEmail(),userDetails.getPassword(),new ArrayList<>());
        else
            throw  new UsernameNotFoundException("User Not Found");
    }
    public com.ecomerce.com.project.ecommece.POJO.User getUserDetails(){
        return userDetails;
    }
}
