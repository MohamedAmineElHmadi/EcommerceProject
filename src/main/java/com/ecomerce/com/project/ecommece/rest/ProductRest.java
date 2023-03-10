package com.ecomerce.com.project.ecommece.rest;

import com.ecomerce.com.project.ecommece.POJO.Product;
import com.ecomerce.com.project.ecommece.wrapper.ProductWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RequestMapping(path = "/product")
public interface ProductRest {
    @PostMapping(path="/add")
    ResponseEntity<String>addNewProduct(@RequestBody Map<String,String>requestMap);
    @GetMapping(path="/get")
    ResponseEntity<List<ProductWrapper>>getAllProduct();
    @PostMapping(path="update")
    ResponseEntity<String>updateProduct(@RequestBody Map<String,String>requestMap);
    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String>deleteProduct(@PathVariable Integer id);
}

