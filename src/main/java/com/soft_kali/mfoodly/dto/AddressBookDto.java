package com.soft_kali.mfoodly.dto;

import com.soft_kali.mfoodly.entity.UserEntity;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressBookDto {
    private Long addressBookId;

    private String deliveryAddress1;
    private String deliveryAddress2;
    private String deliveryAddressPhoneNumber;
    private UserDto userEntity;

}
