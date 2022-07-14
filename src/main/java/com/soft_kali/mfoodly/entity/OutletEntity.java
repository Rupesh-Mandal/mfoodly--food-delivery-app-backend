package com.soft_kali.mfoodly.entity;

import com.soft_kali.mfoodly.entity.location.CityEntity;
import com.soft_kali.mfoodly.entity.product_order.ProductOrderEntity;
import com.soft_kali.mfoodly.entity.product_order.SingleProductOrderEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OutletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long outletId;

    @Column(name = "outlet_name")
    private String outletName;
    private String outletDescription;
    private String outletSlogan;
    private String outletBanner;
    private String outletLogo;

    private String outletEmail;
    private String outletPhoneNumber;

    @ManyToOne
    @JoinColumn(name = "cityId")
    private CityEntity cityEntity;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;



    @OneToMany(mappedBy = "outletName", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<ProductEntity> postEntityList = new ArrayList<>();


    @OneToMany(mappedBy = "outletName", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<ProductOrderEntity> productOrderEntityList = new ArrayList<>();

}
