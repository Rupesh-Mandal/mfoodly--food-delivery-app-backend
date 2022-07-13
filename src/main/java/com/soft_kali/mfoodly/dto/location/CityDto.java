package com.soft_kali.mfoodly.dto;

import com.soft_kali.mfoodly.entity.location.DistrictsEntity;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
