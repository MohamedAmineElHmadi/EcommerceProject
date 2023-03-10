package com.ecomerce.com.project.ecommece.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name="Product.getAllProduct",query="select new com.ecomerce.com.project.ecommece.wrapper.ProductWrapper(p.id,p.name,p.description,p.price,p.status,p.category.id,p.category.name) from Product p")
@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Table(name = "product")
public class Product implements Serializable {
    private static final long serialVersionID=1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="name")
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk",nullable = false)
    private Category category;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private Integer price;
    @Column(name = "status")
    private String status;

}
