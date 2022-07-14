package com.soft_kali.mfoodly.entity.product_order;

import com.soft_kali.mfoodly.entity.OutletEntity;
import com.soft_kali.mfoodly.entity.location.CityEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class SingleProductOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long singleProductOrderId;

    private Long productId;
    private String productImageLink;
    private double markedPrice;
    private double sellingprice;


    @ManyToOne
    @JoinColumn(name = "userId")
    private ProductOrderEntity ProductOrderEntity;



}
