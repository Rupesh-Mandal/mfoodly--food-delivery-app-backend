package com.soft_kali.mfoodly.entity;


import com.soft_kali.mfoodly.entity.location.CityEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;
    private String productDescription;

    private double markedPrice;
    private double sellingprice;

    private String productImageLink;
    private LocalDateTime createdTime;

    private double totalRating=0.0;


    @ManyToOne
    @JoinColumn(name = "cityId")
    private CityEntity cityEntity;

    @ManyToOne
    @JoinColumn(name = "outletId")
    private OutletEntity outletName;


    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "productId", referencedColumnName = "productId"),
            inverseJoinColumns = @JoinColumn(name = "categoryId", referencedColumnName = "categoryId"))
    private List<CategoryEntity> categoryEntities = new ArrayList<>();



    @OneToMany(mappedBy = "productEntity",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ProductReviewEntity> productReviewEntityList=new ArrayList<>();


}
