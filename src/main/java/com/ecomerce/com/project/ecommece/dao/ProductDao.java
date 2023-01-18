package com.ecomerce.com.project.ecommece.dao;

import com.ecomerce.com.project.ecommece.POJO.Product;
import com.ecomerce.com.project.ecommece.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product,Integer> {
    List<ProductWrapper>getAllProduct();

}
