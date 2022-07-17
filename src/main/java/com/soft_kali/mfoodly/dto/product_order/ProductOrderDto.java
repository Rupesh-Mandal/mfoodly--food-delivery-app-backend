package com.soft_kali.mfoodly.dto.product_order;

import com.soft_kali.mfoodly.dto.user.AddressBookDto;
import com.soft_kali.mfoodly.dto.user.OutletDto;
import com.soft_kali.mfoodly.dto.location.CityDto;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderDto {
    private Long productOrderId;

    private LocalDateTime createdTime;
    private double totalPrice;
    private UserEntity userEntity;

    private CityDto cityEntity;

    private OutletDto outletName;
    private AddressBookDto addressBookEntity;
    private List<SingleProductOrderDto> singleProductOrderEntityList;

}
