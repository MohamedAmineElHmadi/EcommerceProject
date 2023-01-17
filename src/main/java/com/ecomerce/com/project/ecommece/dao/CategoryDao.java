package com.ecomerce.com.project.ecommece.dao;

import com.ecomerce.com.project.ecommece.POJO.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category,Integer> {
     List<Category> getAllCategory();
}
