package com.soft_kali.mfoodly.entity.product_order;

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



}
