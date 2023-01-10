package com.ecomerce.com.project.ecommece.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class EcomUtils {
    private EcomUtils () {

    }
    public static ResponseEntity <String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return  new  ResponseEntity<String>("{\"message\":\""+responseMessage+"\",}",httpStatus);

    }
}
