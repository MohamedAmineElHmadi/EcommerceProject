package com.ecomerce.com.project.ecommece.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService {

    ResponseEntity<String>signup(Map<String,String>requestMap);
}
