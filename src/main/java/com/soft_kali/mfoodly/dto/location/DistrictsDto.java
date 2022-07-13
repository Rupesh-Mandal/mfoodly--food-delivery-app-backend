package com.soft_kali.mfoodly.dto;

import com.soft_kali.mfoodly.entity.location.CountryEntity;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
