package com.soft_kali.mfoodly.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class ProductReviewEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productReviewId;


    private String reviewComment;
    private int rating;

    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;


}
