package com.soft_kali.mfoodly.entity.location;

import com.soft_kali.mfoodly.entity.OutletEntity;
import com.soft_kali.mfoodly.entity.ProductEntity;
import com.soft_kali.mfoodly.entity.ProductReviewEntity;
import com.soft_kali.mfoodly.entity.UserEntity;
import com.soft_kali.mfoodly.entity.product_order.ProductOrderEntity;
import com.soft_kali.mfoodly.entity.product_order.SingleProductOrderEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cityId;

    private String name;
    @ManyToOne
    @JoinColumn(name = "districtsId")
    private DistrictsEntity districtsEntity;


    @OneToMany(mappedBy = "cityEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserEntity> userEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "cityEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OutletEntity> outletEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "cityEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductEntity> ProductEntity = new ArrayList<>();

    @OneToMany(mappedBy = "cityEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductOrderEntity> productOrderEntityList = new ArrayList<>();

    public CityEntity(int cityId, String name, DistrictsEntity districtsEntity) {
        this.cityId = cityId;
        this.name = name;
        this.districtsEntity = districtsEntity;
    }
}
