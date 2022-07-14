package com.soft_kali.mfoodly.dto.location;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DistrictsDto {
    private int districtsId;
    private String name;
    private CountryDto countryEntity;

}
