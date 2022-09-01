package com.soft_kali.mfoodly.dto.product_order;

import com.soft_kali.mfoodly.dto.user.AddressBookDto;
import com.soft_kali.mfoodly.dto.user.OutletDto;
import com.soft_kali.mfoodly.dto.location.CityDto;
import com.soft_kali.mfoodly.dto.user.PickupBoyUserDetailsDto;
import com.soft_kali.mfoodly.dto.user.UserDto;
import com.soft_kali.mfoodly.entity.location.CityEntity;
import com.soft_kali.mfoodly.entity.product_order.SingleProductOrderEntity;
import com.soft_kali.mfoodly.entity.status.OrderStatusEntity;
import com.soft_kali.mfoodly.entity.user.AddressBookEntity;
import com.soft_kali.mfoodly.entity.user.OutletEntity;
import com.soft_kali.mfoodly.entity.user.PickupBoyUserDetails;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private UserDto userEntity;
    private UserDto seller;
    private CityDto cityEntity;
    private OutletDto outletName;
    private PickupBoyUserDetailsDto pickupBoyUserDetails;
    private OrderStatusDto orderStatusEntity;

    private AddressBookDto addressBookEntity;

    private List<SingleProductOrderDto> singleProductOrderEntityList;


}
