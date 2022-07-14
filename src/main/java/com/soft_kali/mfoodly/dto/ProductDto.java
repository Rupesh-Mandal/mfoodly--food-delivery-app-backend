package com.soft_kali.mfoodly.dto;



import com.soft_kali.mfoodly.dto.location.CityDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long productId;

    private String productName;
    private String productDescription;

    private double markedPrice;
    private double sellingprice;

    private String productImageLink;
    private LocalDateTime createdTime;

    private CityDto cityEntity;

    private OutletDto outletName;

    private List<CategoryDto> categoryEntities;

    private List<ProductReviewDto> productReviewEntityList;


}
