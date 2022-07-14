package com.soft_kali.mfoodly.dto.product_order;

import com.soft_kali.mfoodly.dto.OutletDto;
import com.soft_kali.mfoodly.dto.location.CityDto;
import com.soft_kali.mfoodly.entity.OutletEntity;
import com.soft_kali.mfoodly.entity.location.CityEntity;
import com.soft_kali.mfoodly.entity.product_order.ProductOrderEntity;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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


    private ProductOrderDto ProductOrderEntity;

}
