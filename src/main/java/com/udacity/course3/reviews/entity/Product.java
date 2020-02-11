package com.udacity.course3.reviews.entity;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_desc")
    private String productDescription;

    @OneToMany(mappedBy = "product")
    @JsonManageReference
    private List<Review> reviews = new ArrayList<>();

}
