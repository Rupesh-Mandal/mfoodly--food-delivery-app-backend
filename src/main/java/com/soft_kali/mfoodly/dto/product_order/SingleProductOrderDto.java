package com.soft_kali.mfoodly.dto.product_order;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SingleProductOrderDto {

    private Long singleProductOrderId;

    private Long productId;
    private String productImageLink;
    private double markedPrice;
    private double sellingprice;
}
