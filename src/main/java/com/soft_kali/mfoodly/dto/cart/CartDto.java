package com.soft_kali.mfoodly.dto.cart;

import com.soft_kali.mfoodly.dto.user.OutletDto;
import com.soft_kali.mfoodly.dto.user.UserDto;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long cartId;
    private UserDto userEntity;
    private OutletDto outletEntity;
    List<SubCartDto> subCartEntityList;

}
