package com.soft_kali.mfoodly.entity.location;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cityId;

    private String name;
    @ManyToOne
    @JoinColumn(name = "districtsId")
    private DistrictsEntity districtsEntity;


}
