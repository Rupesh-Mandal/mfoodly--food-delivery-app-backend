package com.soft_kali.mfoodly.dto.user;

import com.soft_kali.mfoodly.dto.location.CityDto;
import lombok.*;


@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OutletDto {
    private Long outletId;

    private String outletName;
    private String outletDescription;
    private String outletSlogan;
    private String outletBanner;
    private String outletLogo;

    private String outletEmail;
    private String outletPhoneNumber;

    private UserDto userEntity;
    private CityDto cityEntity;

}
