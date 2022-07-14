package com.soft_kali.mfoodly.entity.location;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int countrytId;
    private String name;

    public CountryEntity(int countrytId, String name) {
        this.countrytId = countrytId;
        this.name = name;
    }

    @OneToMany(mappedBy = "countryEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<DistrictsEntity> districtsEntityList =new ArrayList<>();
}
