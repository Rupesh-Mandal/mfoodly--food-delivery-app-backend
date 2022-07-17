package com.soft_kali.mfoodly.dto.user;

import com.soft_kali.mfoodly.dto.location.CityDto;
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
    private CityDto cityEntity;

    private UserDto userEntity;

}
