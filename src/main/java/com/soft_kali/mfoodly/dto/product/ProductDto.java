package com.soft_kali.mfoodly.dto.product;



import com.soft_kali.mfoodly.dto.user.OutletDto;
import com.soft_kali.mfoodly.dto.location.CityDto;
import com.soft_kali.mfoodly.dto.user.UserDto;
import com.soft_kali.mfoodly.entity.user.UserEntity;
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
    private UserDto userEntity;

    private List<CategoryDto> categoryEntities;



}
