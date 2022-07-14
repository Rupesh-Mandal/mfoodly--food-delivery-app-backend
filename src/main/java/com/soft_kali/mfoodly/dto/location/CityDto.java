package com.soft_kali.mfoodly.dto.location;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CityDto {
    private int cityId;
    private String name;
    private DistrictsDto districtsEntity;
}
